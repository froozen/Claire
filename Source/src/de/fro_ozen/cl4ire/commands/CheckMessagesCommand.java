package de.fro_ozen.cl4ire.commands;

import java.io.BufferedWriter;
import java.util.ArrayList;

public class CheckMessagesCommand extends BaseCommand{
	public void run(String channel, String originUserName, ArrayList<String> args, BufferedWriter IRCWriter) {
		if(MessageManager.getMessages(originUserName) != null && MessageManager.getMessages(originUserName).size()>0){
			ArrayList<Message> unreadMessages = new ArrayList<Message>();
			ArrayList<Message> readMessages = new ArrayList<Message>();
			ArrayList<Message> messages = MessageManager.getMessages(originUserName);
			
			for(Message msg:messages){
				if(msg.unread)unreadMessages.add(msg);
				else readMessages.add(msg);
			}
			
			if(unreadMessages.size()>0){
				writeMessage(originUserName, "Unread messages:", IRCWriter);
				for(Message msg:unreadMessages){
					writeMessage(originUserName, (messages.indexOf(msg) + 1) + ". " + msg.toString(), IRCWriter);
					msg.unread = false;
				}
			}
			MessageManager.saveMessageList();
			
			if(readMessages.size()>0){
				writeMessage(originUserName, "Read messages:", IRCWriter);
				for(Message msg:readMessages)writeMessage(originUserName, (messages.indexOf(msg) + 1) + ". " + msg.toString(), IRCWriter);
			}
		}
		else writeMessage(originUserName, "There are no messages for you.", IRCWriter);
	}

	public String getCommandGuide() {
		return "]checkmessages to see all your new messages";
	}
}
