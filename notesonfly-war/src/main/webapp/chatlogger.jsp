<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="net.versatile.notesonfly.sqlite.FeedbackSupportProvider" %>
<%@ page import="net.versatile.notesonfly.service.FeedbackSupport" %>
<%@ page import="net.versatile.notesonfly.model.Feedback" %>
<%@ page import="net.versatile.web.utils.AppConstants" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Collections" %>
<%@page import="com.google.appengine.api.users.User" %>
<%@page import="com.google.appengine.api.users.UserService" %>
<%@page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.TimeZone" %>
 <head>
	<meta http-equiv="refresh" content="5">
</head> 

	<%! boolean isUserLoggedIn = false; %>
	<%! boolean isUserValid = false; %>
	<%
		UserService userService  = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		if(user != null){ 
			isUserLoggedIn = true;
			
			String [] validUsers = {"apurvaaeron", "muk.yadav", "sethi.gaurav2004", "vivekkohli2004", "peerhasan", "akhera2012", "vikrantsnirban", "sarabjitsb"};
			for(String userName: validUsers){
				if(user.getNickname().trim().equalsIgnoreCase(userName)){
					isUserValid = true;
				}
			}
		}
	%>

	<%if(isUserLoggedIn && isUserValid){ %>
		<%
			FeedbackSupport feedbackSupportProvider = new FeedbackSupportProvider();
			List<Feedback> feedbacks = feedbackSupportProvider.getAllFeedbacks();
			if(feedbacks.isEmpty()){
		%>
			<p> Not Data. </p>
		<% } else {
				Collections.sort(feedbacks);
				for(Feedback feedback : feedbacks){
			%>
			<p> <%
									DateFormat gmtFormat = new SimpleDateFormat();
		    			TimeZone gmtTime = TimeZone.getTimeZone("IST");
		    			gmtFormat.setTimeZone(gmtTime);
		    	%>
		    	<%= gmtFormat.format(feedback.getUpdateTime()) %> &nbsp; <%=feedback.getUserName()%>: &nbsp; <%=feedback.getContent() %> </p>
		<%}}%>
	<% }%>
	
	<%if(!isUserValid){%>
		You are not authorized to access chat service.
	<%}%>