package de.fro_ozen.cl4ire.inputlisteners;

import java.util.ArrayList;

import de.fro_ozen.cl4ire.IRCInputListener;
import de.fro_ozen.cl4ire.commands.ClaireCommandHandler;

public class ClaireInputListener extends IRCInputListener{
	private final String commandStarter = "[";

	public void handlePrivmsgInput(String channel, String originUserName, String restText) {
		System.out.println(restText.startsWith(commandStarter));
		if(restText.startsWith(commandStarter)){
			restText = restText.substring(commandStarter.length());
			String[] splitText = restText.split(" ");
			ArrayList<String> args = new ArrayList<String>();
			
			if(splitText.length>1){
				for(int i = 1; i<splitText.length; i++){
					args.add(splitText[i]);
				}
			}
			else args = null;
			
			ClaireCommandHandler.runCommand(splitText[0], channel, originUserName, args, IRCWriter);
		}
	}

	public void handleJoinInput(String channel, String originUserName) {
		writeMessage(channel, "Welcome on " + channel + ", " + originUserName + ".");
	}
}
