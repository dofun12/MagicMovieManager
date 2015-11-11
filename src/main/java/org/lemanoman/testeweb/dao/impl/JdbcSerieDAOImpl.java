package org.lemanoman.testeweb.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.lemanoman.testeweb.dao.JdbcSerieDAO;
import org.lemanoman.testeweb.model.SerieFileMapper;
import org.lemanoman.testeweb.model.SerieFileModel;
import org.lemanoman.testeweb.model.SerieModel;
import org.lemanoman.testeweb.model.SerieMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcSerieDAOImpl extends JdbcBaseDAOImpl<SerieModel>implements JdbcSerieDAO {

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonConverter;

    public List<SerieModel> listarSeriesOffline() {
	StringBuilder query = new StringBuilder();
	query.append("SELECT * from serie");
	return this.getNamedParameterJdbcTemplate().query(query.toString(), new SerieMapper());
    }

    public void updateCatalogo() {
	for (SerieModel source : listarSeriesOffline()) {
	    for (SerieFileModel sf : source.getFiles()) {
		SerieFileModel model = getSerieFileModel(source.getId(), sf.getEpisodio());

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("episodio", sf.getEpisodio());
		params.addValue("file", sf.getFile().getAbsolutePath());
		params.addValue("id_serie", source.getId());
		if (model == null) {
		    StringBuilder query = new StringBuilder();
		    query.append("INSERT INTO serie_file (  ");
		    query.append("  episodio, ");
		    query.append("  file, ");
		    query.append("  id_serie ");
		    query.append(" ) VALUES ( ");
		    query.append("  :episodio, ");
		    query.append("  :file, ");
		    query.append("  :id_serie ");
		    query.append(")");

		    this.getNamedParameterJdbcTemplate().update(query.toString(), params);
		} else if(!model.getFile().getAbsolutePath().equals(sf.getFile().getAbsolutePath())){
		    StringBuilder query = new StringBuilder();
		    query.append("UPDATE ");
		    query.append(" serie_file ");
		    query.append(" SET ");
		    query.append(" file=:file ");
		    query.append(" WHERE episodio = :episodio ");
		    query.append(" AND id_serie = :id_serie ");
		    query.append(")");

		    this.getNamedParameterJdbcTemplate().update(query.toString(), params);
		}
	    }
	}
    }

    public SerieFileModel getSerieFileModel(Integer idSerie, String episodio) {
	StringBuilder query = new StringBuilder();
	query.append("SELECT * from serie_file where id_serie = :idserie and episodio= :episodio");

	MapSqlParameterSource params = new MapSqlParameterSource();
	params.addValue("idserie", idSerie);
	params.addValue("episodio", episodio);

	List<SerieFileModel> files = this.getNamedParameterJdbcTemplate().query(query.toString(), params,
		new SerieFileMapper());
	if (files != null && files.size() > 0) {
	    return files.get(0);
	} else {
	    return null;
	}
    }

    public List<SerieModel> listarSeries() {
	List<SerieModel> list = new ArrayList<SerieModel>();

	StringBuilder query = new StringBuilder();
	query.append("SELECT * from serie");

	List<SerieModel> seriesTmp = this.getNamedParameterJdbcTemplate().query(query.toString(), new SerieMapper());
	if (seriesTmp != null) {
	    for (SerieModel s : seriesTmp) {
		List<SerieFileModel> serieFileModels = listarSeriesFiles(s.getId());
		s.setFiles(serieFileModels);
		list.add(s);
	    }
	}
	return list;
    }

    public List<SerieFileModel> listarSeriesFiles(Integer idSerie) {
	StringBuilder query = new StringBuilder();
	query.append("SELECT * from serie_file where id_serie = :idserie order by episodio");

	MapSqlParameterSource params = new MapSqlParameterSource();
	params.addValue("idserie", idSerie);
	return this.getNamedParameterJdbcTemplate().query(query.toString(), params, new SerieFileMapper());
    }

}