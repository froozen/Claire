package de.fro_ozen.cl4ire.commands;

public class Message {
	public String content, receiver, author;
	public boolean unread;

	public Message(String content, String receiver, String author){
		this.content = content;
		this.author = author;
		this.receiver = receiver;
		unread = true;
	}

	public Message(String fileContent){
		String[] messageSplit = fileContent.split(" ");
		if(messageSplit.length < 4 && !fileContent.equals(""))System.out.println("Error creating message for: -" + fileContent + "-");
		else{
			this.author = messageSplit[0];
			this.receiver = messageSplit[1];
			
			int startIndex = 3; //Index at that the message starts
			
			try {
				unread = Boolean.parseBoolean(messageSplit[2]);
			} catch (Exception e) {
				unread = true;
				startIndex = 2;
			}
			
			String messageContent = "";
			for(int i = startIndex; i<messageSplit.length; i++){
				messageContent += messageSplit[i];
				if(i != messageSplit.length - 1)messageContent += " ";
			}
			this.content = messageContent;
		}
	}

	public String toString(){
		return content + " - from " + author;
	}

	public String toSaveFormat(){
		return author + " " + receiver + " " + unread +" " + content;
	}
}
