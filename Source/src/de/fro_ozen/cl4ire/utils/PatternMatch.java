package de.fro_ozen.cl4ire.utils;

public class PatternMatch {
	public int start, end;
	public String group;
	
	public PatternMatch(int start, int end, String group){
		this.start = start;
		this.end = end;
		this.group = group;
	}
}
