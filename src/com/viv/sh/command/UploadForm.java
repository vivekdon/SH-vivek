package com.viv.sh.command;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

public class UploadForm {
	
	private String fileType;
	private MultipartFile file;
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
	
	

}
