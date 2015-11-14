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
public class HistoricoModel {
    	@Id
    	@GeneratedValue
    	private Integer codigo;

    	@Column
    	private Integer idSerieFile;
    	
    	@Transient
	private MPHCResponseModel status;
    	
    	@Column
	private Date lastTimeWatched;
    	
    	@Column
	private Double percentWatched;

	public Integer getCodigo() {
	    return codigo;
	}

	public void setCodigo(Integer codigo) {
	    this.codigo = codigo;
	}

	public Integer getIdSerieFile() {
	    return idSerieFile;
	}

	public void setIdSerieFile(Integer idSerieFile) {
	    this.idSerieFile = idSerieFile;
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

	public Double getPercentWatched() {
	    return percentWatched;
	}

	public void setPercentWatched(Double percentWatched) {
	    this.percentWatched = percentWatched;
	}
}
