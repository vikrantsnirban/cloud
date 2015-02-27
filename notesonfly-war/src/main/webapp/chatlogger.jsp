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
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
 <head>
	<meta http-equiv="refresh" content="5">
</head> 

	<%! boolean isUserLoggedIn = false; %>
	<%! boolean isUserValid = false; %>
	<%! Map<String, String> validUserMap = new HashMap<String, String>(); %>
	<%
		UserService userService  = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		if(user != null){ 
			isUserLoggedIn = true;
			
			validUserMap.put("apurvaaeron", "Apurva");
			validUserMap.put("muk.yadav", "Mukesh");
			validUserMap.put("sethi.gaurav2004", "Gaurav");
			validUserMap.put("vivekkohli2004", "Vivek");
			validUserMap.put("peerhasan", "Peer");
			validUserMap.put("akhera2012", "Amit");
			validUserMap.put("vikrantsnirban", "Vikrant");
			validUserMap.put("sarabjitsb", "Sarabjit");
			
			for(String userName: validUserMap.keySet()){
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
		    	<%= gmtFormat.format(feedback.getUpdateTime()) %> &nbsp; <%=validUserMap.get(feedback.getUserName().trim().toLowerCase())%>: &nbsp; <%=feedback.getContent() %> </p>
		<%}}%>
	<% }%>
	
	<%if(!isUserValid){%>
		You are not authorized to access chat service.
	<%}%>