package org.lemanoman.testeweb.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SerieSource {
    	private Integer id;
	private String title;
	private List<MediaFileModel> files = new ArrayList<MediaFileModel>();
	
	public SerieSource(String title,String path,String regex){
		this.title = title;
		File file = new File(path);
		for(File media:file.listFiles()){
			String name = media.getName();
			
			String epName = getVarEpisodio(name, regex);
			
			MediaFileModel mediaFile = new MediaFileModel();
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

	public List<MediaFileModel> getFiles() {
		return files;
	}

	public void setFiles(List<MediaFileModel> files) {
		this.files = files;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getId() {
	    return id;
	}

	public void setId(Integer id) {
	    this.id = id;
	}
	
	
}
