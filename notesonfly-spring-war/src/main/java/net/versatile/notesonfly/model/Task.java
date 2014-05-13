package net.versatile.notesonfly.model;

public class Task {
	String name;
	String queueName;
	String parameterName;
	String parameterValue;
	String endpoint;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getQueueName() {
		return queueName;
	}
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}
	public String getParameterName() {
		return parameterName;
	}
	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}
	public String getParameterValue() {
		return parameterValue;
	}
	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}
	public String getEndpoint() {
		return endpoint;
	}
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Name=" + getName() + "\n");
		builder.append("Endpoint=" + getEndpoint() + "\n");
		builder.append("QueueName=" + getQueueName() + "\n");
		builder.append("ParameterName=" + getParameterName() + "\n");
		builder.append("ParameterValue=" + getParameterValue() + "\n");
		return builder.toString();
	}
}

