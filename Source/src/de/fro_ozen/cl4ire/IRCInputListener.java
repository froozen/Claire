package de.fro_ozen.cl4ire;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

public abstract class IRCInputListener {
	public BufferedWriter IRCWriter;

	public void handleInput(String IRCInput) {
		String[] inputSplit = IRCInput.split(" " );
		boolean number = true;
		
		ArrayList<String> avoidSignalType = new ArrayList<String>();
		avoidSignalType.add("PONG");
		avoidSignalType.add("NOTICE");

		String signalType = inputSplit[1];

		try{Integer.parseInt(signalType);}
		catch(NumberFormatException nfe){number = false;}

		if(!number && !avoidSignalType.contains(signalType)){
			String channel = inputSplit[2];
			String originUserName = inputSplit[0].substring(1, inputSplit[0].indexOf('!'));

			if(signalType.equals("PRIVMSG")){
				String restText = IRCInput.substring(IRCInput.indexOf(channel) + channel.length() + 2);
				handlePrivmsgInput(channel, originUserName, restText);
			}

			else if(signalType.equals("JOIN")){
				UserManager.createUser(originUserName);
				try {
					IRCWriter.write("WHOIS " +  originUserName + "\r\n");
					IRCWriter.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			else if(signalType.equals("PART")){
				UserManager.removeUserChannel(originUserName, channel);
			}
			
			else if(signalType.equals("QUIT")){
				UserManager.removeUser(originUserName);
			}
		}
		else{
			if(signalType.equals("319")){
				ArrayList<String> channels = new ArrayList<String>();

				for(int i = 4; i<inputSplit.length; i++){
					if(i == 4)channels.add(inputSplit[4].substring(1));
					else channels.add(inputSplit[i]);
				}

				UserManager.setUserChannels(inputSplit[3], channels);
			}
		}
	}

	public abstract void handlePrivmsgInput(String channel, String originUserName, String restText);

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

	public void setIRCWriter(BufferedWriter IRCWriter){
		this.IRCWriter = IRCWriter;
	}

}
