package net.versatile.web.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.versatile.notesonfly.exceptions.DataStoreException;
import net.versatile.notesonfly.sqlite.FeedbackSupportProvider;
import net.versatile.notesonfly.gaesupport.MailProviderImpl;
import net.versatile.notesonfly.gaesupport.SubscriberSupportProvider;
import net.versatile.notesonfly.gaesupport.UserManagerImpl;
import net.versatile.notesonfly.model.Feedback;
import net.versatile.notesonfly.model.Subscriber;
import net.versatile.notesonfly.service.FeedbackSupport;
import net.versatile.notesonfly.service.MailProvider;
import net.versatile.notesonfly.service.SubscriberSupport;
import net.versatile.notesonfly.service.UserManager;
import net.versatile.web.utils.AppConstants;
import net.versatile.notesonfly.service.CacheManager;
import net.versatile.notesonfly.gaesupport.CacheManagerImpl;

public class ChatServlet extends HttpServlet{
	
	SubscriberSupport subsriberProvider  = new SubscriberSupportProvider();
	MailProvider mailProvider = new MailProviderImpl();
	Logger logger = java.util.logging.Logger.getLogger(this.getClass().getName());
	CacheManager cacheManager = new CacheManagerImpl();  
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UserManager userManager = new UserManagerImpl();
		
		String content = request.getParameter("content");

		FeedbackSupport feedbackProvider = new FeedbackSupportProvider();
		try {
			Feedback feedback = new Feedback(content, userManager.getUserName(), new Date());
			logger.info("Adding Chat to datastore: " + feedback.toString());
			feedbackProvider.addFeedback(feedback);
		} catch (DataStoreException datastoreException) {
			logger.warning("Exception occured: " + datastoreException.getMessage());
			response.getWriter().print(datastoreException.getMessage());
		}

		
		String status = AppConstants.STATUS_SUCCESS;
		
		if(!status.equalsIgnoreCase("SUCCESS")){
			response.getOutputStream().print(status);
		}
		
		response.sendRedirect("/machat.jsp");
	}
}
