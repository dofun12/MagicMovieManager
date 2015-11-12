package org.lemanoman.testeweb.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SerieModel {
    	private Integer id;
	private String name;
	private List<SerieFileModel> files = new ArrayList<SerieFileModel>();
	
	public SerieModel() {
	    
	}
	
	public SerieModel(String title,String path,String regex){
		this.name = title;
		File file = new File(path);
		for(File media:file.listFiles()){
			String name = media.getName();
			
			String epName = getVarEpisodio(name, regex);
			
			SerieFileModel mediaFile = new SerieFileModel();
			mediaFile.setEpisodio(epName);
			mediaFile.setFile(media);
			
			files.add(mediaFile);
		}
	}
	
	private String getVarEpisodio(String name,String regex){
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(name);
		if (matcher.matches()) {
		    String episodio = matcher.group(1);
		    return episodio;
		}
		return null;
	}

	public List<SerieFileModel> getFiles() {
		return files;
	}

	public void setFiles(List<SerieFileModel> files) {
		this.files = files;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
	    return id;
	}

	public void setId(Integer id) {
	    this.id = id;
	}
	
	
}