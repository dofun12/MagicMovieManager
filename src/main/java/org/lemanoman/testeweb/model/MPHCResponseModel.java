package org.lemanoman.testeweb.model;

import javax.persistence.Entity;
import javax.persistence.Table;

public class MPHCResponseModel {
/**
 * //{
 * "file":"00102.flv",
 * "filepatharg":"F:%5cDataFiles%5ctemp%5c00102.flv",
 * "filepath":"F:\\DataFiles\\temp\\00102.flv",
 * "filedirarg":"F:%5cDataFiles%5ctemp",
 * "filedir":"F:\\DataFiles\\temp",
 * "state":"2",
 * "statestring":"Reproduzindo",
 * "position":"342863",
 * "positionstring":"00:05:43",
 * "duration":"511107",
 * "durationstring":"00:08:31",
 * "volumelevel":"8",
 * "muted":"0",
 * "playbackrate":"1",
 * "size":"25,5 MB",
 * "reloadtime":"0",
 * "version":"1.7.9.0"
 * }
 */
	private String file;
	private String filepath;
	private String filepatharg;
	private String filedir;
	private String filedirarg;
	private Integer state;
	private String statestring;
	private Long position;
	private String positionstring;
	private Long duration;
	private String durationstring;
	private Integer volumelevel;
	private String muted;
	private Integer playbackrate;
	private String size;
	private Integer reloadtime;
	private String version;
	private MPHCStatusType statusType;
	
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	
	public String getFilepatharg() {
		return filepatharg;
	}
	public void setFilepatharg(String filepatharg) {
		this.filepatharg = filepatharg;
	}
	public String getFiledir() {
		return filedir;
	}
	public void setFiledir(String filedir) {
		this.filedir = filedir;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getStatestring() {
		return statestring;
	}
	public void setStatestring(String statestring) {
		this.statestring = statestring;
	}
	public Long getPosition() {
		return position;
	}
	public void setPosition(Long position) {
		this.position = position;
	}
	public String getPositionstring() {
		return positionstring;
	}
	public void setPositionstring(String positionstring) {
		this.positionstring = positionstring;
	}
	public Long getDuration() {
		return duration;
	}
	public void setDuration(Long duration) {
		this.duration = duration;
	}
	public Integer getVolumelevel() {
		return volumelevel;
	}
	public void setVolumelevel(Integer volumelevel) {
		this.volumelevel = volumelevel;
	}
	public String getMuted() {
		return muted;
	}
	public void setMuted(String muted) {
		this.muted = muted;
	}
	public Integer getPlaybackrate() {
		return playbackrate;
	}
	public void setPlaybackrate(Integer playbackrate) {
		this.playbackrate = playbackrate;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public Integer getReloadtime() {
		return reloadtime;
	}
	public void setReloadtime(Integer reloadtime) {
		this.reloadtime = reloadtime;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getFiledirarg() {
		return filedirarg;
	}
	public void setFiledirarg(String filedirarg) {
		this.filedirarg = filedirarg;
	}
	public String getDurationstring() {
		return durationstring;
	}
	public void setDurationstring(String durationstring) {
		this.durationstring = durationstring;
	}
	public MPHCStatusType getStatusType() {
		return statusType;
	}
	public void setStatusType(MPHCStatusType statusType) {
		this.statusType = statusType;
	}
	
	
	
}



