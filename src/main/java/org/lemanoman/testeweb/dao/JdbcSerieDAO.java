package org.lemanoman.testeweb.dao;

import java.util.List;

import org.lemanoman.testeweb.model.HistoricoModel;
import org.lemanoman.testeweb.model.NovaSerieModel;
import org.lemanoman.testeweb.model.SerieFileModel;
import org.lemanoman.testeweb.model.SerieModel;
import org.lemanoman.testeweb.model.SerieMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.node.ObjectNode;


public interface JdbcSerieDAO{
	public List<SerieModel> listarSeriesOffline();
	public void updateCatalogo();
	public SerieFileModel getSerieFileModel(Integer idSerie, String episodio,Integer temporada);
	public List<SerieModel> listarSeries();
	public void adicionarSerie(NovaSerieModel novaSerie);
	public void salvarHistorico(HistoricoModel historicoModel);
	public List<SerieFileModel> listarSeriesFilesByPath(String path);
	public HistoricoModel ultimoEpisodioAssistido(Integer idSerie);
	public <T>T find(Class<T> type,Object key);
}