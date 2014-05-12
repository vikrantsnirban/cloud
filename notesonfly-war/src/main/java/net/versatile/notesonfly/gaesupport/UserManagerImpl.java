package net.versatile.notesonfly.gaesupport;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import net.versatile.notesonfly.service.UserManager;

public class UserManagerImpl implements UserManager{

	User currentUser = null;
	
	public UserManagerImpl(){
		UserService userService = UserServiceFactory.getUserService();
		currentUser = userService.getCurrentUser();
	}
	
	@Override
	public String getUserName() {
		return isUserloggedIn() ? currentUser.getNickname() : "anonymous";
	}

	@Override
	public String getUserId() {
		return isUserloggedIn() ? currentUser.getUserId() : "NO-ID";
	}

	@Override
	public boolean isUserloggedIn() {
		return currentUser != null;
	}

}
