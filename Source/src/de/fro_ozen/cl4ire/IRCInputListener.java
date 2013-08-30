package de.fro_ozen.cl4ire;

import java.io.BufferedWriter;
import java.io.IOException;

public abstract class IRCInputListener {
	private BufferedWriter IRCWriter;
	
	public abstract void handleInput(String IRCInput);
	
	public void writeMessage(String target, String message){
		try {
			IRCWriter.write("PRIVMSG " + target + " :" + message + "\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
