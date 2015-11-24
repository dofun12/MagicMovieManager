package org.lemanoman.testeweb.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileModel {
    private Integer id;
    private String name;
    private String path;
    private Long size;
    private String episodio;
    private boolean isDirectory;
    
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public Long getSize() {
        return size;
    }
    public void setSize(Long size) {
        this.size = size;
    }
    public String getEpisodio() {
        return episodio;
    }
    public void setEpisodio(String episodio) {
        this.episodio = episodio;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public boolean isDirectory() {
        return isDirectory;
    }
    public void setDirectory(boolean isDirectory) {
        this.isDirectory = isDirectory;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    
    

}