package de.fro_ozen.cl4ire.commands;

import java.io.BufferedWriter;
import java.util.ArrayList;

import de.fro_ozen.cl4ire.User;
import de.fro_ozen.cl4ire.UserManager;

public class AfkCommand extends BaseCommand{

	public void run(String channel, String originUserName, ArrayList<String> args, BufferedWriter IRCWriter) {
		if(args == null){
			UserManager.setStatus(originUserName, User.StatusType.AFK);
			
			if(UserManager.getUserChannels(originUserName) != null){
				for(String channelName:UserManager.getUserChannels(originUserName)){
					if(channel.equals(channelName))writeMessage(channelName, "You are now afk.", IRCWriter);
					else writeMessage(channelName, originUserName + " is now afk.", IRCWriter);
				}
			}
		}
		else{
			if(UserManager.getStatus(args.get(0)) == User.StatusType.AFK)writeMessage(channel, args.get(0) + " is currently afk.", IRCWriter);
			else if(UserManager.getStatus(args.get(0)) == User.StatusType.ONLINE) writeMessage(channel, args.get(0) + " is not afk.", IRCWriter);
			else if(UserManager.getStatus(args.get(0)) == User.StatusType.OFFLINE) writeMessage(channel, args.get(0) + " is offline.", IRCWriter);
		}
	}

	@Override
	public String getCommandGuide() {
		return "]afk to mark yourself as afk, write something to indicate that you are back";
	}

}