package net.versatile.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.versatile.notesonfly.gaesupport.TaskManagerImpl;
import net.versatile.notesonfly.model.Task;
import net.versatile.notesonfly.service.TaskManager;

public class TaskServlet extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Task task = new Task();
		task.setName(getTaskAttribute(request, "name"));
		task.setQueueName(getTaskAttribute(request, "queueName"));
		task.setEndpoint(getTaskAttribute(request, "endpoint"));
		task.setParameterName(getTaskAttribute(request, "parameterName"));
		task.setParameterValue(getTaskAttribute(request, "parameterValue"));
		TaskManager taskManager = new TaskManagerImpl();
		taskManager.addTask(task);
		
		response.sendRedirect("/task.jsp");

	}
	
	private String getTaskAttribute(HttpServletRequest request, String attributeName){
		String parameterValue = null;
		Object paramValueInObject = request.getParameter(attributeName);
		if(paramValueInObject != null && paramValueInObject.toString().trim().length() > 0){
			parameterValue = paramValueInObject.toString();
		}
		return parameterValue;
	}
}
