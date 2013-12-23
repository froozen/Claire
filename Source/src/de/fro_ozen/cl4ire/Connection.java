package de.fro_ozen.cl4ire;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URLDecoder;

import de.fro_ozen.cl4ire.inputlisteners.ClaireInputListener;

public class Connection extends Thread{
	public  String nickName, serverName;
	public InputListenerType type;
	public String[] channels;
	public Socket connectionSocket;

	private String connectionInput;
	private BufferedWriter connectionWriter;
	private BufferedReader connectionReader;
	private ConnectionTester connectionTester;
	private IRCInputListener connectionInputListener;

	public Connection(String serverName, String[] channels, String nickName, InputListenerType type){
		this.serverName = serverName;
		this.channels = channels;
		this.nickName = nickName;
		this.type = type;
	}

	public Connection(){

	}

	private void joinChannels() throws IOException{
		for(int i = 0; i<channels.length; i++){
			connectionWriter.write("JOIN " + channels[i] + "\r\n");
			System.out.println("Joining " + channels[i]);
		}
		connectionWriter.flush();
	}

	private void createInputListener(){
		switch(type){
		case CLAIRE:
			connectionInputListener = new ClaireInputListener();
			break;
		default:
			System.out.println("Invalid InputListenerType in " + nickName);
		}
	}

	public void run(){
		try {
			//Try to connect
			boolean connectionEstablished = false;
			while(!connectionEstablished){
				try{
					connectionSocket = new Socket(serverName, 6667);
					connectionEstablished = true;


				}
				catch (IOException e) {
					System.out.println("Failed to connect to: " + serverName);
					try {Thread.sleep(30000);
					}catch (InterruptedException e1) {e1.printStackTrace();}
				}
			}
			connectionReader = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			connectionWriter = new BufferedWriter(new OutputStreamWriter(connectionSocket.getOutputStream()));

			//Send out data needed for connection
			connectionWriter.write("NICK " + nickName +"\r\n");
			connectionWriter.write("USER fro_ozen-bot localhost localhost : A IRC chatbot by fro_ozen\r\n");
			connectionWriter.flush();

			//Initial loop in order to finish connecting
			while((connectionInput = connectionReader.readLine()) != null){

				//Return the pong to stay connected
				if(connectionInput.startsWith("PING")){
					connectionWriter.write(connectionInput.replace('O', 'I') + "\r\n");
					connectionWriter.flush();
				}
				else if(connectionInputListener == null){
					//Error: nick already in use
					if(connectionInput.contains(" 433 ")){
						if(!nickName.endsWith("_")){
							System.out.println("Error: nick is already in use");
							nickName += "_";
							System.out.println("Switching to: " + nickName);
						}
						else{
							System.out.println("Error: nick is already in use");
							nickName = nickName.substring(0, nickName.length() - 1);
							System.out.println("Switching to: " + nickName);
						}

						connectionWriter.write("NICK " + nickName +"\r\n");
						connectionWriter.flush();
					}

					//Connection established
					else if(connectionInput.contains(" 004 ")){
						System.out.println("Connection established to: " + serverName);
					}

					//Ready to join channels
					else if(connectionInput.equals(":" + nickName + " MODE " + nickName +" :+i")){
						joinChannels();
						connectionWriter.flush();
						createInputListener();
						if(connectionInputListener != null)connectionInputListener.setIRCWriter(connectionWriter);

						connectionTester = new ConnectionTester(this);
						connectionTester.start();
					}
				}

				//Handle the input via connectionInputListener
				else{
					connectionInputListener.handleInput(connectionInput);
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getFilePath(){
		try {
			return URLDecoder.decode(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	public enum InputListenerType{
		CLAIRE
	}

}
