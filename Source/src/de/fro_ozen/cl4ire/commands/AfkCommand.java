package de.fro_ozen.cl4ire.commands;

import java.io.BufferedWriter;
import java.util.ArrayList;

import de.fro_ozen.cl4ire.UserManager;

public class AfkCommand extends BaseCommand{

	public void run(String channel, String originUserName, ArrayList<String> args, BufferedWriter IRCWriter) {
		if(args == null){
			UserManager.setAfk(originUserName, true);
			if(UserManager.getUserChannels(originUserName) != null){
				for(String channelName:UserManager.getUserChannels(originUserName)){
					if(channel.equals(channelName))postText(channelName, "You are now afk.", IRCWriter);
					else postText(channelName, originUserName + " is now afk.", IRCWriter);
				}
			}
		}
		else{
			if(UserManager.isAfk(args.get(0)))postText(channel, args.get(0) + " is currently afk.", IRCWriter);
			else postText(channel, args.get(0) + " is not afk.", IRCWriter);
		}
	}

}
