package net.versatile.web.servlet;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.versatile.notesonfly.gaesupport.FeedbackSupportProvider;
import net.versatile.notesonfly.gaesupport.MailProviderImpl;
import net.versatile.notesonfly.model.Feedback;
import net.versatile.notesonfly.service.FeedbackSupport;
import net.versatile.notesonfly.service.MailProvider;

public class IncomingMailServlet extends HttpServlet{
	MailProvider mailProvider = new MailProviderImpl();
	Logger logger = Logger.getLogger(this.getClass().getName());
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			FeedbackSupport feedbackProvider = new FeedbackSupportProvider();
			Feedback feedback = mailProvider.getFeedbackByStream(request.getInputStream());
			logger.info("Recieved Feedback using email:" + feedback.toString());
			logger.info("Adding Feedback to datastore:" + feedback.toString());
			feedbackProvider.addFeedback(feedback);
		} catch (Exception e) {
			logger.warning("Exception occured:" + e.getMessage());
			e.printStackTrace();
		}
	}
}
