package org.lemanoman.testeweb.service.impl;

import java.util.List;

import org.lemanoman.testeweb.dao.JdbcSerieDAO;
import org.lemanoman.testeweb.model.SerieSource;
import org.lemanoman.testeweb.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SerieServiceImpl implements SerieService{
    @Autowired
    private JdbcSerieDAO dao;

    @Override
    public List<SerieSource> listarSeries() {
	return dao.listAll();
    }
    
    
}
