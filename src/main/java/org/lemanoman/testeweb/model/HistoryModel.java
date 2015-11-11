package org.lemanoman.testeweb.model;

import java.util.Date;

public class HistoryModel {
	private MediaFileModel media;
	private MPHCResponseModel status;
	private Date lastTimeWatched;
	
	public MediaFileModel getMedia() {
		return media;
	}
	public void setMedia(MediaFileModel media) {
		this.media = media;
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
	
	
}
