package com.gencode.issuetool.storage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.gencode.issuetool.dao.FileInfoDao;
import com.gencode.issuetool.dao.FileReferenceDao;
import com.gencode.issuetool.etc.Constant;
import com.gencode.issuetool.etc.Utils;
import com.gencode.issuetool.exception.StorageException;
import com.gencode.issuetool.exception.StorageFileNotFoundException;
import com.gencode.issuetool.obj.FileInfo;
import com.gencode.issuetool.obj.FileReference;

@Service
public class FileSystemStorageService {

	private final static Logger logger = LoggerFactory.getLogger(FileSystemStorageService.class);
	@Value("${files.upload.path}")
	protected Path filePath;
	protected String uploadFileNameFormat; 
	protected String uploadFileNameFormatShort; 
	boolean noOverwrite = true;
	
	@Autowired
	private FileInfoDao fileInfoDao;
	@Autowired
	private FileReferenceDao fileReferenceDao;

	public FileSystemStorageService() {
	}
	
//	@PostConstruct
//	protected void init() {
//        try {
//            Files.createDirectories(filePath);
//            logger.info("filePath created: "+filePath);
//        }
//        catch (IOException e) {
//            throw new StorageException("Could not initialize storage", e);
//        }
//	}
		
	public FileInfo store(MultipartFile file) {
		return store(file, null);
	}
		
	public FileInfo store(MultipartFile file, boolean noOverwrite) {
		this.noOverwrite = noOverwrite;
		return store(file, null);
	}
	
	public FileInfo store(MultipartFile file, FileInfo fileInfo) {
		String filename = StringUtils.cleanPath(file.getOriginalFilename());
		try {
			if (file.isEmpty()) {
				throw new StorageException("Failed to store empty file "+filename);
			}
			if (filename.contains("..")) {
				//This is a security check
				throw  new StorageException(
						"Cannot store file with relative outside current directory "
						+ filename);
			}
			
			try (InputStream inputStream = file.getInputStream()) {
				logger.info("file new name {}=>{}", filename, fileInfo.getFileName());
				String newFileName;
				String fileExt = Utils.getFileExtension(file.getOriginalFilename());
				fileInfo.setFileExt(fileExt);
				if (fileExt.equals("")) {
					 newFileName = String.format(this.uploadFileNameFormatShort, fileInfo.getId());
				} else {
					 newFileName = String.format(this.uploadFileNameFormat, fileInfo.getId(), fileExt);
				}
				Path yyyymmmddPath = Paths.get(this.filePath.resolve(Utils.YYYYMMDD()).toString());
				if  (!Files.exists(yyyymmmddPath)) {
					Files.createDirectory(yyyymmmddPath);
					logger.info("director created:"+yyyymmmddPath.toString() );
				}
				Path newFilePath = Paths.get(yyyymmmddPath.resolve(newFileName).toString());
				Files.copy(inputStream, newFilePath,
						StandardCopyOption.REPLACE_EXISTING);
					
				logger.info("file copied: "+ newFilePath.toString());
				File newFile = new File(newFilePath.toString());
				long fileSize = newFile.length();
				String mimeType = Utils.getFileMimeType(newFile);
				
				fileInfo.setFileName(newFileName);
				fileInfo.setFileSize(fileSize);
//				fileInfo.setFileType(mimeType.contains(Constant.MIME_TYPE_IMAGE.get())
//						?Constant.FILE_TYPE_IMAGE.get():Constant.FILE_TYPE_NORMAL.get());
				fileInfo.setMimeType(mimeType);
				fileInfo.setFileOrgName(filename);
				fileInfo.setStorageDir(yyyymmmddPath.toString().replace(File.separatorChar, '/'));
				fileInfo.setFileDesc(filename);
			}
			return fileInfo;
		}
		catch (IOException e) {
			throw new StorageException("Failed to store file " + filename, e);
		}
	}
	
	public Path load(String filename) {
		return filePath.resolve(filename);
	}

	public Path load(String yyyymmdd, String filename) {
		return filePath.resolve(yyyymmdd).resolve(filename);
	}

	public Resource loadAsResource(String yyyymmdd, String filename) {
        try {
            Path file = load(yyyymmdd, filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);

            }
        }
        catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
	}

	public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);

            }
        }
        catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
	}

	/**
	 * 실제 파일 삭제 아닌 deleteYn=Y
	 * 1. fileInfo.delete
	 * 2. fileRef.delete
	 */
	public void deleteFile(long noticeId, long fileId) {
		fileInfoDao.delete(fileId);
		fileReferenceDao.delete(new FileReference(fileId, noticeId));
	}

	/**
	 * data도 delete
	 * 1. fileInfo.forceDelete
	 * 2. fileRef.forceDelete
	 * --> 참조없으면 file도 삭제 
	 */
	public void forceDeleteFile(long noticeId, FileInfo fileInfo) {
		fileInfoDao.delete(fileInfo.getId());
		fileReferenceDao.delete(new FileReference(fileInfo.getId(), noticeId));
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("fileId", Long.toString(fileInfo.getId()));
		searchMap.put("refId", Long.toString(noticeId));
		if (fileReferenceDao.search(searchMap).get().size()>0) {
			try {
				Utils.delete(fileInfo.getStorageDir(), fileInfo.getFileName());
				logger.info(String.format("file deleted %s/%s",fileInfo.getStorageDir(), fileInfo.getFileName()));
			} catch (IOException e1) {
				logger.error(String.format("file delete error %s/%s",fileInfo.getStorageDir(), fileInfo.getFileName()), e1);
			}
		}
	}
	
	/**
	 * 미완료 컨텐츠 파일을 지운다
	 * -> 내가 등록한 fileInfo 중 status=N인데 updatedDtm이 1일 이상 소요한것은 삭제
	 */
	@Transactional
	void cleanseFile(long userId) {
		List<FileInfo> uncompletedFiles = fileInfoDao.getUncompletedFiles(userId).get();
		uncompletedFiles.forEach(e -> {
			fileInfoDao.forceDelete(e.getId());
			try {
				Utils.delete(e.getStorageDir(), e.getFileName());
				logger.info(String.format("file deleted %s/%s",e.getStorageDir(), e.getFileName()));
			} catch (IOException e1) {
				logger.error(String.format("file delete error %s/%s",e.getStorageDir(), e.getFileName()), e1);
			}
		});
	}
}
