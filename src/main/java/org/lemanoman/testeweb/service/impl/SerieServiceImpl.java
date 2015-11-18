package org.lemanoman.testeweb.service.impl;

import java.util.List;

import org.lemanoman.testeweb.dao.JdbcSerieDAO;
import org.lemanoman.testeweb.model.HistoricoModel;
import org.lemanoman.testeweb.model.HistoricoPK;
import org.lemanoman.testeweb.model.NovaSerieModel;
import org.lemanoman.testeweb.model.SerieFileModel;
import org.lemanoman.testeweb.model.SerieModel;
import org.lemanoman.testeweb.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SerieServiceImpl implements SerieService {
	@Autowired
	private JdbcSerieDAO dao;

	public void adicionarSerie(NovaSerieModel novaSerie) {
	    dao.adicionarSerie(novaSerie);
	}

	@Override
	public List<SerieModel> listarSeriesOffline() {
		return dao.listarSeriesOffline();
	}

	public void updateCatalogo() {
		dao.updateCatalogo();
	}

	public List<SerieModel> listarSeries() {
		return dao.listarSeries();
	}

	public List<SerieFileModel> listarSeriesByPath(String path) {
		return dao.listarSeriesFilesByPath(path);
	}

	public void salvarHistorico(HistoricoModel model) {
		if (model != null && model.getPk() != null) {
			dao.salvarHistorico(model);
		}
	}

	@Override
	public HistoricoModel getHistoricoModel(HistoricoPK pk) {
		return dao.find(HistoricoModel.class, pk);
	}
	
	@Override
	public HistoricoModel getUltimaSerieAssistida(Integer idSerie) {
		return dao.ultimoEpisodioAssistido(idSerie);
	}

}
