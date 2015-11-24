package org.lemanoman.testeweb.model.rest;

import org.lemanoman.testeweb.model.MediaFileModel;

public class MPHCPlayModel {
	private boolean fullscreen;
	private String position;
	private Long positionLong;
	private String password;
	private MediaFileModel mediaFile;
	
	public boolean isFullscreen() {
	    return fullscreen;
	}
	public void setFullscreen(boolean fullscreen) {
	    this.fullscreen = fullscreen;
	}
	public String getPosition() {
	    return position;
	}
	public void setPosition(String position) {
	    this.position = position;
	}
	public Long getPositionLong() {
	    return positionLong;
	}
	public void setPositionLong(Long positionLong) {
	    this.positionLong = positionLong;
	}
	public String getPassword() {
	    return password;
	}
	public void setPassword(String password) {
	    this.password = password;
	}
	public MediaFileModel getMediaFile() {
	    return mediaFile;
	}
	public void setMediaFile(MediaFileModel mediaFile) {
	    this.mediaFile = mediaFile;
	}
	
	
}
