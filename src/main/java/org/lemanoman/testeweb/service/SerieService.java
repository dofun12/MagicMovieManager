package org.lemanoman.testeweb.service;

import java.util.List;

import org.lemanoman.testeweb.dao.JdbcSerieDAO;
import org.lemanoman.testeweb.model.HistoricoModel;
import org.lemanoman.testeweb.model.HistoricoPK;
import org.lemanoman.testeweb.model.NovaSerieModel;
import org.lemanoman.testeweb.model.SerieFileModel;
import org.lemanoman.testeweb.model.SerieModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

public interface SerieService {
    public List<SerieModel> listarSeries();
    public void updateCatalogo();
    public List<SerieModel> listarSeriesOffline();
    public void adicionarSerie(NovaSerieModel serie);
    public void salvarHistorico(HistoricoModel model);
    public List<SerieFileModel> listarSeriesByPath(String path);
    public HistoricoModel getHistoricoModel(HistoricoPK pk);
    public HistoricoModel getUltimaSerieAssistida(Integer idSerie);
    public void deleteAllSerieFileModel(Integer idSerie);
    public List<SerieFileModel> listarSerieSecreta(SerieModel serieModel);
    public void setAllSecretSeriesVisible();
    public void setAllSecretSeriesInvisible();
}
