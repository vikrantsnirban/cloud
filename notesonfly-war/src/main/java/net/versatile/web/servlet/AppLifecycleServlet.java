package net.versatile.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.versatile.notesonfly.gaesupport.MailProviderImpl;
import net.versatile.notesonfly.gaesupport.SubscriberSupportProvider;
import net.versatile.notesonfly.model.Subscriber;
import net.versatile.notesonfly.service.MailProvider;
import net.versatile.notesonfly.service.SubscriberSupport;

import com.google.appengine.api.log.AppLogLine;
import com.google.appengine.api.log.LogQuery;
import com.google.appengine.api.log.LogServiceFactory;
import com.google.appengine.api.log.RequestLogs;

public class AppLifecycleServlet extends HttpServlet{
	Logger logger = Logger.getLogger(this.getClass().getName());
	SubscriberSupport subsriberProvider  = new SubscriberSupportProvider();
	MailProvider mailProvider = new MailProviderImpl();
	
	@Override
	public void init() throws ServletException {
		logger.info("Application Started");
/*		try {
			List<Subscriber> subscribersInList = subsriberProvider.getAllSubscribers();
			Set<Subscriber> subscribers = new HashSet<Subscriber>(subscribersInList);
			for(Subscriber subscriber : subscribers){
				if(subscriber.getEmailAddress().trim().length() > 0){
					logger.info("Sending startup mail to " + subscriber.toString());
					mailProvider.sendMail(subscriber.getEmailAddress(), "VikrantSNIrban@gmail.com", "NotesOnFly : Admin", "NotesOnFly : Startup Status", "Application has started with latest version. <br.> You can access using link: http://notesonfly.appspot.com/");
				}
			}
		} catch (Exception exception) {
			logger.warning("Exception occured: " + exception.getMessage());
		} */ 
	}
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
	    PrintWriter writer = response.getWriter();
	    StringBuilder builder = new StringBuilder();
	    Object resourceName = request.getParameter("resource");
	    LogQuery query = LogQuery.Builder.withDefaults();

	    if(resourceName != null){
	    	builder.append("<b>REQUEST LOG for : "+ resourceName +"</b> <br />");
	    	builder.append("<b>Resource | IP Address| Method | Time </b> <hr/>");
		    
		    for (RequestLogs record : LogServiceFactory.getLogService().fetch(query)) {
		      Calendar cal = Calendar.getInstance();
		      cal.setTimeInMillis(record.getStartTimeUsec() / 1000);
		      if(record.getResource().contains(resourceName.toString())){
			      builder.append(record.getResource() + " " + record.getIp() + " " + record.getMethod() + " "+cal.getTime().toString() + "<br/>");
			      
		      }
		    }
		    writer.println(builder.toString());
		    
		    /*Object cronHeader = request.getHeader("X-AppEngine-Cron");
		    if(cronHeader != null && Boolean.getBoolean(cronHeader.toString()) == Boolean.TRUE){
		    */	new MailProviderImpl().sendMail("vikrantsnirban@hotmail.com", "vikrantsnirban@gmail.com", "NotesOnFly-Admin", "Resource: Mail Usage Report", builder.toString());
		    //}
		    
	    }else{
	    	writer.println("No Resource criteria provided.");
	    }
	}
}
