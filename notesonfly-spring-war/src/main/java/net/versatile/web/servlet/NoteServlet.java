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

public class NoteServlet extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UserService userService = UserServiceFactory.getUserService();
		User currentUser = userService.getCurrentUser();
		
		Key noteKey = KeyFactory.createKey(AppConstants.NOTE_KEY, currentUser.getUserId());
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String notebook = request.getParameter("notebook");
		
		Entity note = new Entity(AppConstants.NOTE_ENTITY, noteKey);
		note.setProperty(AppConstants.NOTE_TITLE, title);
		note.setProperty(AppConstants.NOTE_CONTENT,content);
		note.setProperty(AppConstants.NOTE_NOTEBOOK_NAME,notebook);
		note.setProperty(AppConstants.NOTE_AUTHOR,currentUser.getUserId());

		DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
		datastoreService.put(note);
		
		response.sendRedirect("/index.jsp?notebook="+notebook);
	}
}
