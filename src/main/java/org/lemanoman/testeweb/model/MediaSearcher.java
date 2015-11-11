package org.lemanoman.testeweb.model;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MediaSearcher {

	public static void main(String[] args) {
		try{
			
			
		}catch(Exception e){
			e.printStackTrace();
		}

	}

	public static String getVarEpisodio(String name,String regex){
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(name);
		if (matcher.matches()) {
		    String episodio = matcher.group(1);
		    return episodio;
		}
		return "";
	}
}
