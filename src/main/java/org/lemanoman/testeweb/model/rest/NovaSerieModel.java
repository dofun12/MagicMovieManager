package org.lemanoman.testeweb.model.rest;

import java.util.ArrayList;
import java.util.List;

import org.lemanoman.testeweb.model.EpisodioModel;
import org.lemanoman.testeweb.model.SerieModel;

public class NovaSerieModel {
    private SerieModel serie;
    private Integer temporada;
    private List<EpisodioModel> episodios = new ArrayList<EpisodioModel>();
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
    public List<EpisodioModel> getEpisodios() {
        return episodios;
    }
    public void setEpisodios(List<EpisodioModel> episodios) {
        this.episodios = episodios;
    }
    
    
}
