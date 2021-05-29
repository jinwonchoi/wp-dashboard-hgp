package com.gencode.issuetool.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.gencode.issuetool.dao.NoticeBoardDao;
import com.gencode.issuetool.obj.NoticeBoard;
import com.gencode.issuetool.obj.NoticeBoardEx;
import com.gencode.issuetool.obj.NoticeBoardWithFileList;
import com.gencode.issuetool.storage.AttachFileSystemStorageService;
import com.gencode.issuetool.storage.EmbedFileSystemStorageService;
import com.gencode.issuetool.dao.BoardCommentDao;
import com.gencode.issuetool.dao.FileInfoDao;
import com.gencode.issuetool.dao.FileReferenceDao;
import com.gencode.issuetool.obj.BoardComment;
import com.gencode.issuetool.obj.FileInfo;
import com.gencode.issuetool.obj.FileReference;
import com.gencode.issuetool.etc.Constant;
import com.gencode.issuetool.etc.Utils;
import com.gencode.issuetool.exception.MethodUnsupportableException;
import com.gencode.issuetool.exception.TooManyRowException;
import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.io.PageResultObj;
import com.gencode.issuetool.io.SortDirection;

@Service
public class NoticeBoardService {
	private final static Logger logger = LoggerFactory.getLogger(NoticeBoardService.class);

	@Autowired
	private NoticeBoardDao noticeBoardDao;
	@Autowired
	private BoardCommentDao boardCommentDao;
	@Autowired
	private FileInfoDao fileInfoDao;
	@Autowired
	private FileReferenceDao fileReferenceDao;
	
	@Autowired
	private EmbedFileSystemStorageService embedFileService;
	@Autowired
	private AttachFileSystemStorageService attachFileService;
		
	@Transactional
	public Optional<NoticeBoard> addPost(NoticeBoard t) {
		long noticeId = noticeBoardDao.register(t);
		return noticeBoardDao.load(noticeId);
	}
	
	@Transactional
	public Optional<NoticeBoardEx> addPostEx(NoticeBoard t) {
		long noticeId = noticeBoardDao.register(t);
		return noticeBoardDao.loadEx(noticeId);
	}
	
	@Transactional
	public Optional<NoticeBoardEx> addPostEx(NoticeBoardWithFileList t) {
		long noticeId = noticeBoardDao.register(t);
		t.setId(noticeId);
		completeFileUploadOnContentSave(t);
		cleanseFile(t.getRegisterId());
		return noticeBoardDao.loadEx(noticeId);
	}

	@Transactional
	public void updatePost(NoticeBoard t) {
		noticeBoardDao.update(t);
	}
	
	@Transactional
	public void updatePost(NoticeBoardWithFileList t) {
		noticeBoardDao.update(t);
		completeFileUploadOnContentSave(t);
		cleanseFile(t.getRegisterId());
	}

	public Optional<NoticeBoard> loadPost(long id) {
		return noticeBoardDao.load(id);
	}
	
	public Optional<NoticeBoardWithFileList> loadPostEx(long id) {
		NoticeBoardWithFileList result = new NoticeBoardWithFileList(noticeBoardDao.loadEx(id).get());
		List<FileInfo> attachedFileList = fileInfoDao.getFilesByRefId(id, Constant.FILE_REFERENCE_REF_TYPE_ADDFILE.get()).get();
		List<FileInfo> embededFileList = fileInfoDao.getFilesByRefId(id, Constant.FILE_REFERENCE_REF_TYPE_NORMAL.get()).get();
		result.setAttachedFileList(attachedFileList);
		result.setEmbededFileList(embededFileList);
		return Optional.of(result);
	}
	
	@Transactional
	public void deletePost(long id) {
		NoticeBoard notice = noticeBoardDao.load(id).get();
		noticeBoardDao.delete(id);
		
		fileInfoDao.getFilesByRefId(id, "").ifPresent( fList->{
			fList.forEach(e-> embedFileService.deleteFile(id, e.getId()));
		});
		cleanseFile(notice.getRegisterId());
	}
	
	public Optional<PageResultObj<List<NoticeBoard>>> searchPost(PageRequest req) {
		return noticeBoardDao.search(req);
	}

	public Optional<PageResultObj<List<NoticeBoardEx>>> searchPostEx(PageRequest req) {
		return noticeBoardDao.searchEx(req);
	}
	
	@Transactional
	public Optional<BoardComment> addComment(BoardComment t) {
		NoticeBoard board = noticeBoardDao.load(t.getRefBoardId()).get();
		board.setCommentCnt(board.getCommentCnt()>=0?(board.getCommentCnt()+1):0);
		noticeBoardDao.update(board);
		long noticeId = boardCommentDao.register(t);
		return boardCommentDao.load(noticeId);
	}
	
	@Transactional
	public void updateComment(BoardComment t) {
		boardCommentDao.update(t);
	}

	public Optional<BoardComment> loadComment(long id) {
		return boardCommentDao.load(id);
	}
	
	@Transactional
	public void deleteComment(long id) {
		BoardComment comment = boardCommentDao.load(id).get();
		NoticeBoard board = noticeBoardDao.load(comment.getRefBoardId()).get();
		board.setCommentCnt(board.getCommentCnt()>0?(board.getCommentCnt()-1):0);
		noticeBoardDao.update(board);
		boardCommentDao.delete(id);
	}
	public Optional<PageResultObj<List<BoardComment>>> searchComment(PageRequest req) {
		return boardCommentDao.search(req);
	}
	
	/** 
	 * 컨텐츠 삽입파일 업로드
	 * 1. file upload
	 * 2. fileInfo.insert
	 */
	@Transactional
	public Optional<FileInfo> uploadEmbedFile(MultipartFile multipartFile, FileInfo fileInfo) {
		long fileId = fileInfoDao.register(fileInfo);
		fileInfo.setId(fileId);
		fileInfo.setFileType(Constant.FILE_INFO_FILE_TYPE_EMBED.get());
		fileInfo = embedFileService.store(multipartFile, fileInfo);
		fileInfoDao.update(fileInfo);
		return fileInfoDao.load(fileInfo.getId());		
	}

	/**
	 * 컨텐츠 첨부파일 업로드
	 * 1. file upload
	 * 2. fileInfo.insert
	 * @param multipartFile
	 * @param fileInfo
	 * @return
	 */
	@Transactional
	public Optional<FileInfo> uploadAttachFile(MultipartFile multipartFile, FileInfo fileInfo, long refId) {
		long fileId = fileInfoDao.register(fileInfo);
		fileInfo.setId(fileId);
		fileInfo.setFileType(Constant.FILE_INFO_FILE_TYPE_ATTACHED.get());
		attachFileService.store(multipartFile, fileInfo);
		// refId가 이미 등록된 게시물이면 첨부파일은 바로 저장처리한다.
		if (refId > 0) {
			fileInfo.setStatus(Constant.FILE_INFO_STATUS_COMPLETE.get());
			FileReference fileReference = new FileReference(fileInfo.getId(), refId, fileInfo.getFileType()
					,fileInfo.getFileOrgName()
					, fileInfo.getStorageDir(),null, Constant.FILE_REFERENCE_STATUS_COMPLETE.get(), 
					fileInfo.getRegisterId());
			fileReferenceDao.register(fileReference);
		}
		fileInfoDao.update(fileInfo);
		return fileInfoDao.load(fileInfo.getId());
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
	
	/**
	 * 컨텐츠 등록/수정 등 편집을 취소
	 * 작업하며 등록했던 파일을 모두 삭제 
	 * 클라이언트에서는 삭제대상 파일들만 모두 올린다.
	 */
	@Transactional
	public void cancelFileUploadOnContentEdit(List<FileInfo> tempFileList) {
		tempFileList.forEach(e -> {
			if (!e.getStatus().equals(Constant.FILE_INFO_STATUS_COMPLETE.get())) {
				fileInfoDao.forceDelete(e.getId());
				try {
					Utils.delete(e.getStorageDir(), e.getFileName());
					logger.info(String.format("file deleted %s/%s",e.getStorageDir(), e.getFileName()));
				} catch (IOException e1) {
					logger.error(String.format("file delete error %s/%s",e.getStorageDir(), e.getFileName()), e1);
				}
			}
		});
	}
	
	/**
	 * 게시물 저장시 변경된 content 내장 파일을 삭제/추가처리한다.
	 * @param notice
	 */
	void completeFileUploadOnContentSave(NoticeBoardWithFileList notice) {
		if (notice.getFileIdListToAdd() != null) {
			notice.getFileIdListToAdd().forEach(e-> {
				fileInfoDao.completeUpload(e.getId());
				FileInfo fileInfo = fileInfoDao.load(e.getId()).get();
				FileReference fileReference = new FileReference(fileInfo.getId(), notice.getId(), fileInfo.getFileType()
						,fileInfo.getFileOrgName()
						, fileInfo.getStorageDir(),null, Constant.FILE_REFERENCE_STATUS_COMPLETE.get(), 
						notice.getRegisterId());
				fileReferenceDao.register(fileReference);
			});
		}
		//파일삭제하는 로직은 기능 확장시 고려
		if (notice.getFileIdListToDelete() != null ) {
			notice.getFileIdListToDelete().forEach(e-> {
				embedFileService.deleteFile(notice.getId(), e.getId());
			});
		}
	}

	/**
	 * 실제 파일 삭제 아닌 deleteYn=Y
	 * 1. fileInfo.delete
	 * 2. fileRef.delete
	 */
	@Transactional
	public void deleteFile(long noticeId, long fileId) {
		embedFileService.deleteFile(noticeId, fileId);
	}

	/**
	 * data도 delete
	 * 1. fileInfo.forceDelete
	 * 2. fileRef.forceDelete
	 * --> 참조없으면 file도 삭제 
	 */
//	public void forceDeleteFile(long noticeId, FileInfo fileInfo) {
//		fileInfoDao.delete(fileInfo.getId());
//		fileReferenceDao.delete(new FileReference(fileInfo.getId(), noticeId));
//		Map<String, String> searchMap = new HashMap<String, String>();
//		searchMap.put("fileId", Long.toString(fileInfo.getId()));
//		searchMap.put("refId", Long.toString(noticeId));
//		if (fileReferenceDao.search(searchMap).get().size()>0) {
//			try {
//				Utils.delete(fileInfo.getStorageDir(), fileInfo.getFileName());
//				logger.info(String.format("file deleted %s/%s",fileInfo.getStorageDir(), fileInfo.getFileName()));
//			} catch (IOException e1) {
//				logger.error(String.format("file delete error %s/%s",fileInfo.getStorageDir(), fileInfo.getFileName()), e1);
//			}
//		}
//	}
}
