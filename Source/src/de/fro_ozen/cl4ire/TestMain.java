package de.fro_ozen.cl4ire;

import java.io.File;
import java.net.URISyntaxException;

import de.fro_ozen.cl4ire.Connection.InputListenerType;
import de.fro_ozen.cl4ire.commands.MessageManager;

public class TestMain {

	public static void main(String[] args) {
		//Detect running directory to load messages from
		String runningDirectory = "";
		try {
			runningDirectory = new File(ConnectionManager.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath();
		} catch (URISyntaxException e1) {e1.printStackTrace();}
		if(runningDirectory.endsWith(".jar"))runningDirectory = runningDirectory.substring(0, runningDirectory.lastIndexOf(File.separatorChar) + 1);
		else runningDirectory += File.separator + ".." + File.separator;
		
		String[] channelList = {"#bottest"};
		MessageManager.setFilePath(runningDirectory);
		MessageTemplates.loadTemplates(runningDirectory);
		ConnectionManager.createConnection("irc.freenode.net", channelList, "testbotttt", InputListenerType.CLAIRE);
	}
}