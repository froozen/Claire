package de.fro_ozen.cl4ire.commands;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MessageManager {
	private static ArrayList<Message> messageList = new ArrayList<Message>();
	private static String filePath;
	
	public static void setFilePath(String runningDirectory){
		filePath = runningDirectory + "files" + File.separator + "messages.txt";
		File messageFile = new File(filePath);
		if(messageFile.isFile()){
			System.out.println("Loading Messages from: " + filePath);
			
			try {
				BufferedReader messageFileReader = new BufferedReader(new FileReader(messageFile));
				
				String messageFileLine;
				while((messageFileLine = messageFileReader.readLine()) != null){
					messageList.add(new Message(messageFileLine));
				}
				messageFileReader.close();
			} catch (IOException e) {e.printStackTrace();}
		}
		else System.out.println("Can't find file: " + filePath);
	}

	public static void addMessage(String content, String receiver, String author){
		System.out.println("Created new message!");
		messageList.add(new Message(content, receiver, author));
		saveMessageList();
	}

	public static void addMessage(Message message){
		System.out.println("Created new message!");
		messageList.add(message);
		saveMessageList();
	}

	public static ArrayList<Message> getMessages(String nickName){
		ArrayList<Message> messages = new ArrayList<Message>();
		for(Message msg:messageList)if(msg.receiver.equals(nickName))messages.add(msg);
		return messages;
	}

	public static void removeMessage(String nickName, int index){
		ArrayList<Message> messages = new ArrayList<Message>();
		for(Message msg:messageList)if(msg.receiver.equals(nickName))messages.add(msg);
		if(index <= messages.size())messageList.remove(messages.get(index - 1));
		saveMessageList();
	}

	public static void addMessage(String fileContent){
		Message message = new Message(fileContent);
		messageList.add(message);
	}

	public static void saveMessageList(){
		try{
			File messageFile = new File(filePath);
			if(!messageFile.isFile()){
				System.out.println("Error: File not fund: messages.txt");
			}
			else{
				BufferedWriter messageFileWriter = new BufferedWriter(new FileWriter(messageFile));

				for(Message msg:messageList){
					messageFileWriter.write(msg.toSaveFormat());
					messageFileWriter.newLine();
				}
				messageFileWriter.close();
			}
		}
		catch (IOException e) {e.printStackTrace();}
	}
}
