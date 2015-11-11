package org.lemanoman.testeweb.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class SerieSourceMapper implements ResultSetExtractor<List<SerieSource>> {

    public List<SerieSource> extractData(ResultSet rs) throws SQLException, DataAccessException {

	List<SerieSource> series = new LinkedList<SerieSource>();

	if (rs != null) {
	    while (rs.next()) {
		Integer id = rs.getInt("id");
		String name = rs.getString("nome");
		String path = rs.getString("path");
		String regex = rs.getString("regex");
		
		SerieSource serie = new SerieSource(name, path, regex);
		serie.setId(id);
		series.add(serie);
	    }
	}

	return series;
    }

}
