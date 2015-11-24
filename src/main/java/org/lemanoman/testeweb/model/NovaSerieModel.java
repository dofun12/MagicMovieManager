package org.lemanoman.testeweb.model;

import java.util.ArrayList;
import java.util.List;

public class NovaSerieModel {
    private SerieModel serie;
    private Integer temporada;
    private List<FileModel> episodios = new ArrayList<FileModel>();
    public SerieModel getSerie() {
        return serie;
    }
    public void setSerie(SerieModel serie) {
        this.serie = serie;
    }
    public Integer getTemporada() {
        return temporada;
    }
    public void setTemporada(Integer temporada) {
        this.temporada = temporada;
    }
    public List<FileModel> getEpisodios() {
        return episodios;
    }
    public void setEpisodios(List<FileModel> episodios) {
        this.episodios = episodios;
    }
    
    
}
