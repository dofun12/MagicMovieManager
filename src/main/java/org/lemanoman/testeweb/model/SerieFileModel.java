package org.lemanoman.testeweb.model;

import java.io.File;

public class SerieFileModel {
	private String episodio;
	private File file;
	private Integer idSerie;
	
	public String getEpisodio() {
		return episodio;
	}
	public void setEpisodio(String episodio) {
		this.episodio = episodio;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public Integer getIdSerie() {
	    return idSerie;
	}
	public void setIdSerie(Integer idSerie) {
	    this.idSerie = idSerie;
	}
	
	
}
