package de.fro_ozen.cl4ire;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ConnectionTester extends Thread{
	private Connection targetConnection;
	private BufferedWriter targetWriter;
	
	public ConnectionTester(Connection target){
		targetConnection = target;
		
		try {
			targetWriter = new BufferedWriter(new OutputStreamWriter(target.connectionSocket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void run(){
		while(true){
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
