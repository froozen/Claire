package de.fro_ozen.cl4ire;

import java.util.HashMap;

public class UserManager {
	private static HashMap<String, User> users;
	
	public static void createUser(String nickName){
		if(users == null)users = new HashMap<String, User>();
		if(!users.containsKey(nickName))users.put(nickName, new User(nickName));
	}
	
	public static void setUserChannels(String nickName, String[] channels){
		User target = users.get(nickName);
		if(target!=null)target.channels = channels;
	}
}
