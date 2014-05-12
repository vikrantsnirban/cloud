package net.versatile.web.servlet;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class InformationServlet extends HttpServlet{
	@Override
	  public void doGet(HttpServletRequest request, HttpServletResponse response)
	      throws IOException {
	      UserService userService = UserServiceFactory.getUserService();
	      User currentUser = userService.getCurrentUser();

	    if (request.getParameter("testing") == null) {
	      response.setContentType("text/html");
	      response.getWriter().println("Hello, this is a testing servlet.");
	      response.getWriter().println("<hr/>");
	      response.getWriter().println("<b> User Details </b> <br/>");
	      if(currentUser != null){
	    	  response.getWriter().println("Auth Domain" + currentUser.getAuthDomain()+ "<br/>");
	    	  response.getWriter().println("Email" + currentUser.getEmail()+ "<br/>" );
	    	  response.getWriter().println("Nick Name" + currentUser.getNickname()+ "<br/>" );
	    	  response.getWriter().println("User Id" + currentUser.getUserId()+ "<br/>");
	    	  response.getWriter().println( "String Format" + currentUser.toString() +"<br/>");
	      }
	      response.getWriter().println("<hr/>");
	      response.getWriter().println("<b> System Properties </b> <br/>");
	      Properties p = System.getProperties();
	      p.list(response.getWriter());
	      response.getWriter().println("<hr/>");
	      Enumeration requestAttributeNames = request.getAttributeNames();
	      response.getWriter().println("<b> Request Attributes </b> <br/>");
	      while(requestAttributeNames.hasMoreElements()){
	    	  Object attributeName = requestAttributeNames.nextElement();
	    	  response.getWriter().println(attributeName + " = " + request.getAttribute(attributeName.toString()) + "<br/>");
	      }
	      response.getWriter().println("<hr/>");
	      Enumeration requestheaderNames = request.getHeaderNames();
	      response.getWriter().println("<b> Request Headers </b>\n");
	      while(requestheaderNames.hasMoreElements()){
	    	  Object attributeName = requestheaderNames.nextElement();
	    	  response.getWriter().println(attributeName + " = " + request.getHeader(attributeName.toString()) + "<br/>");
	      }
	      response.getWriter().println("<hr/>");
	      Enumeration requestParameterNames = request.getParameterNames();
	      response.getWriter().println("<b> Request Parameters </b><br/>");
	      while(requestParameterNames.hasMoreElements()){
	    	  Object attributeName = requestParameterNames.nextElement();
	    	  response.getWriter().println(attributeName + " = " + request.getParameter(attributeName.toString()) + "<br/>");
	      }
	      

	    } else {
	      if (currentUser != null) {
	        response.setContentType("text/plain");
	        response.getWriter().println("Hello, " + currentUser.getNickname());
	      } else {
	        response.sendRedirect(userService.createLoginURL(request.getRequestURI()));
	      }
	    }
	  }

}
