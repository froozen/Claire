package de.fro_ozen.cl4ire;

import de.fro_ozen.cl4ire.Connection.InputListenerType;

public class ExecuteableMain {

	public static void main(String[] args) {
		String[] channelList = {"#teamfirepowder"};
		ConnectionManager.createConnection("irc.freenode.net", channelList, "Cl4ire", InputListenerType.CLAIRE);
	}

}
