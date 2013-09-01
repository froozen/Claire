package de.fro_ozen.cl4ire;

import de.fro_ozen.cl4ire.Connection.InputListenerType;

public class Main {

	public static void main(String[] args) {
		String[] channelList = {"#bottest"};
		ConnectionManager.createConnection("irc.freenode.net", channelList, "Cl4ire_", InputListenerType.CLAIRE);
	}
}
