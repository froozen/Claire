package de.fro_ozen.cl4ire.commands;

import java.io.BufferedWriter;
import java.util.ArrayList;

public class InviteCommand extends BaseCommand{

	public void run(String channel, String originUserName, ArrayList<String> args, BufferedWriter IRCWriter) {
		if(args != null){
			if(args.get(0).startsWith("#")){
				writeCommand("JOIN " + args.get(0), IRCWriter);
			}
			else writeMessage(channel, "Usage of the ]invite command: ]invite <channelname>", IRCWriter);
		}
		else writeMessage(channel, "Usage of the ]invite command: ]invite <channelname>", IRCWriter);
	}

}
