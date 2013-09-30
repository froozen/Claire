package de.fro_ozen.cl4ire;

import java.util.ArrayList;

public class User {
	public String nickName;
	public ArrayList<String> channels;
	public StatusType status;
	public String afkMessage;
	
	public User(String nickName){
		status = StatusType.ONLINE;
		afkMessage = "";
	}
	
	public enum StatusType{
		ONLINE, AFK, OFFLINE;
	}
}
