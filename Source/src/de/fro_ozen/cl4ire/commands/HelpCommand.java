package de.fro_ozen.cl4ire.commands;

import java.io.BufferedWriter;
import java.util.ArrayList;

public class HelpCommand extends BaseCommand{

	public void run(String channel, String originUserName, ArrayList<String> args, BufferedWriter IRCWriter) {
		if(args == null){
			String commandList = "";
			
			for(String s: ClaireCommandHandler.getCommands().keySet()){
				commandList += s + ", ";
			}
			
			commandList = commandList.substring(0, commandList.length() - 2);
			
			writeMessage(channel, "Available commands: " + commandList, IRCWriter);
		}
		else{
			if(ClaireCommandHandler.getCommands().containsKey(args.get(0))){
				String abbreviations = "", helpString = "";
				
				for(String s:ClaireCommandHandler.getCommandAbbreviations().keySet()){
					if(ClaireCommandHandler.getCommandAbbreviations().get(s).equals(args.get(0)))abbreviations += s + ", ";
				}
				
				helpString = "Usage of ]" + args.get(0) + ": " + ClaireCommandHandler.getCommands().get(args.get(0)).getCommandGuide();
				if(!abbreviations.equals(""))helpString += "; Abbreviations: " + abbreviations.substring(0, abbreviations.length() - 2);
				
				writeMessage(channel, helpString, IRCWriter);
			}
			else if(ClaireCommandHandler.getCommandAbbreviations().containsKey(args.get(0))){
				String abbreviations = "", helpString = "";
				String actualCommand = ClaireCommandHandler.getCommandAbbreviations().get(args.get(0));
				
				for(String s:ClaireCommandHandler.getCommandAbbreviations().keySet()){
					if(ClaireCommandHandler.getCommandAbbreviations().get(s).equals(actualCommand))abbreviations += s + ", ";
				}
				
				helpString = "Usage of ]" + actualCommand + ": " + ClaireCommandHandler.getCommands().get(actualCommand).getCommandGuide();
				if(!abbreviations.equals(""))helpString += "; Abbreviations: " + abbreviations.substring(0, abbreviations.length() - 2);
				
				writeMessage(channel, helpString, IRCWriter);
			}
			else writeMessage(channel, "I don't know this command.", IRCWriter);
		}
	}

	public String getCommandGuide() {
		return "]help for a list of all commands, ]help <commandname> for the command guide of <commandname>";
	}

}
