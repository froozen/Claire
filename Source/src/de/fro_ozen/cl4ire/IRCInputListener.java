package de.fro_ozen.cl4ire;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

public abstract class IRCInputListener {
	public BufferedWriter IRCWriter;
	public String ownNickName;
	private boolean firstJoin = true;

	public abstract void handlePrivmsgInput(String channel, String originUserName, String restText);
	public abstract void handleJoinInput(String channel, String originUserName);

	public void handleInput(String IRCInput) {
		String[] inputSplit = IRCInput.split(" " );
		boolean number = true;

		ArrayList<String> avoidSignalType = new ArrayList<String>();
		avoidSignalType.add("PONG");
		avoidSignalType.add("NOTICE");
		avoidSignalType.add("MODE");

		String signalType = inputSplit[1];

		try{Integer.parseInt(signalType);}
		catch(NumberFormatException nfe){number = false;}

		if(!number && !avoidSignalType.contains(signalType)){
			String channel = inputSplit[2];
			String originUserName = inputSplit[0].substring(1, inputSplit[0].indexOf('!'));

			if(signalType.equals("PRIVMSG")){
				String restText = IRCInput.substring(IRCInput.indexOf(channel) + channel.length() + 2);
				if(channel.equals(ownNickName))channel = originUserName;
				handlePrivmsgInput(channel, originUserName, restText);
			}

			else if(signalType.equals("JOIN")){
				if(firstJoin){
					if(ownNickName == null){
						if(!originUserName.startsWith("@"))ownNickName = originUserName.substring(1);
						else ownNickName = originUserName;
					}
					else if (!ownNickName.equals(originUserName) && !ownNickName.equals(originUserName.substring(1))){
						firstJoin = false;
					}
				}
				if(!originUserName.equals(ownNickName)){
					UserManager.createUser(originUserName);
					writeCommand("WHOIS " + originUserName);
					handleJoinInput(channel, originUserName);
				}
				else if(!firstJoin){
					writeMessage(channel, "My name is Cl4ire, i'm a chatbot made by fro_ozen.");
				}

			}

			else if(signalType.equals("PART")){
				UserManager.removeUserChannel(originUserName, channel);
			}

			else if(signalType.equals("QUIT")){
				UserManager.removeUser(originUserName);
			}
			else if(signalType.equals("NICK")){
				String newNick = inputSplit[2].substring(1);
				UserManager.renameUser(originUserName, newNick);
			}
		}
		else{
			if(signalType.equals("319")){
				ArrayList<String> channels = new ArrayList<String>();
				String channel = "";
				
				for(int i = 4; i<inputSplit.length; i++){
					if(i == 4)channel = inputSplit[4].substring(1);
					else channel = inputSplit[i];
					if(channel.startsWith("@"))channel = channel.substring(1);
					
					channels.add(channel);
				}

				UserManager.setUserChannels(inputSplit[3], channels);
			}

			if(signalType.equals("353")){
				ArrayList<String> names = new ArrayList<String>();

				
				for(int i = 5; i<inputSplit.length; i++){
					if(i == 5)names.add(inputSplit[i].substring(1));
					else names.add(inputSplit[i]);
				}

				for(String name:names){
					if(!name.equals(ownNickName)){
						if(name.startsWith("@"))name = name.substring(1);
						UserManager.createUser(name);
						writeCommand("WHOIS " + name);
					}
				}
			}
		}
	}

	public void writeMessage(String target, String message){
		if(IRCWriter != null){
			try {
				IRCWriter.write("PRIVMSG " + target + " :" + message + "\r\n");
				IRCWriter.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else System.out.println("IRCWriter is missing!");
	}

	public void writeCommand(String command){
		if(IRCWriter != null){
			try {
				IRCWriter.write(command + "\r\n");
				IRCWriter.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else System.out.println("IRCWriter is missing!");
	}

	public void setIRCWriter(BufferedWriter IRCWriter){
		this.IRCWriter = IRCWriter;
	}

}
