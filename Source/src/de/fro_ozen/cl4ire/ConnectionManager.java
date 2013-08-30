package de.fro_ozen.cl4ire;

import java.util.ArrayList;
import de.fro_ozen.cl4ire.Connection.InputListenerType;

public class ConnectionManager {
	public static ArrayList <Connection> connections;
	
	public static void createConnection(String serverName, String[] channels, String nickName, InputListenerType type){
		if(connections == null)connections = new ArrayList<Connection>();
		
		Connection connection = new Connection(serverName, channels, nickName, type);
		connections.add(connection);
		connection.start();
	}
}
