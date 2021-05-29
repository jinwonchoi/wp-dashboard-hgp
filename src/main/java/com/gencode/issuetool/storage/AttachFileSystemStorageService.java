package com.gencode.issuetool.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.gencode.issuetool.exception.StorageException;

@Service
public class AttachFileSystemStorageService extends FileSystemStorageService {
	private final static Logger logger = LoggerFactory.getLogger(AttachFileSystemStorageService.class);
	
	public AttachFileSystemStorageService(@Value("${files.upload.board.embed.path}") Path filePath, 
			@Value("${files.board.embed.filename}") String uploadFileNameFormat, 
			@Value("${files.board.embed.filename.short}") String uploadFileNameFormatShort) {
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

}
