package net.versatile.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.versatile.notesonfly.exceptions.DataRetievalException;
import net.versatile.notesonfly.gaesupport.FeedbackSupportProvider;
import net.versatile.notesonfly.gaesupport.MailProviderImpl;
import net.versatile.notesonfly.model.Feedback;
import net.versatile.notesonfly.service.FeedbackSupport;
import net.versatile.notesonfly.service.MailProvider;

public class FeedbackReportServlet extends HttpServlet{
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		FeedbackSupport feedbackSupportProvider = new FeedbackSupportProvider();
		List<Feedback> feedbacks;
		StringBuilder builder = new StringBuilder();
		try {
			feedbacks = feedbackSupportProvider.getAllFeedbacks();
			
			if(feedbacks.isEmpty()){
				builder.append("Not Comment provided.");  
		  } else { 
			  builder.append("Following the comments provided:\n");  
			 
				for(Feedback feedback : feedbacks){
					builder.append("User: " + feedback.getUserName() + " added comment: "+ feedback.getContent()+ "\n");
					}
				} 
		
			
			MailProvider mailProvider = new MailProviderImpl();
			mailProvider.sendMail("vikrantsnirban@hotmail.com", "VikrantSNIrban@gmail.com", "NotesOnFly : Admin", "Feedback Report", builder.toString());
		
		} catch (DataRetievalException e) {
			e.printStackTrace();
		}
	}

}
