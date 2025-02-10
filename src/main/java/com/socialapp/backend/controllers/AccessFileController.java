package com.socialapp.backend.controllers;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.socialapp.backend.service.FileSaveService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class AccessFileController {
	
	@Autowired
	private FileSaveService fileSaveService;
	
	@GetMapping(value = "/images/{imagePath}", produces = MediaType.IMAGE_PNG_VALUE)
	public void processImages(@PathVariable String imagePath,HttpServletResponse response) throws IOException {
		InputStream file =  fileSaveService.getFile("images/", imagePath);
		response.setContentType(MediaType.IMAGE_PNG_VALUE);
		StreamUtils.copy(file, response.getOutputStream());
		
	}
}
