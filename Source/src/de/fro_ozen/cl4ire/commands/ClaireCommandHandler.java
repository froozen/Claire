package de.fro_ozen.cl4ire.commands;

import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class ClaireCommandHandler {
	private static HashMap<String, BaseCommand> commands;
	
	public static void runCommand(String command, String channel, String originUserName, ArrayList<String> args, BufferedWriter IRCWriter){
		if(commands == null)initialize();
		
		if(commands.containsKey(command))commands.get(command).run(channel, originUserName, args, IRCWriter);
	}
	
	private static void initialize(){
		commands = new HashMap<String, BaseCommand>();
		commands.put("roll", new RollCommand());
		commands.put("afk", new AfkCommand());
	}
}
