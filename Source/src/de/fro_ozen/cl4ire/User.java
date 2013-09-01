package de.fro_ozen.cl4ire;

import java.util.ArrayList;

public class User {
	public String nickName;
	public boolean afk;
	public ArrayList<String> channels;
	
	public User(String nickName){
		afk = false;
	}
}
