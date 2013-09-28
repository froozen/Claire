package de.fro_ozen.cl4ire.commands;

import java.io.BufferedWriter;
import java.util.ArrayList;

import de.fro_ozen.cl4ire.User.StatusType;
import de.fro_ozen.cl4ire.UserManager;

public class StatusCommand extends BaseCommand{

	public void run(String channel, String originUserName, ArrayList<String> args, BufferedWriter IRCWriter) {
		if(args != null && args.size()>0){
			StatusType status = UserManager.getStatus(args.get(0));
			if(status == StatusType.AFK)writeMessage(channel, args.get(0) + " is currently afk.", IRCWriter);
			else if(status == StatusType.ONLINE)writeMessage(channel, args.get(0) + " is available.", IRCWriter);
			else if(status == StatusType.OFFLINE)writeMessage(channel, args.get(0) + " is not here.", IRCWriter);
		}
		else writeMessage(channel, "Usage of the ]status command: ]status <username>", IRCWriter);
	}
}
