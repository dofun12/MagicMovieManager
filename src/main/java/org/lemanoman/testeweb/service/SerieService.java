package org.lemanoman.testeweb.service;

import java.util.List;

import org.lemanoman.testeweb.model.SerieModel;
import org.lemanoman.testeweb.model.rest.NovaSerieModel;

public interface SerieService {
    public void adicionarSerie(NovaSerieModel serie);
    public List<SerieModel> listarSeries();
    
}
