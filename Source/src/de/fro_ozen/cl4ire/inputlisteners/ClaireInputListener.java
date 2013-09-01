package de.fro_ozen.cl4ire.inputlisteners;

import de.fro_ozen.cl4ire.IRCInputListener;

public class ClaireInputListener extends IRCInputListener{

	public void handlePrivmsgInput(String channel, String originUserName, String restText) {
		
	}

	public void handleJoinInput(String channel, String originUserName) {
		writeMessage(channel, "Welcome on " + channel + ", " + originUserName + ".");
	}

}
