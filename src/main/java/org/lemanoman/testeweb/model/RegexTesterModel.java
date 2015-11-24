package org.lemanoman.testeweb.model;

import java.util.List;

public class RegexTesterModel {
    private String regex;
    private List<FileModel> files;

    public String getRegex() {
	return regex;
    }

    public void setRegex(String regex) {
	this.regex = regex;
    }

    public List<FileModel> getFiles() {
	return files;
    }

    public void setFiles(List<FileModel> files) {
	this.files = files;
    }

}
