package de.fro_ozen.cl4ire.commands;

import java.io.BufferedWriter;
import java.util.ArrayList;

import de.fro_ozen.cl4ire.User.StatusType;
import de.fro_ozen.cl4ire.MessageTemplates;
import de.fro_ozen.cl4ire.UserManager;

public class StatusCommand extends BaseCommand{

	public void run(String channel, String originUserName, ArrayList<String> args, BufferedWriter IRCWriter) {
		if(args != null && args.size()>0){
			StatusType status = UserManager.getStatus(args.get(0));
			if(status == StatusType.AFK){
				if(!UserManager.getUserAfkMessage(args.get(0)).equals(""))writeMessage(channel, MessageTemplates.formatMessage("statusAfkMessageMessage", args.get(0), channel, 0, UserManager.getUserAfkMessage(args.get(0))), IRCWriter);
				else writeMessage(channel, MessageTemplates.formatMessage("statusAfkMessage", args.get(0), channel, 0, null), IRCWriter);
			}
			else if(status == StatusType.ONLINE)writeMessage(channel, MessageTemplates.formatMessage("statusOnlineMessage", args.get(0), channel, 0, null), IRCWriter);
			else if(status == StatusType.OFFLINE)writeMessage(channel, MessageTemplates.formatMessage("statusOfflineMessage", args.get(0), channel, 0, null), IRCWriter);
		}
		else writeMessage(channel, "Usage of the ]status command: ]status <username>", IRCWriter);
	}

	public String getCommandGuide() {
		return "]status <target> to display the status of <target>";
	}
}
