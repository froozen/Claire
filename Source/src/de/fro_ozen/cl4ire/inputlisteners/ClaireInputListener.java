package de.fro_ozen.cl4ire.inputlisteners;

import java.util.ArrayList;

import de.fro_ozen.cl4ire.IRCInputListener;
import de.fro_ozen.cl4ire.MessageTemplates;
import de.fro_ozen.cl4ire.User;
import de.fro_ozen.cl4ire.UserManager;
import de.fro_ozen.cl4ire.commands.ClaireCommandHandler;
import de.fro_ozen.cl4ire.commands.Message;
import de.fro_ozen.cl4ire.commands.MessageManager;

public class ClaireInputListener extends IRCInputListener{
	private final String commandStarter = "]";

	public void handlePrivmsgInput(String channel, String originUserName, String restText) {
		System.out.println("[" + originUserName + "]: " + restText);
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
							writeMessage(channelName, MessageTemplates.formatMessage(MessageTemplates.backMessage, originUserName, channel, 0, null));
						}
					}
					
					int messageCount = 0;
					for(Message msg:MessageManager.getMessages(originUserName))if(msg.unread)messageCount++;
					if(messageCount > 0)writeMessage(originUserName, MessageTemplates.formatMessage(MessageTemplates.newMessagesMessage, originUserName, channel, messageCount, null));
				}
			}
		}
	}

	public void handleJoinInput(String channel, String originUserName) {
		int messageCount = 0;
		String welcomeMessage = "";

		//Count unread messages
		for(Message msg:MessageManager.getMessages(originUserName))if(msg.unread)messageCount++;

		if(messageCount > 0) welcomeMessage = MessageTemplates.formatMessage(MessageTemplates.getWelcomeMessage(originUserName) + MessageTemplates.welcomeMessageNewMessages, originUserName, channel, messageCount, null);
		else welcomeMessage = MessageTemplates.formatMessage(MessageTemplates.getWelcomeMessage(originUserName), originUserName, channel, messageCount, null);
		
		writeMessage(channel, welcomeMessage);

	}
}
