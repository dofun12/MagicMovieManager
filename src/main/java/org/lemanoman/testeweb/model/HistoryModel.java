package org.lemanoman.testeweb.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="historico")
public class HistoryModel {
    	@Id
    	@GeneratedValue
    	private Integer codigo;

    	@OneToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.EAGER, orphanRemoval = true)
	private SerieFileModel serieFile;
    	
    	@Transient
	private MPHCResponseModel status;
    	
    	@Column
	private Date lastTimeWatched;
    	
    	@Column
	private Double percentWatched;
	
	public SerieFileModel getMedia() {
		return serieFile;
	}
	public void setMedia(SerieFileModel media) {
		this.serieFile = media;
	}
	public MPHCResponseModel getStatus() {
		return status;
	}
	public void setStatus(MPHCResponseModel status) {
		this.status = status;
	}
	public Date getLastTimeWatched() {
		return lastTimeWatched;
	}
	public void setLastTimeWatched(Date lastTimeWatched) {
		this.lastTimeWatched = lastTimeWatched;
	}
	public Integer getCodigo() {
	    return codigo;
	}
	public void setCodigo(Integer codigo) {
	    this.codigo = codigo;
	}
	public SerieFileModel getSerieFile() {
	    return serieFile;
	}
	public void setSerieFile(SerieFileModel serieFile) {
	    this.serieFile = serieFile;
	}
	public Double getPercentWatched() {
	    return percentWatched;
	}
	public void setPercentWatched(Double percentWatched) {
	    this.percentWatched = percentWatched;
	}
	
	
}
