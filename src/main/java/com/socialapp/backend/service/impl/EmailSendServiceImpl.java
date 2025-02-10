package com.socialapp.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.socialapp.backend.service.EmailSendService;

@Service
public class EmailSendServiceImpl implements EmailSendService {

	@Autowired
	private JavaMailSender javaMailSender;
	
	@Override
	public void sendMail(String to, String subject, String data) {
	System.out.println(to + " " + subject + " " + data);
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(data);
		javaMailSender.send(message);
	}
	
}
