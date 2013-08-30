package de.fro_ozen.cl4ire.inputlisteners;

import de.fro_ozen.cl4ire.IRCInputListener;

public abstract class AnalyseInputListener extends IRCInputListener{

	public void handleInput(String IRCInput) {
		String channelName, originUserName, commandType, restText, extractString;
		
//		originUserName = IRCInput.substring(1, IRCInput.indexOf('!'));
	}

	public abstract void handleAdvancedInput(String channelName, String originUserName, String commandType, String restText);
	
}
