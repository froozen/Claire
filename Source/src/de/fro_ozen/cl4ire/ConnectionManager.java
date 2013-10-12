package de.fro_ozen.cl4ire;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import de.fro_ozen.cl4ire.Connection.InputListenerType;
import de.fro_ozen.cl4ire.commands.MessageManager;

public class ConnectionManager {
	public static ArrayList <Connection> connections;
	private static String filePath;

	static{
		connections = new ArrayList<Connection>();
	}

	public static void createConnection(String serverName, String[] channels, String nickName, InputListenerType type){
		Connection connection = new Connection(serverName, channels, nickName, type);
		connections.add(connection);
		connection.start();
	}

	public static void initConnections(){
		String runningDirectory = new Connection().getFilePath();

		try{
			if(runningDirectory.endsWith(".jar"))runningDirectory = runningDirectory.substring(0, runningDirectory.lastIndexOf(File.separatorChar) + 1);
			else runningDirectory += ".." + File.separator;

			filePath = runningDirectory + "files" + File.separator + "connections.txt";
			MessageManager.setFilePath(runningDirectory);

			File connectionsFile = new File(filePath);
			if(connectionsFile.isFile()){
				BufferedReader connectionsFileReader = new BufferedReader(new FileReader(connectionsFile));

				String connectionLine;
				while((connectionLine = connectionsFileReader.readLine()) != null){
					String[] connectionLineSplit = connectionLine.split(" ");
					if(connectionLineSplit.length > 2){
						String[] connectionChannels = new String[connectionLineSplit.length - 2];
						for(int i = 2; i<connectionLineSplit.length; i++){
							connectionChannels[i - 2] = connectionLineSplit[i];
						}

						createConnection(connectionLineSplit[0], connectionChannels, connectionLineSplit[1], InputListenerType.CLAIRE);
					}
				}
				connectionsFileReader.close();
			}
		}
		catch (IOException e) {e.printStackTrace();}
	}
}
