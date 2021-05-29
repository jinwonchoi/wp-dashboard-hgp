package com.gencode.issuetool.storage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gencode.issuetool.etc.ImageResizer;
import com.gencode.issuetool.etc.Utils;
import com.gencode.issuetool.exception.StorageException;
import com.gencode.issuetool.obj.FileInfo;

@Service
public class ProfileImgStorageService extends FileSystemStorageService {
	
	private final static Logger logger = LoggerFactory.getLogger(ProfileImgStorageService.class);
	
	@Value("${files.avatar.filename}")
	protected String thumbFileNameFormat;

	@Value("${files.profile.filename}")
	protected String profileFileNameFormat;

	/*
		files.profile.path=files/profile
		files.profile.profile.filename=profile_%s.xxx
		files.profile.thumbnail.filename=thumb_%s.xxx
	 */
	public ProfileImgStorageService(@Value("${files.upload.profile.path}") Path filePath, 
			@Value("${files.profile.filename.org}") String uploadFileNameFormat, 
			@Value("${files.profile.filename.org.short}") String uploadFileNameFormatShort) {
		super();
		this.filePath = filePath;
		this.uploadFileNameFormat = uploadFileNameFormat;
		this.uploadFileNameFormatShort = uploadFileNameFormatShort;		
	}

	@PostConstruct
	protected void init() {
        try {
            Files.createDirectories(filePath);
            logger.info("filePath created: "+filePath);
        }
        catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
	}

	public String generateThumbImage(FileInfo fileInfo) throws IOException {
		String thumbFileName = String.format(thumbFileNameFormat, fileInfo.getRegisterId());
    	int scaledWidth = 32;
    	int scaledHeight = 32;
    	String inputImagePath = Paths.get(fileInfo.getStorageDir()).resolve(fileInfo.getFileName()).toString();
    	System.out.println(inputImagePath+"==>"+filePath.resolve(thumbFileName).toString());
        ImageResizer.resize(inputImagePath, 
        		filePath.resolve(thumbFileName).toString()
        		, scaledWidth, scaledHeight);
        return filePath.resolve(thumbFileName).toString().replace(File.separatorChar, '/');
	}

	public String generateProfileImage(FileInfo fileInfo) throws IOException {
		String profileFileName = String.format(profileFileNameFormat, fileInfo.getRegisterId());
    	int scaledWidth = 128;
    	int scaledHeight = 128;
    	String inputImagePath = Paths.get(fileInfo.getStorageDir()).resolve(fileInfo.getFileName()).toString();
    	System.out.println(inputImagePath+"==>"+filePath.resolve(profileFileName).toString());
        ImageResizer.resize(inputImagePath, filePath.resolve(profileFileName).toString()
        		, scaledWidth, scaledHeight);
        return filePath.resolve(profileFileName).toString().replace(File.separatorChar, '/');
	}

}

//files.profile.org.path=files/profile/org
//files.profile.thmb.path=files/profile/thm
