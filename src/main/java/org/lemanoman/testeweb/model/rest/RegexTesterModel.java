package org.lemanoman.testeweb.model.rest;

import java.util.List;

import org.lemanoman.testeweb.model.EpisodioModel;

public class RegexTesterModel {
    private String regex;
    private List<EpisodioModel> episodios;

    public String getRegex() {
	return regex;
    }

    public void setRegex(String regex) {
	this.regex = regex;
    }

    public List<EpisodioModel> getFiles() {
	return episodios;
    }

    public void setFiles(List<EpisodioModel> episodios) {
	this.episodios = episodios;
    }

}
