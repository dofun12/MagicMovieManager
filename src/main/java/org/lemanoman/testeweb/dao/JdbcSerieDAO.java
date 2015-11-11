package org.lemanoman.testeweb.dao;

import java.util.List;

import org.lemanoman.testeweb.model.SerieSource;
import org.lemanoman.testeweb.model.SerieSourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.node.ObjectNode;


public interface JdbcSerieDAO{
	public List<SerieSource> listAll();
	
}