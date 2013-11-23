package de.fro_ozen.cl4ire;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;

public class ConnectionTester extends Thread{
	private Connection targetConnection;
	private BufferedWriter targetWriter;
	
	@SuppressWarnings("deprecation")
	public ConnectionTester(Connection target){
		targetConnection = target;
		
		try {
			targetWriter = new BufferedWriter(new OutputStreamWriter(target.connectionSocket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Date now = new Date();
		System.out.println("ConnectionTester.ConnectionTester() :: " + now.getHours() + ":" + now.getMinutes());
	}
	
	public void run(){
		while(true){
			System.out.println("Testing Connection");
			try {
				Socket s = new Socket(targetConnection.serverName, 6667);
				s.close();
				
				targetWriter.write("PING test\r\n");
				targetWriter.flush();
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
