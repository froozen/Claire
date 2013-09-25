package de.fro_ozen.cl4ire.commands;

public class Message {
	public String content, receiver, author;
	
	public Message(String content, String receiver, String author){
		this.content = content;
		this.author = author;
		this.receiver = receiver;
	}
	
	public String toString(){
		return content + " - from " + author;
	}
}
