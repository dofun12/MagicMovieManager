package org.lemanoman.testeweb.model;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class SerieMapper implements ResultSetExtractor<List<SerieModel>> {

    public List<SerieModel> extractData(ResultSet rs) throws SQLException, DataAccessException {

	List<SerieModel> series = new LinkedList<SerieModel>();

	if (rs != null) {
	    while (rs.next()) {
		Integer id = rs.getInt("id");
		String name = rs.getString("nome");
		String path = rs.getString("path");
		String regex = rs.getString("regex");

		SerieModel serie = new SerieModel(name, path, regex);
		serie.setId(id);
		series.add(serie);
	    }
	}

	return series;
    }

}
