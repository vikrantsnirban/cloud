<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="net.versatile.notesonfly.sqlite.FeedbackSupportProvider" %>
<%@ page import="net.versatile.notesonfly.service.FeedbackSupport" %>
<%@ page import="net.versatile.notesonfly.model.Feedback" %>
<%@ page import="net.versatile.web.utils.AppConstants" %>
<%@ page import="java.util.List" %>
<%@page import="com.google.appengine.api.users.User" %>
<%@page import="com.google.appengine.api.users.UserService" %>
<%@page import="com.google.appengine.api.users.UserServiceFactory" %>
<html>
	<body>
	
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
		<form action="/chat" method="post">
		  Enter Text: <input type="textfield" name="content" size="50"/>&nbsp; <input type="submit" value="Send"/>
		</form>
		<iframe src="chatlogger.jsp" style='left:0px; width:100%; height:80%' scrolling='auto'></iframe> 
		<% }%>
		
		<%if(!isUserValid){%>
			You are not authorized to access chat service.
		<%}%>
	</body>
</html>