package de.fro_ozen.cl4ire.commands;

import java.io.BufferedWriter;
import java.util.ArrayList;

import de.fro_ozen.cl4ire.User;
import de.fro_ozen.cl4ire.UserManager;

public class MessageCommand extends BaseCommand{

	public void run(String channel, String originUserName, ArrayList<String> args, BufferedWriter IRCWriter){
		if(args != null && args.size()>1){
			String messageString = "";
			
			for(int i = 1; i<args.size(); i++){
				messageString += args.get(i);
				if(i != args.size() - 1)messageString += " ";
			}
			
			Message message = new Message(messageString, args.get(0), originUserName);
			
			if(UserManager.getStatus(message.receiver) == User.StatusType.AFK){
				MessageManager.addMessage(message);
				writeMessage(channel, message.receiver + " is currently afk. Message will be delivered.", IRCWriter);
			}
			else if(UserManager.getStatus(message.receiver) == User.StatusType.OFFLINE){
				MessageManager.addMessage(message);
				writeMessage(channel, message.receiver + " is currently offline. Message will be delivered.", IRCWriter);
			}
			else if(UserManager.getStatus(message.receiver) == User.StatusType.ONLINE){
				writeMessage(message.receiver, message.toString(), IRCWriter);
				writeMessage(channel, message.receiver + " is not afk at the moment. The message was delivered!", IRCWriter);
			}
		}
	}

	public String getCommandGuide() {
		return "]message <target> <content> to send a message with content <content> to <target>";
	}
	
}
