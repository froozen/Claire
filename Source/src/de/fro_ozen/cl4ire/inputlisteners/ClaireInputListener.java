package de.fro_ozen.cl4ire.inputlisteners;

import java.util.ArrayList;

import de.fro_ozen.cl4ire.IRCInputListener;
import de.fro_ozen.cl4ire.User;
import de.fro_ozen.cl4ire.UserManager;
import de.fro_ozen.cl4ire.commands.ClaireCommandHandler;
import de.fro_ozen.cl4ire.commands.Message;
import de.fro_ozen.cl4ire.commands.MessageManager;

public class ClaireInputListener extends IRCInputListener{
	private final String commandStarter = "]";

	public void handlePrivmsgInput(String channel, String originUserName, String restText) {
		if(restText.startsWith(commandStarter)){
			restText = restText.substring(commandStarter.length());
			String[] splitText = restText.split(" ");
			ArrayList<String> args = new ArrayList<String>();
			
			if(splitText.length>1){
				for(int i = 1; i<splitText.length; i++){
					args.add(splitText[i]);
				}
			}
			else args = null;
			
			System.out.println("ClaireInputListener.handlePrivmsgInput() :: Command: " + splitText[0] + " by " + originUserName + " on " + channel);
			ClaireCommandHandler.runCommand(splitText[0], channel, originUserName, args, IRCWriter);
		}
		else{
			if(!channel.equals(originUserName)){
				if(UserManager.getStatus(originUserName) == User.StatusType.AFK){
					//AFK user writes something
					UserManager.setUserStatus(originUserName, User.StatusType.ONLINE);
					UserManager.setUserAfkMessage(originUserName, "");
					if(UserManager.getUserChannels(originUserName) != null){
						for(String channelName:UserManager.getUserChannels(originUserName)){
							writeMessage(channelName, originUserName + " is back!");
						}
					}
					
					if(MessageManager.getMessages(originUserName).size()>0)writeMessage(originUserName, "You got mail!");
				}
			}
		}
	}

	public void handleJoinInput(String channel, String originUserName) {
		int messageCount = 0;
		
		//Count unread messages
		for(Message msg:MessageManager.getMessages(originUserName))if(msg.unread)messageCount++;
		
		if(messageCount > 1) writeMessage(channel, "Welcome on " + channel + ", " + originUserName + ". You have " + messageCount + " new messages!");
		else if(messageCount == 1) writeMessage(channel, "Welcome on " + channel + ", " + originUserName + ". You have a new message!");
		else writeMessage(channel, "Welcome on " + channel + ", " + originUserName + ".");
	}
}
