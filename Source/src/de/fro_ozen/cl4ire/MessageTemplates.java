package de.fro_ozen.cl4ire;

public class MessageTemplates {
	public static String welcomeMessage = "Welcome on %c, %n.";
	public static String welcomeMessageNewMessages = "Welcome on %c, %n. You have %M new messages.";
	public static String afkMessage = "%n is now afk.";
	public static String backMessage = "%n is back!";
	public static String newMessagesMessage = "You got %M new messages!";
	public static String statusAfkMessage = "%n is afk.";
	public static String statusAfkMessageMessage = "%n is afk but left a message: %m";
	public static String statusOnlineMessage = "%n is available.";
	public static String statusOfflineMessage = "%n is offline.";
	public static String introductionMessage = "fro_ozen-bot localhost localhost : A IRC chatbot by fro_ozen";


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
}
