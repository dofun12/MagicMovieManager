package org.lemanoman.testeweb.model;

import java.util.Date;

public class HistoricoModel {
	private Integer idSerieFile;
	private Date lastWatched;
	private Long lastPosition;
	public Integer getIdSerieFile() {
		return idSerieFile;
	}
	public void setIdSerieFile(Integer idSerieFile) {
		this.idSerieFile = idSerieFile;
	}
	public Date getLastWatched() {
		return lastWatched;
	}
	public void setLastWatched(Date lastWatched) {
		this.lastWatched = lastWatched;
	}
	public Long getLastPosition() {
		return lastPosition;
	}
	public void setLastPosition(Long lastPosition) {
		this.lastPosition = lastPosition;
	}
	
	
}
