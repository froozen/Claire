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
	public static String welcomeMessage = "Welcome on %c, %n.";
	public static String welcomeMessageNewMessages = " You have %M new messages.";
	public static String afkMessage = "%n is now afk.";
	public static String backMessage = "%n is back!";
	public static String newMessagesMessage = "You got %M new messages!";
	public static String statusAfkMessage = "%n is afk.";
	public static String statusAfkMessageMessage = "%n is afk but left a message: %m";
	public static String statusOnlineMessage = "%n is available.";
	public static String statusOfflineMessage = "%n is offline.";
	public static String introductionMessage = "My name is Cl4ire, i'm a chatbot made by fro_ozen.";
	private static String runningDirectory;
	
	public static String formatMessage(String template, String name, String channel, int messageCount, String afkMessage){
		String formatedMessage = template;
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
	
	public static String getWelcomeMessage(String name){
		if(welcomeMessages.get(name) != null)return welcomeMessages.get(name);
		else return welcomeMessage;
	}

	public static void loadTemplates(String runningDirectoryArgument){
		runningDirectory = runningDirectoryArgument;
		
		File templateFile = new File(runningDirectory + "files" + File.separator + "templates.txt");
		if(templateFile.isFile()){
			BufferedReader templateFileReader;
			try {
				templateFileReader = new BufferedReader(new FileReader(templateFile));
				String templateFileLine = "";

				while((templateFileLine = templateFileReader.readLine()) != null){
					if(!templateFileLine.startsWith("#")){

						if(templateFileLine.startsWith("welcomeMessage=")){
							welcomeMessage = templateFileLine.substring("welcomeMessage=".length());
						}
						else if(templateFileLine.startsWith("welcomeMessageNewMessages=")){
							welcomeMessageNewMessages = templateFileLine.substring("welcomeMessageNewMessages=".length());
						}
						else if(templateFileLine.startsWith("afkMessage=")){
							afkMessage = templateFileLine.substring("afkMessage=".length());
						}
						else if(templateFileLine.startsWith("backMessage=")){
							backMessage = templateFileLine.substring("backMessage=".length());
						}
						else if(templateFileLine.startsWith("newMessagesMessage=")){
							newMessagesMessage = templateFileLine.substring("newMessagesMessage=".length());
						}
						else if(templateFileLine.startsWith("statusAfkMessage=")){
							statusAfkMessage = templateFileLine.substring("statusAfkMessage=".length());
						}
						else if(templateFileLine.startsWith("statusAfkMessageMessage=")){
							statusAfkMessageMessage = templateFileLine.substring("statusAfkMessageMessage=".length());
						}
						else if(templateFileLine.startsWith("statusOnlineMessage=")){
							statusOnlineMessage = templateFileLine.substring("statusOnlineMessage=".length());
						}
						else if(templateFileLine.startsWith("statusOfflineMessage=")){
							statusOfflineMessage = templateFileLine.substring("statusOfflineMessage=".length());
						}
						else if(templateFileLine.startsWith("introductionMessage=")){
							introductionMessage = templateFileLine.substring("introductionMessage=".length());
						}

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
					else if(!welcomeMessageFileLine.endsWith("")) System.out.println("Error: Can't use: " + welcomeMessageFileLine);
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
				}
				welcomeMessageFileWriter.close();
			}
			catch (IOException e) {e.printStackTrace();}
		}
		else System.out.println("Can't find file: " + welcomeMessageFile.getAbsolutePath());
	}
}
