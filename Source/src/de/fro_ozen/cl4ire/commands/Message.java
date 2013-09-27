package de.fro_ozen.cl4ire.commands;

public class Message {
	public String content, receiver, author;

	public Message(String content, String receiver, String author){
		this.content = content;
		this.author = author;
		this.receiver = receiver;
	}

	public Message(String fileContent){
		String[] messageSplit = fileContent.split(" ");
		if(messageSplit.length < 3)System.out.println("Error creating message for: " + fileContent);
		else{
			this.author = messageSplit[0];
			this.receiver = messageSplit[1];
			
			String messageContent = "";
			for(int i = 2; i<messageSplit.length; i++){
				messageContent += messageSplit[i];
				if(i != messageSplit.length - 1)messageContent += " ";
			}
			this.content = messageContent;
			
			System.out.println("Created: " + this.toString());
		}
	}

	public String toString(){
		return content + " - from " + author;
	}

	public String toSaveFormat(){
		return author + " " + receiver + " " + content;
	}
}
