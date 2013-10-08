package de.fro_ozen.cl4ire.commands;

import java.io.BufferedWriter;
import java.util.ArrayList;

import de.fro_ozen.cl4ire.User;
import de.fro_ozen.cl4ire.UserManager;

public class AfkCommand extends BaseCommand{

	public void run(String channel, String originUserName, ArrayList<String> args, BufferedWriter IRCWriter) {
		if(args == null){
			UserManager.setUserStatus(originUserName, User.StatusType.AFK);

			if(UserManager.getUserChannels(originUserName) != null){
				for(String channelName:UserManager.getUserChannels(originUserName)){
					if(channel.equals(channelName))writeMessage(channelName, "You are now afk.", IRCWriter);
					else writeMessage(channelName, originUserName + " is now afk.", IRCWriter);
				}
			}
		}
		else{
			String afkMessage = "";
			
			for(String s:args){
				afkMessage += s + " ";
			}
			afkMessage = afkMessage.substring(0, afkMessage.length() - 1);
			
			UserManager.setUserStatus(originUserName, User.StatusType.AFK);
			UserManager.setUserAfkMessage(originUserName, afkMessage);

			if(UserManager.getUserChannels(originUserName) != null){
				for(String channelName:UserManager.getUserChannels(originUserName)){
					if(channel.equals(channelName))writeMessage(channelName, "You are now afk.", IRCWriter);
					else writeMessage(channelName, originUserName + " is now afk.", IRCWriter);
				}
			}
		}
	}

	@Override
	public String getCommandGuide() {
		return "]afk to mark yourself as afk, write something to indicate that you are back";
	}

}