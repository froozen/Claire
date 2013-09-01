package de.fro_ozen.cl4ire.commands;

import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.Random;

public class RollCommand extends BaseCommand{
	Random ran = new Random();
	public void run(String channel, String originUserName, ArrayList<String> args, BufferedWriter IRCWriter) {
		System.out.println("Roll!");
				if(args != null){
					int rollLimit = 0;
					try{
						rollLimit = Integer.parseInt(args.get(0));
					}catch(NumberFormatException e){}
					
					if(rollLimit > 0){
						postText(channel,  " You rolled: " + (ran.nextInt(rollLimit) + 1) , IRCWriter);
					}
					else{
						postText(channel, "Usage of roll: ]roll <positive Integer>", IRCWriter);
					}
				}
				else postText(channel, "Usage of roll: ]roll <positive Integer>", IRCWriter);
	}

}
