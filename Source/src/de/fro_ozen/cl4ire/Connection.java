package de.fro_ozen.cl4ire;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import de.fro_ozen.cl4ire.inputlisteners.ClaireInputListener;

public class Connection extends Thread{
	private String nickName, serverName;
	private String connectionInput;
	private String[] channels;
	private Socket connectionSocket;
	private BufferedWriter connectionWriter;
	private BufferedReader connectionReader;
	private InputListenerType type;
	private IRCInputListener connectionInputListener;

	public Connection(String serverName, String[] channels, String nickName, InputListenerType type){
		this.serverName = serverName;
		this.channels = channels;
		this.nickName = nickName;
		this.type = type;
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
			connectionSocket = new Socket(serverName, 6667);

			connectionReader = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			connectionWriter = new BufferedWriter(new OutputStreamWriter(connectionSocket.getOutputStream()));

			//Send out data needed for connection
			connectionWriter.write("NICK " + nickName +"\r\n");
			connectionWriter.write("USER fro_ozen-bot localhost localhost : A IRC chatbot by fro_ozen\r\n");
			connectionWriter.flush();

			//Initial loop in order to finish connecting
			while((connectionInput = connectionReader.readLine()) != null){
				System.out.println(">> " + connectionInput);

				//Return the pong to stay connected
				if(connectionInput.startsWith("PING")){
					connectionWriter.write(connectionInput.replace('O', 'I') + "\r\n");
					connectionWriter.flush();
				}
				else if(connectionInputListener == null){
					//Error: nick already in use
					if(connectionInput.contains(" 433 ")){
						System.out.println("Error: nick is already in use");

						connectionReader.close();
						connectionWriter.close();
						connectionSocket.close();

						break;
					}

					//Connection established
					else if(connectionInput.contains(" 004 ")){
						System.out.println("Connection established");
					}

					//Ready to join channels
					else if(connectionInput.equals(":" + nickName + " MODE " + nickName +" :+i")){
						joinChannels();
						connectionWriter.flush();
						createInputListener();
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

	public enum InputListenerType{
		CLAIRE
	}

}
