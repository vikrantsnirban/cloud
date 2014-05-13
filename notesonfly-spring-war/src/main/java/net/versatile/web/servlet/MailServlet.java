package net.versatile.web.servlet;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MailServlet extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Properties properties = new Properties();
		Session session = Session.getDefaultInstance(properties);
		
		String content = request.getParameter("content");
		String subject = request.getParameter("subject");
		String reciepentEmail = request.getParameter("receipent");
		
		Message email = new MimeMessage(session);
		try {
			email.setFrom(new InternetAddress("VikrantSNIrban@gmail.com", "NotesOnFly : Admin"));
			email.addRecipient(Message.RecipientType.TO, new InternetAddress(reciepentEmail));
			email.setSubject(subject);
			email.setText(content);
			Transport.send(email);
		} catch (Exception e) {
			response.getOutputStream().print(e.toString());
		}
	}
}
