package net.versatile.notesonfly.model;

import java.io.Serializable;

public class Subscriber implements Serializable{
	String emailAddress;

	public Subscriber(){}
	
	public Subscriber(String emailAddress){
		setEmailAddress(emailAddress);
	}
	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	@Override
	public String toString() {
		return "Email:" + emailAddress;
	}
	
	@Override
	public int hashCode() {
		return emailAddress.hashCode();
	}
	
	@Override
	public boolean equals(Object object) {
		if(object instanceof Subscriber){
			Subscriber subscriber = (Subscriber) object;
			return getEmailAddress().equalsIgnoreCase(subscriber.getEmailAddress());
		}
		return false;
	}
}
