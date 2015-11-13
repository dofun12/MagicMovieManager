package org.lemanoman.testeweb.model;

import java.io.File;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="serie_file")
public class SerieFileModel {
    	@EmbeddedId
    	private SerieFilePK pk;
    	
    	@Transient
	private File file;
    	
    	@Column
	private String filePath;

	public SerieFilePK getPk() {
	    return pk;
	}

	public void setPk(SerieFilePK pk) {
	    this.pk = pk;
	}

	public File getFile() {
	    return file;
	}

	public void setFile(File file) {
	    this.file = file;
	}

	public String getFilePath() {
	    return filePath;
	}

	public void setFilePath(String filePath) {
	    this.filePath = filePath;
	}
	
	
	
	
}
