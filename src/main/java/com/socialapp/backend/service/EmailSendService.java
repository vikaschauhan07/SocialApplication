package com.socialapp.backend.service;

public interface EmailSendService {
	void sendMail(String to, String subject,String data);
}
