<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="net.versatile.notesonfly.gaesupport.FeedbackSupportProvider" %>
<%@ page import="net.versatile.notesonfly.service.FeedbackSupport" %>
<%@ page import="net.versatile.notesonfly.model.Feedback" %>
<%@ page import="net.versatile.web.utils.AppConstants" %>
<%@ page import="java.util.List" %>
<%@page import="com.google.appengine.api.users.User" %>

<%
	FeedbackSupport feedbackSupportProvider = new FeedbackSupportProvider();
	List<Feedback> feedbacks = feedbackSupportProvider.getAllFeedbacks();
	if(feedbacks.isEmpty()){
%>
	<p> Not Comment provided. </p>
<% } else {%>
	<p> Following the comments provided: </p>
	<%
		for(Feedback feedback : feedbacks){
	%>
	<p> <%=feedback.getUserName()%> &nbsp; wrote : 
	<blockquote><%=feedback.getContent() %></blockquote>		
<%}}%>

<form action="/feedback" method="post">
    <div>Provide Your Comment: <textarea name="content" rows="3" cols="60"></textarea></div>
    <div>Enter Email for notifications: <input type="text" name="email" /></div>
    <div><input type="submit" value="Post Feedback"/></div>
</form>