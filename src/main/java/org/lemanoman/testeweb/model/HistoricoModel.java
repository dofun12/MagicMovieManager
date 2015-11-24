package org.lemanoman.testeweb.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "historico")
public class HistoricoModel {
	@EmbeddedId
	private HistoricoPK pk;

	@Transient
	private MPHCResponseModel status;

	@Column
	private Date lastTimeWatched;

	@Column
	private Double percentWatched;
	
	@Column
	private String positionstring;
	
	public String getPositionstring() {
		return positionstring;
	}

	public void setPositionstring(String positionstring) {
		this.positionstring = positionstring;
	}

	@Column
	private Long lastPosition;

	public HistoricoPK getPk() {
		return pk;
	}

	public void setPk(HistoricoPK pk) {
		this.pk = pk;
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

	public Long getLastPosition() {
		return lastPosition;
	}

	public void setLastPosition(Long lastPosition) {
		this.lastPosition = lastPosition;
	}
	
}
