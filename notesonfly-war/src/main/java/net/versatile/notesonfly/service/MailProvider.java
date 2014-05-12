package net.versatile.notesonfly.service;

import java.io.InputStream;

import net.versatile.notesonfly.exceptions.MailReaderException;
import net.versatile.notesonfly.model.Feedback;

public interface MailProvider {
	String sendMail(String recipient, String senderEmailAddress, String sendTitle, String subject, String content);
	Feedback getFeedbackByStream(InputStream inputStream) throws MailReaderException;
}
