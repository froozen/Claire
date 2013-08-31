package de.fro_ozen.cl4ire;

import java.io.BufferedWriter;
import java.io.IOException;

public abstract class IRCInputListener {
	BufferedWriter IRCWriter;

	public abstract void handleInput(String IRCInput);

	public void writeMessage(String target, String message){
		if(IRCWriter != null){
			try {
				IRCWriter.write("PRIVMSG " + target + " :" + message + "\r\n");
				IRCWriter.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else System.out.println("IRCWriter is missing!");
	}

	public void setIRCWriter(BufferedWriter IRCWriter){
		this.IRCWriter = IRCWriter;
	}

}
