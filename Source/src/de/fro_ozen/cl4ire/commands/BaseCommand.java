package de.fro_ozen.cl4ire.commands;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

public abstract class BaseCommand {
	public abstract void run(String channel, String originUserName, ArrayList<String> args, BufferedWriter IRCWriter);
	
	public void postText(String target, String text, BufferedWriter IRCWriter){
		try {
			IRCWriter.write("PRIVMSG " + target + " :"  + text + "\r\n");
			IRCWriter.flush();
		} catch (IOException e) {e.printStackTrace();}
	}
	
	public void writeCommand(String target, String command, BufferedWriter IRCWriter){
		try {
			IRCWriter.write( command + "\r\n");
			IRCWriter.flush();
		} catch (IOException e) {e.printStackTrace();}
	}
}
