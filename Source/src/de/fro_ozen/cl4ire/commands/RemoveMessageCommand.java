package de.fro_ozen.cl4ire.commands;

import java.io.BufferedWriter;
import java.util.ArrayList;

public class RemoveMessageCommand extends BaseCommand{

	public void run(String channel, String originUserName, ArrayList<String> args, BufferedWriter IRCWriter) {
		if(args != null){
			int index = 0;
			try {
				index = Integer.parseInt(args.get(0));
			} catch (NumberFormatException e) {
				writeMessage(channel, "Invalid argument: " + args.get(0), IRCWriter);
			}
			
			if(index > 0)MessageManager.removeMessage(originUserName, index);
			else writeMessage(channel, "Invalid argument: " + args.get(0), IRCWriter);
		}
	}

	public String getCommandGuide() {
		return "]rm <index> to remove the message at the index <index> from your inbox";
	}

}
