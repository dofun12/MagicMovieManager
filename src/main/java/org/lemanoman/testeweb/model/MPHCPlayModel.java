package org.lemanoman.testeweb.model;

public class MPHCPlayModel {
	private boolean fullscreen;
	private String position;
	private SerieFileModel serie;
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
	public SerieFileModel getSerie() {
		return serie;
	}
	public void setSerie(SerieFileModel serie) {
		this.serie = serie;
	}
	
	
	
}
