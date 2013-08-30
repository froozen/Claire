package de.fro_ozen.cl4ire.utils;

import java.awt.Point;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternMatcher {
	
	public static boolean containsPattern(String regularExpression, String sampleString){
		Pattern pattern = Pattern.compile(regularExpression);
		Matcher matcher = pattern.matcher(sampleString);
		
		return matcher.find();
	}
	
	public static Point indexOfPatternMatch(){
		return null;
	}
}
