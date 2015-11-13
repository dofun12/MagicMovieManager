package org.lemanoman.testeweb.model;

import java.io.File;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="serie_file")
public class SerieFileModel {
    	@Column
	private String episodio;
    	
    	@Column
	private File file;
    	
    	@Id
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
