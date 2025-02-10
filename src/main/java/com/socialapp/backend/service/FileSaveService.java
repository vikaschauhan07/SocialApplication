package com.socialapp.backend.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface FileSaveService {
	String saveFile(String projectUrl,String path, MultipartFile file) throws IOException;
	InputStream getFile(String path, String fileName) throws FileNotFoundException;
}
