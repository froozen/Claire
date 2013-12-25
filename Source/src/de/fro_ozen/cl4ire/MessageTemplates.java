package de.fro_ozen.cl4ire;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class MessageTemplates {
	public static HashMap<String, String> welcomeMessages;
	public static HashMap<String, String> messageTemplates;
	private static String runningDirectory;

	public static String formatMessage(String templateName, String name, String channel, int messageCount, String afkMessage){
		String formatedMessage;

		//Fetch template
		if(!(templateName.equals("welcomeMessage") || templateName.equals("welcomeMessageMessage"))){
			if(messageTemplates.get(templateName) != null)formatedMessage = messageTemplates.get(templateName);
			else formatedMessage = "Template not found: " + templateName;
		}
		else{
			formatedMessage = getWelcomeMessage(name);
			if(templateName.equals("welcomeMessageMessage")) formatedMessage += messageTemplates.get("welcomeMessageMessage");
		}

		//Replace %n with name
		while(formatedMessage.contains("%n")){
			String formatedMessageStart = formatedMessage.substring(0, formatedMessage.indexOf("%n"));
			formatedMessage = formatedMessageStart + name + formatedMessage.substring(formatedMessage.indexOf("%n") + 2);
		}

		//Replace %c with channel
		while(formatedMessage.contains("%c")){
			String formatedMessageStart = formatedMessage.substring(0, formatedMessage.indexOf("%c"));
			formatedMessage = formatedMessageStart + channel + formatedMessage.substring(formatedMessage.indexOf("%c") + 2);
		}

		//Replace %M with messageCount
		while(formatedMessage.contains("%M")){
			String formatedMessageStart = formatedMessage.substring(0, formatedMessage.indexOf("%M"));
			formatedMessage = formatedMessageStart + messageCount + formatedMessage.substring(formatedMessage.indexOf("%M") + 2);
		}

		//Replace %m with afkMessage
		while(formatedMessage.contains("%m")){
			String formatedMessageStart = formatedMessage.substring(0, formatedMessage.indexOf("%m"));
			formatedMessage = formatedMessageStart + afkMessage + formatedMessage.substring(formatedMessage.indexOf("%m") + 2);
		}

		return formatedMessage;
	}

	private static String getWelcomeMessage(String name){
		if(welcomeMessages.get(name) != null)return welcomeMessages.get(name);
		else return messageTemplates.get("welcomeMessage");
	}

	public static void loadTemplates(String runningDirectoryArgument){
		runningDirectory = runningDirectoryArgument;

		messageTemplates = new HashMap<String, String>();

		//Hardcoded default messages
		messageTemplates.put("welcomeMessage", "Welcome on %c, %n.");
		messageTemplates.put("welcomeMessageNewMessages", " You have %M new messages.");
		messageTemplates.put("afkMessage", "%n is now afk.");
		messageTemplates.put("backMessage", "%n is back!");
		messageTemplates.put("newMessagesMessage", "You got %M new messages!");
		messageTemplates.put("statusAfkMessage", "%n is afk.");
		messageTemplates.put("statusAfkMessageMessage", "%n is afk but left a message: %m");
		messageTemplates.put("statusOnlineMessage", "%n is available.");
		messageTemplates.put("statusOfflineMessage", "%n is offline.");
		messageTemplates.put("introductionMessage", "My name is Cl4ire, i'm a chatbot made by fro_ozen.");


		//Load templates from file
		File templateFile = new File(runningDirectory + "files" + File.separator + "templates.txt");
		if(templateFile.isFile()){
			BufferedReader templateFileReader;
			try {
				templateFileReader = new BufferedReader(new FileReader(templateFile));
				String templateFileLine = "";

				while((templateFileLine = templateFileReader.readLine()) != null){
					if(!templateFileLine.startsWith("#")){
						if(templateFileLine.split(" ").length > 1){
							String[] templateFileLineSplit = templateFileLine.split(" ");
							messageTemplates.put(templateFileLineSplit[0], templateFileLine.substring(templateFileLineSplit[0].length() + 1));
						}
						else if(!templateFileLine.equals("")) System.out.println("Error: Can't use: " + templateFileLine);
					}
				}

				templateFileReader.close();
			} catch (IOException e) {e.printStackTrace();}

		}
		else System.out.println("Can't find file: " + templateFile.getAbsolutePath());

		welcomeMessages = new HashMap<String, String>();
		File welcomeMessageFile = new File(runningDirectory + "files" + File.separator + "welcomeMessages.txt");
		if(welcomeMessageFile.isFile()){
			BufferedReader welcomeMessageFileReader;
			try{
				welcomeMessageFileReader = new BufferedReader(new FileReader(welcomeMessageFile));
				String welcomeMessageFileLine = "";

				while((welcomeMessageFileLine = welcomeMessageFileReader.readLine()) != null){
					if(welcomeMessageFileLine.split(" ").length > 1){
						welcomeMessages.put(welcomeMessageFileLine.split(" ")[0], welcomeMessageFileLine.substring(welcomeMessageFileLine.split(" ")[0].length() + 1));
					}
					else if(!welcomeMessageFileLine.equals("")) System.out.println("Error: Can't use: " + welcomeMessageFileLine);
				}
				welcomeMessageFileReader.close();
			}
			catch (IOException e) {e.printStackTrace();}

		}
		else System.out.println("Can't find file: " + welcomeMessageFile.getAbsolutePath());
	}

	public static void setWelcomeMessage(String nick, String message){
		welcomeMessages.put(nick, message);
		saveWelcomeMessages();
	}

	private static void saveWelcomeMessages(){
		File welcomeMessageFile = new File(runningDirectory + "files" + File.separator + "welcomeMessages.txt");
		if(welcomeMessageFile.isFile()){
			BufferedWriter welcomeMessageFileWriter;
			try{
				welcomeMessageFileWriter = new BufferedWriter(new FileWriter(welcomeMessageFile));

				for(String nick:welcomeMessages.keySet()){
					welcomeMessageFileWriter.write(nick + " " + welcomeMessages.get(nick));
					welcomeMessageFileWriter.newLine();
				}
				welcomeMessageFileWriter.close();
			}
			catch (IOException e) {e.printStackTrace();}
		}
		else System.out.println("Can't find file: " + welcomeMessageFile.getAbsolutePath());
	}
}
