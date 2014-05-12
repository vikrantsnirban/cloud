package net.versatile.notesonfly.model;

import java.util.Date;

public class Feedback {
	String content;
	String userName;
	Date updateTime;
	
	public Feedback(){}
	
	public Feedback(String content, String userName, Date updateTime){
		setContent(content);
		setUserName(userName);
		setUpdateTime(updateTime);
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	@Override
	public String toString() {
		return content + " added by " + userName + " at " + updateTime;
	}
}
