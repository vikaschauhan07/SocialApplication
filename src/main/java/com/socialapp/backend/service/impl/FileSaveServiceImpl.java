package com.socialapp.backend.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.socialapp.backend.service.FileSaveService;

import lombok.Value;

@Service
public class FileSaveServiceImpl implements FileSaveService {

	@Override
	public String saveFile(String projectUrl,String path, MultipartFile file) throws IOException{
		
		
		// get the file name
		String fileName = file.getOriginalFilename();
		//genrate nwe Name
		String newName = UUID.randomUUID().toString().concat(fileName.substring(fileName.lastIndexOf('.')));
		System.out.println(fileName);
		//full path
		String filePath = path + File.separator + newName;
	
		//created the folder of the path 
		
		File f = new File(path);
		
		if(!f.exists()) {
			f.mkdir();
		}
		
		//copy the file and save
		Files.copy(file.getInputStream(), Paths.get(filePath));
		
		return filePath;
	}

	@Override
	public InputStream getFile(String path, String fileName) throws FileNotFoundException {
		String pathFile = path + fileName;
		InputStream is = new FileInputStream(pathFile);
		System.out.println(fileName);
		return is;
	}
	
}
