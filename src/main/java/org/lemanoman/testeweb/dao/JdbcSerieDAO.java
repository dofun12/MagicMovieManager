package org.lemanoman.testeweb.dao;

import java.util.List;

import org.lemanoman.testeweb.model.HistoricoModel;
import org.lemanoman.testeweb.model.SerieModel;
import org.lemanoman.testeweb.model.rest.NovaSerieModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.node.ObjectNode;


public interface JdbcSerieDAO{
	public List<SerieModel> listarSeries();
	public void adicionarSerie(NovaSerieModel novaSerie);
}