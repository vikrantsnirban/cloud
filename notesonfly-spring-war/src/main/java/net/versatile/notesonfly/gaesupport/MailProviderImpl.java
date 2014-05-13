package net.versatile.notesonfly.gaesupport;

import java.io.InputStream;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import net.versatile.notesonfly.exceptions.MailReaderException;
import net.versatile.notesonfly.model.Feedback;
import net.versatile.notesonfly.service.MailProvider;
import net.versatile.web.utils.AppConstants;

public class MailProviderImpl implements MailProvider{

	Logger logger = Logger.getLogger(this.getClass().getName());
	
	@Override
	public String sendMail(String recipientEmailAddress, String senderEmailAddress, String senderTitle, String subject, String content) {
		String status = AppConstants.STATUS_SUCCESS;
		
		Properties properties = new Properties();
		Session session = Session.getDefaultInstance(properties);
		Message email = new MimeMessage(session);
		try {
			email.setFrom(new InternetAddress(senderEmailAddress, senderTitle));
			email.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmailAddress));
			email.setSubject(subject);
			email.setText(content);
			Transport.send(email);
		
		} catch (Exception exception) {
			status = exception.getMessage();
		}
		return status;
	}

	@Override
	public Feedback getFeedbackByStream(InputStream inputStream) throws MailReaderException{
		Feedback feedback;
		try {
			logger.info("Creating feedback by reading Email");
			Properties properties = new Properties();
			Session session = Session.getDefaultInstance(properties);
			MimeMessage incomingMail = new MimeMessage(session,inputStream);
			Multipart multipart = (Multipart)incomingMail.getContent();
			StringBuilder builder = new StringBuilder();
			Address[] senderAddresses = incomingMail.getFrom();
			logger.info("Extracted Senders List by Email : " + senderAddresses);
			String senderEmailAddress = (senderAddresses != null && senderAddresses.length > 0) ? senderAddresses[0].toString() : null;
			logger.info("Extracted Sender by Email : " + senderEmailAddress);
			logger.info("Got BodyPart Count = " + multipart.getCount());
			if(multipart.getCount() > 0){
				String bodyPartContent = multipart.getBodyPart(0).getContent().toString();
				logger.info("Reading Body Part:" + 0 + " having content" + bodyPartContent);
				builder.append(bodyPartContent);
			}
			/*for(int i =0; i < multipart.getCount(); i++){
				logger.info("Got BodyPart Count = " + multipart.getCount());
				String bodyPartContent = multipart.getBodyPart(i).getContent().toString();
				logger.info("Reading Body Part:" + i + " having content" + bodyPartContent);
				builder.append(bodyPartContent);
			}*/
			feedback = new Feedback();
			feedback.setContent(builder.toString());
			feedback.setUserName(senderEmailAddress != null ? senderEmailAddress : "Unknown Sender");
			feedback.setUpdateTime(new Date());
		} catch (Exception e) {
			throw new MailReaderException();
		}
		logger.info("Returning generated Feedback from Email: " + feedback.toString());
		return feedback;
	}

}
