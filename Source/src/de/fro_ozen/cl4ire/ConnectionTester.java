package de.fro_ozen.cl4ire;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

public class ConnectionTester extends Thread{
	private Connection targetConnection;
	
	@SuppressWarnings("deprecation")
	public ConnectionTester(Connection target){
		targetConnection = target;
		
		Date now = new Date();
		System.out.println("ConnectionTester.ConnectionTester() :: " + now.getHours() + ":" + now.getMinutes());
	}
	
	public void run(){
		while(true){
			try {
				Socket s = new Socket(targetConnection.serverName, 6667);
				s.close();
			} catch (IOException e) {
				System.out.println("Conenction lost at " + System.currentTimeMillis());
//				e.printStackTrace();
				break;
			}
			
			try {Thread.sleep(30000);
			} catch (InterruptedException e) {e.printStackTrace();}
		}
		ConnectionManager.createConnection(targetConnection.serverName, targetConnection.channels, targetConnection.nickName, targetConnection.type);
		ConnectionManager.removeConnection(targetConnection);
	}
}
