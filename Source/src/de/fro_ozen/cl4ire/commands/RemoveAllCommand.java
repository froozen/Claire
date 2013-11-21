package de.fro_ozen.cl4ire.commands;

import java.io.BufferedWriter;
import java.util.ArrayList;

public class RemoveAllCommand extends BaseCommand{

	public void run(String channel, String originUserName, ArrayList<String> args, BufferedWriter IRCWriter) {
		MessageManager.removeAllMessages(originUserName);
		writeMessage(channel, "Your messages were removed!", IRCWriter);
	}

	public String getCommandGuide() {
		return "remove all messages that are for you";
	}

}
