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
import net.versatile.notesonfly.gaesupport.FeedbackSupportProvider;
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

public class FeedbackServlet extends HttpServlet{
	
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
			logger.info("Adding Feedback to datastore: " + feedback.toString());
			feedbackProvider.addFeedback(feedback);
		} catch (DataStoreException datastoreException) {
			logger.warning("Exception occured: " + datastoreException.getMessage());
			response.getWriter().print(datastoreException.getMessage());
		}

		
		String status = AppConstants.STATUS_SUCCESS;
		
		try {
			List<Subscriber> subscribersInList = cacheManager.getAllSubsribers();//subsriberProvider.getAllSubscribers();
			Set<Subscriber> subscribers = new HashSet<Subscriber>(subscribersInList);
			for(Subscriber subscriber : subscribers){
				logger.info("Sending mail to " + subscriber.toString());
				status = mailProvider.sendMail(subscriber.getEmailAddress(), "VikrantSNIrban@gmail.com", "NotesOnFly : Admin", "NotesOnFly : Feedback - Update", content + " added by :" + userManager.getUserName());
			}

		} catch (Exception exception) {
			logger.warning("Exception occured: " + exception.getMessage());
			exception.printStackTrace();
			status = exception.getMessage();
		} 

		
		if(!status.equalsIgnoreCase("SUCCESS")){
			response.getOutputStream().print(status);
		}
		
		String email = request.getParameter("email");
		if(email != null && email.trim().length() > 0){
			try {
				Subscriber subscriber = new Subscriber(email);
				logger.info("Adding Subscriber to datastore: " + subscriber.toString());
				subsriberProvider.addSubsriber(subscriber);
			} catch (DataStoreException datastoreException) {
				response.getWriter().print(datastoreException.getMessage());
			}
		}
		
		response.sendRedirect("/feedback.jsp");
	}
}
