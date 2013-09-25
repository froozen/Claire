package de.fro_ozen.cl4ire.commands;

import java.io.BufferedWriter;
import java.util.ArrayList;

public class CheckMessagesCommand extends BaseCommand{
	public void run(String channel, String originUserName, ArrayList<String> args, BufferedWriter IRCWriter) {
		if(MessageManager.getMessages(originUserName).size()>0){
			for(Message msg:MessageManager.getMessages(originUserName))writeMessage(channel, msg.toString(), IRCWriter);
			MessageManager.removeMessages(originUserName);
		}
		else writeMessage(channel, "There are no messages for you.", IRCWriter);
	}
}
