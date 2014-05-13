package net.versatile.web.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.servlet.ServletException;

import net.versatile.notesonfly.exceptions.DataStoreException;
import net.versatile.notesonfly.model.Feedback;
import net.versatile.notesonfly.model.Subscriber;
import net.versatile.notesonfly.service.CacheManager;
import net.versatile.notesonfly.service.FeedbackSupport;
import net.versatile.notesonfly.service.MailProvider;
import net.versatile.notesonfly.service.SubscriberSupport;
import net.versatile.notesonfly.service.UserManager;
import net.versatile.web.utils.AppConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FeedbackController{
	
	@Autowired SubscriberSupport subsriberProvider;
	@Autowired MailProvider mailProvider;
	Logger logger = java.util.logging.Logger.getLogger(this.getClass().getName());
	@Autowired CacheManager cacheManager;  
	@Autowired FeedbackSupport feedbackProvider;
	@Autowired UserManager userManager;
	
	@RequestMapping("/feedback")
	String processFeedback(String content, String email, Model model)
			throws ServletException, IOException {
		

		
		try {
			Feedback feedback = new Feedback(content, userManager.getUserName(), new Date());
			logger.info("Adding Feedback to datastore: " + feedback.toString());
			feedbackProvider.addFeedback(feedback);
		} catch (DataStoreException datastoreException) {
			logger.warning("Exception occured: " + datastoreException.getMessage());
			model.addAttribute("exception", datastoreException.getMessage());
		}

		
		String status = AppConstants.STATUS_SUCCESS;
		
		try {
			List<Subscriber> subscribersInList = subsriberProvider.getAllSubscribers();//cacheManager.getAllSubsribers();
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
			model.addAttribute("error", status);
		}
		
		if(email != null && email.trim().length() > 0){
			try {
				Subscriber subscriber = new Subscriber(email);
				logger.info("Adding Subscriber to datastore: " + subscriber.toString());
				subsriberProvider.addSubsriber(subscriber);
			} catch (DataStoreException datastoreException) {
				model.addAttribute("exception", datastoreException.getMessage());
			}
		}
		
		return "feedback";
	}

	public SubscriberSupport getSubsriberProvider() {
		return subsriberProvider;
	}

	public void setSubsriberProvider(SubscriberSupport subsriberProvider) {
		this.subsriberProvider = subsriberProvider;
	}

	public MailProvider getMailProvider() {
		return mailProvider;
	}

	public void setMailProvider(MailProvider mailProvider) {
		this.mailProvider = mailProvider;
	}

	public CacheManager getCacheManager() {
		return cacheManager;
	}

	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}
}
