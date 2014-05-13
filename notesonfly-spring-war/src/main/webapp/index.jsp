<%@page import="java.util.Properties"%>
<%@page import="com.google.appengine.api.users.User" %>
<%@page import="com.google.appengine.api.users.UserService" %>
<%@page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreService" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ page import="com.google.appengine.api.datastore.Query" %>
<%@ page import="net.versatile.web.utils.AppConstants" %>
<%@ page import="java.util.List" %>


<html>
	<head>
	</head>
	<body>
	<%! boolean isUserLoggedIn = false; %>
	<%
		UserService userService  = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		if(user != null){ isUserLoggedIn = true;
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
	
	<%if(isUserLoggedIn){ %>
	<h1>Enter Details to add Note</h1>
		<form action="/note" method="post">
		<%
			DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
			Key notebookKey = KeyFactory.createKey(AppConstants.NOTEBOOK_KEY, user.getUserId());
			Query query = new Query(AppConstants.NOTEBOOK_ENTITY, notebookKey).addFilter(AppConstants.NOTEBOOK_AUTHOR, Query.FilterOperator.EQUAL, user.getUserId());
			List<Entity> notebooks = datastoreService.prepare(query).asList(FetchOptions.Builder.withDefaults());
		%>
			Select Notebook : <select name="notebook">
			<%for(Entity notebook : notebooks){%>
				<option value="<%=notebook.getProperty(AppConstants.NOTEBOOK_NAME).toString()%>"><%=notebook.getProperty(AppConstants.NOTEBOOK_NAME).toString()%> </option>
			<%}%>
			</select> <br/>
			Enter Title: <input type="text" name="title"  /> <br/>
			Enter Note Content : 
			<textarea rows="4" cols="6" name="content"></textarea> <br/>
			<input type="submit" value="Add Note" />
		</form>
		<%if(request.getParameter("notebook") != null){ %>
		<p> Notes available in <%= request.getParameter("notebook")%></p>
		<%
			Key noteKey = KeyFactory.createKey(AppConstants.NOTE_KEY, user.getUserId());
			Query noteQuery = new Query(AppConstants.NOTE_ENTITY, noteKey).addFilter(AppConstants.NOTE_AUTHOR, Query.FilterOperator.EQUAL, user.getUserId()).addFilter(AppConstants.NOTE_NOTEBOOK_NAME, Query.FilterOperator.EQUAL, request.getParameter("notebook"));
			List<Entity> notes = datastoreService.prepare(noteQuery).asList(FetchOptions.Builder.withDefaults());
			for(Entity note : notes){
		%>
			<b><%= note.getProperty(AppConstants.NOTE_TITLE) %></b> &nbsp; &nbsp; <%= note.getProperty(AppConstants.NOTE_CONTENT) %> <br/>		
	<%}}} %>
	</body>
</html>
