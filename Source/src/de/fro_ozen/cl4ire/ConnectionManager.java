package de.fro_ozen.cl4ire;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
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

	@SuppressWarnings("deprecation")
	public static void removeConnection(Connection connection){
		try {
			connection.connectionSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		connection.stop();
		connections.remove(connection);
	}

	public static void initConnections(){
		String runningDirectory = "";
		try {
			runningDirectory = new File(ConnectionManager.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath();
		} catch (URISyntaxException e1) {e1.printStackTrace();}

		try{
			if(runningDirectory.endsWith(".jar"))runningDirectory = runningDirectory.substring(0, runningDirectory.lastIndexOf(File.separatorChar) + 1);
			else runningDirectory += File.separator + ".." + File.separator;

			filePath = runningDirectory + "files" + File.separator + "connections.txt";
			MessageManager.setFilePath(runningDirectory);
			MessageTemplates.loadTemplates(runningDirectory);

			File connectionsFile = new File(filePath);
			if(connectionsFile.isFile()){
				BufferedReader connectionsFileReader = new BufferedReader(new FileReader(connectionsFile));

				String connectionLine;
				while((connectionLine = connectionsFileReader.readLine()) != null){
					if(!connectionLine.startsWith("#")){
						String[] connectionLineSplit = connectionLine.split(" ");
						if(connectionLineSplit.length > 2){
							String[] connectionChannels = new String[connectionLineSplit.length - 2];
							for(int i = 2; i<connectionLineSplit.length; i++){
								connectionChannels[i - 2] = connectionLineSplit[i];
							}

							createConnection(connectionLineSplit[0], connectionChannels, connectionLineSplit[1], InputListenerType.CLAIRE);
						}
					}
				}
				connectionsFileReader.close();
			}
			else System.out.println("Can't find connections.txt at: " + connectionsFile.getAbsolutePath());
		}
		catch (IOException e) {e.printStackTrace();}
	}
}
