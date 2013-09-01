package de.fro_ozen.cl4ire;

import java.util.ArrayList;
import java.util.HashMap;

public class UserManager {
	private static HashMap<String, User> users;
	
	public static void createUser(String nickName){
		if(users == null)users = new HashMap<String, User>();
		if(!users.containsKey(nickName))users.put(nickName, new User(nickName));
		System.out.println("Created user: " + nickName);
	}
	
	public static void setUserChannels(String nickName, ArrayList<String> channels){
		User target = users.get(nickName);
		if(target!=null)target.channels = channels;
	}
	
	public static void removeUser(String nickName){
		if(users.containsKey(nickName))users.remove(nickName);
		System.out.println("Removed user: " + nickName);
	}
	
	public static void removeUserChannel(String nickName, String channel){
		if(users.containsKey(nickName) && channel.contains(channel)){
			if(users.get(nickName).channels.size() >1){
				users.get(nickName).channels.remove(users.get(nickName).channels.indexOf(channel));
				System.out.println("Removed channel " + channel + " from user: " + nickName);
			}
			else{
				System.out.println("Removed user: " + nickName);
				users.remove(nickName);
			}
		}
	}
}
