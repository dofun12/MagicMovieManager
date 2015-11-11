package org.lemanoman.testeweb.model;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class SerieFileMapper implements ResultSetExtractor<List<SerieFileModel>> {

    public List<SerieFileModel> extractData(ResultSet rs) throws SQLException, DataAccessException {

	List<SerieFileModel> series = new LinkedList<SerieFileModel>();

	if (rs != null) {
	    while (rs.next()) {
		String episodio = rs.getString("episodio");
		String path = rs.getString("file");
		Integer idSerie = rs.getInt("id_serie");
		
		SerieFileModel serieFileModel = new SerieFileModel();
		serieFileModel.setEpisodio(episodio);
		serieFileModel.setFile(new File(path));
		serieFileModel.setIdSerie(idSerie);
		
		series.add(serieFileModel);
	    }
	}

	return series;
    }
}
