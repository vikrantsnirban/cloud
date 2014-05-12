<%@page import="com.google.appengine.api.datastore.Query.SortDirection"%>
<%@ page import="com.google.appengine.api.datastore.DatastoreService" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ page import="com.google.appengine.api.datastore.Query" %>
<%@ page import="net.versatile.web.utils.AppConstants" %>
<%@ page import="java.util.List" %>
<%@page import="com.google.appengine.api.users.User" %>
<%@page import="com.google.appengine.api.users.UserService" %>
<%@page import="com.google.appengine.api.users.UserServiceFactory" %>

<%
	UserService userService  = UserServiceFactory.getUserService();
	User user = userService.getCurrentUser();
	if(user != null){
		DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
		Key notebookKey = KeyFactory.createKey(AppConstants.NOTEBOOK_KEY, user.getUserId());
		Query query = new Query(AppConstants.NOTEBOOK_ENTITY, notebookKey).addFilter(AppConstants.NOTEBOOK_AUTHOR, Query.FilterOperator.EQUAL, user.getUserId());
		List<Entity> notebooks = datastoreService.prepare(query).asList(FetchOptions.Builder.withDefaults());
		if(notebooks.isEmpty()){
%>
	<p> you have not added any notebook. </p>
<%}else{%>
	<p> Notebooks Avaialable: </p>
	<%for(Entity notebook : notebooks){%>
		<%=notebook.getProperty(AppConstants.NOTEBOOK_NAME).toString()%> <br>
	<%}%>
<%}%>
<form action="/notebook" method="post">
    <div><input type="text" name="name" /></div>
    <div><input type="submit" value="Add Notebook"/></div>
</form>
<%}else{ %>
	<p> please log-in to add notebooks </p>
<%} %>