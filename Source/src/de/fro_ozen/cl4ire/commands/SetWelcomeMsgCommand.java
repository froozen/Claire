package de.fro_ozen.cl4ire.commands;

import java.io.BufferedWriter;
import java.util.ArrayList;

import de.fro_ozen.cl4ire.MessageTemplates;

public class SetWelcomeMsgCommand extends BaseCommand{

	public void run(String channel, String originUserName, ArrayList<String> args, BufferedWriter IRCWriter) {
		if(args != null){
			String message = "";
			
			for(String s:args)message += s + " ";
			message.substring(0, message.length() - 1);
			
			MessageTemplates.setWelcomeMessage(originUserName, message);
		}
		else writeMessage(channel, "Usage: ]welcomemsg <message> to set your welcome message to <message>", IRCWriter);
	}

	public String getCommandGuide() {
		return "]welcomemsg <message> to set your welcome message to <message>";
	}
}
