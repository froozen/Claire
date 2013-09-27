package de.fro_ozen.cl4ire.commands;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

@SuppressWarnings("resource")
public class MessageManager {
	private static ArrayList<Message> messageList = new ArrayList<Message>();

	static{
		try{
			File messageFile = new File("messages.txt");
			if(!messageFile.isFile()){
				System.out.println("Error: File not fund: messages.txt");
			}
			else{
				BufferedReader messageFileReader = new BufferedReader(new FileReader(messageFile.getPath()));

				String readLine = "";
				while((readLine = messageFileReader.readLine()) != null){
					addMessage(readLine);
				}
			}
		}
		catch (IOException e) {e.printStackTrace();}
	}

	public static void addMessage(String content, String receiver, String author){
		messageList.add(new Message(content, receiver, author));
		saveMessageList();
	}

	public static void addMessage(Message message){
		messageList.add(message);
		saveMessageList();
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
		saveMessageList();
	}

	public static void addMessage(String fileContent){
		Message message = new Message(fileContent);
		messageList.add(message);
	}

	private static void saveMessageList(){
		try{
			File messageFile = new File(".." + File.separator + "messages.txt");
			if(!messageFile.isFile()){
				System.out.println("Error: File not fund: messages.txt");
			}
			else{
				BufferedWriter messageFileWriter = new BufferedWriter(new FileWriter(messageFile));

				for(Message msg:messageList){
					messageFileWriter.write(msg.toSaveFormat());
				}
				messageFileWriter.close();
			}
		}
		catch (IOException e) {e.printStackTrace();}
	}
}
