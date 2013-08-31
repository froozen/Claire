package de.fro_ozen.cl4ire.utils;

import java.awt.Point;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternMatcher {
	
	public static boolean containsPattern(String regularExpression, String sampleString){
		Pattern pattern = Pattern.compile(regularExpression);
		Matcher matcher = pattern.matcher(sampleString);
		
		return matcher.find();
	}
	
	public static PatternMatch indexOfPatternMatch(String regularExpression, String sampleString){
		Pattern pattern = Pattern.compile(regularExpression);
		Matcher matcher = pattern.matcher(sampleString);
		
		if(matcher.find()){
			return new PatternMatch(matcher.start(), matcher.end(), matcher.group());
		}
		else return null;
	}
	
	public static ArrayList<PatternMatch> indexesOfPatternMatch(String regularExpression, String sampleString){
		Pattern pattern = Pattern.compile(regularExpression);
		Matcher matcher = pattern.matcher(sampleString);
		ArrayList<PatternMatch> output = new ArrayList<PatternMatch>();
		boolean found = false;
		
		while(matcher.find()){
			found = true;
			output.add(new PatternMatch(matcher.start(), matcher.end(), matcher.group()));
		}
		
		if(found) return output;
		else return null;
	}
}
