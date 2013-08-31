package de.fro_ozen.cl4ire.inputlisteners;

import de.fro_ozen.cl4ire.IRCInputListener;

public abstract class AnalyseInputListener extends IRCInputListener{

	public void handleInput(String IRCInput) {
		String[] inputSplit = IRCInput.split(" " );
		
		if(inputSplit[1].equals("PRIVMSG")){
			String channel = inputSplit[2];
			String originUserName = inputSplit[0].substring(1, inputSplit[0].indexOf('!'));
			String restText = IRCInput.substring(IRCInput.indexOf(channel) + channel.length() + 2);
			
			handlePrivmsgInput(channel, originUserName, restText);
		}
		
	}

	public abstract void handlePrivmsgInput(String channel, String originUserName, String restText);
	
}
