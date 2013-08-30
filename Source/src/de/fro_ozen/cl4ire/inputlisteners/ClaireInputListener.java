package de.fro_ozen.cl4ire.inputlisteners;

import de.fro_ozen.cl4ire.IRCInputListener;

public class ClaireInputListener extends IRCInputListener{

	public void handleInput(String IRCInput) {
		System.out.println("Connection Input: " + IRCInput);
	}

}
