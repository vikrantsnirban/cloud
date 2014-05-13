package net.versatile.notesonfly.gaesupport;

import java.util.logging.Logger;

import net.versatile.notesonfly.model.Task;
import net.versatile.notesonfly.service.TaskManager;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

public class TaskManagerImpl implements TaskManager{
	Logger logger = Logger.getLogger(this.getClass().getName());
	@Override
	public void addTask(Task task) {
		Queue queue = null;
		
		logger.info("Adding Task[" + task.toString() + "]");
		
		if(task.getQueueName() == null || (task.getQueueName() != null && task.getQueueName().trim().length() == 0)){
				queue = QueueFactory.getDefaultQueue();
		}else{
			queue = QueueFactory.getQueue(task.getQueueName());
		}
		
		logger.info("Queue to be used : " + queue.getQueueName());
		
		if(task.getParameterName() != null && task.getParameterValue() != null){
			logger.info("Adding Parameters to process");
			queue.add(TaskOptions.Builder.withUrl(task.getEndpoint()).param(task.getParameterName(), task.getParameterValue()).taskName(task.getName()));
		}else{
			logger.info("excluding Parameters to process");
			queue.add(TaskOptions.Builder.withUrl(task.getEndpoint()).taskName(task.getName()));
		}
	}
}
