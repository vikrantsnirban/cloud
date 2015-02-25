<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="net.versatile.notesonfly.gaesupport.FeedbackSupportProvider" %>
<%@ page import="net.versatile.notesonfly.service.FeedbackSupport" %>
<%@ page import="net.versatile.notesonfly.model.Feedback" %>
<%@ page import="net.versatile.web.utils.AppConstants" %>
<%@ page import="java.util.List" %>
<%@page import="com.google.appengine.api.users.User" %>
<%@page import="com.google.appengine.api.users.UserService" %>
<%@page import="com.google.appengine.api.users.UserServiceFactory" %>


	<%! boolean isUserLoggedIn = false; %>
	<%! boolean isUserValid = false; %>
	<%
		UserService userService  = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		if(user != null){ 
			isUserLoggedIn = true;
			
			String [] validUsers = {"apurvaaeron", "muk.yadav", "sethi.gaurav2004", "vivekkohli2004", "peerhasan", "akhera2012", "vikrantsnirban"};
			for(String userName: validUsers){
				if(user.getNickname().trim().equalsIgnoreCase(userName)){
					isUserValid = true;
				}
			}
	%>
	
	<p align="left">
		Hello, <%=user.getNickname() %> !!
	</p>
	
	<p align="right">
		<a href="<%= userService.createLogoutURL(request.getRequestURI())%>"> Sign out. </a>
	</p>
	
	<%} else{
		response.sendRedirect(userService.createLoginURL(request.getRequestURI()));
		}
	%>

	<%if(isUserLoggedIn && isUserValid){ %>
		<%
			FeedbackSupport feedbackSupportProvider = new FeedbackSupportProvider();
			List<Feedback> feedbacks = feedbackSupportProvider.getAllFeedbacks();
			if(feedbacks.isEmpty()){
		%>
			<p> Not Data. </p>
		<% } else {%>
			<p> Following the comments provided: </p>
			<%
				for(Feedback feedback : feedbacks){
			%>
			<p> <%=feedback.getUserName()%>: &nbsp; <%=feedback.getContent() %> </p>
		<%}}%>
		
		<form action="/chat" method="post">
		    <div>Enter Text: <textarea name="content" rows="3" cols="60"></textarea></div>
		    <div><input type="submit" value="Send"/></div>
		</form>
	<% }%>
	
	<%if(!isUserValid){%>
		You are not authorized to access chat service.
	<%}%>