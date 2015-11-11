package org.lemanoman.testeweb.dao.impl;

import java.util.List;

import org.lemanoman.testeweb.dao.JdbcSerieDAO;
import org.lemanoman.testeweb.model.SerieSource;
import org.lemanoman.testeweb.model.SerieSourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcSerieDAOImpl extends JdbcBaseDAOImpl<SerieSource> implements JdbcSerieDAO{
	
	@Autowired
	private MappingJackson2HttpMessageConverter jacksonConverter;
	
	public List<SerieSource> listAll() {
		try {
			StringBuilder query = new StringBuilder();
			
			query.append( "SELECT * from serie;" );
			return this.getNamedParameterJdbcTemplate().query( query.toString(), new SerieSourceMapper() );
			
		} catch( Exception e ) {
			//throw new ApplicationMessageException( e );
		}
		return null;
	}
	
}