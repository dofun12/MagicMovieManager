package org.lemanoman.testeweb.service;

import java.util.List;

import org.lemanoman.testeweb.dao.JdbcSerieDAO;
import org.lemanoman.testeweb.model.SerieModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

public interface SerieService {
    public List<SerieModel> listarSeries();
    public void updateCatalogo();
    public List<SerieModel> listarSeriesOffline();
    
    
}
