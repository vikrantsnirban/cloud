package net.versatile.web.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.versatile.web.utils.AppConstants;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class NotebookServlet extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UserService userService = UserServiceFactory.getUserService();
		User currentUser = userService.getCurrentUser();
		
		Key commentBookKey = KeyFactory.createKey(AppConstants.NOTEBOOK_KEY, currentUser.getUserId());
		String name = request.getParameter("name");
		
		Entity notebook = new Entity(AppConstants.NOTEBOOK_ENTITY, commentBookKey);
		notebook.setProperty(AppConstants.NOTEBOOK_NAME, name);
		notebook.setProperty(AppConstants.NOTEBOOK_AUTHOR, currentUser.getUserId());

		DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
		datastoreService.put(notebook);
		
		response.sendRedirect("/notebook.jsp");
	}
}
