package de.fro_ozen.cl4ire.commands;

import java.util.ArrayList;

public class MessageManager {
	private static ArrayList<Message> messageList = new ArrayList<Message>();
	
	public static void addMessage(String content, String receiver, String author){
		messageList.add(new Message(content, receiver, author));
	}
	
	public static void addMessage(Message message){
		messageList.add(message);
	}
	
	public static ArrayList<Message> getMessages(String nickName){
		ArrayList<Message> messages = new ArrayList<Message>();
		for(Message msg:messageList)if(msg.receiver.equals(nickName))messages.add(msg);
		return messages;
	}
	
	public static void removeMessages(String nickName){
		ArrayList<Message> messages = new ArrayList<Message>();
		for(Message msg:messageList)if(msg.receiver.equals(nickName))messages.add(msg);
		messageList.removeAll(messages);
	}
}
