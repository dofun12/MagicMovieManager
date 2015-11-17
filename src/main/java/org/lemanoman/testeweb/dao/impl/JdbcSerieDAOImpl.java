package org.lemanoman.testeweb.dao.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.spi.DirStateFactory.Result;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.lemanoman.testeweb.dao.JdbcSerieDAO;
import org.lemanoman.testeweb.model.SerieFileMapper;
import org.lemanoman.testeweb.model.SerieFileModel;
import org.lemanoman.testeweb.model.SerieFilePK;
import org.lemanoman.testeweb.model.SerieModel;
import org.lemanoman.testeweb.model.SerieMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcSerieDAOImpl extends JdbcBaseDAOImpl<SerieModel>implements JdbcSerieDAO {

    @PersistenceContext
    protected EntityManager em;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonConverter;

    public List<SerieModel> listarSeriesOffline() {
	SerieModel serieModel = new SerieModel();
	serieModel.setName("Teste");
	serieModel.setRegex(".*([0-9]{2}).mpeg");
	serieModel.setFilepath("/home/kevim/series/teste-serie");

	List<SerieModel> series = em.createQuery("from SerieModel s", SerieModel.class).getResultList();

	if (series == null) {
	    em.persist(serieModel);
	}else if( series.size()== 0 ){
	    em.persist(serieModel);
	}

	return em.createQuery("from SerieModel s", SerieModel.class).getResultList();
    }
    
    public void adicionarSerie(String nome,String regex,String filepath){
	SerieModel serie = new SerieModel();
	serie.setName(nome);
	serie.setFilepath(filepath);
	serie.setRegex(regex);
	em.persist(serie);
    }

    public void updateCatalogo() {
	List<SerieModel> sources = listarSeriesOffline(); 
	for (SerieModel source : sources) {
	    Integer id = source.getId();
	    File file = new File(source.getFilepath());
	    for (File media : file.listFiles()) {
		String name = media.getName();

		String epName = getVarEpisodio(name, source.getRegex());

		SerieFileModel mediaFile = getSerieFileModel(id, epName);
		
		if(mediaFile==null){
		    mediaFile = new SerieFileModel();
		}
		System.out.println(id);
		
		SerieFilePK pk = new SerieFilePK();
		pk.setEpisodio(epName);
		pk.setIdSerie(id);
		
		mediaFile.setPk(pk);
		mediaFile.setFile(media);
		mediaFile.setFilePath(mediaFile.getFile().getAbsolutePath());
		em.persist(mediaFile);
	    }
	}
	em.flush();
    }

    private String getVarEpisodio(String name, String regex) {
	Pattern pattern = Pattern.compile(regex);
	Matcher matcher = pattern.matcher(name);
	if (matcher.matches()) {
	    String episodio = matcher.group(1);
	    return episodio;
	}
	return null;
    }

    public SerieFileModel getSerieFileModel(Integer idSerie, String episodio) {
	SerieFilePK pk = new SerieFilePK();
	pk.setEpisodio(episodio);
	pk.setIdSerie(idSerie);
	return em.find(SerieFileModel.class, pk);
    }

    public List<SerieModel> listarSeries() {
	List<SerieModel> tmp =  new ArrayList<SerieModel>();
	List<SerieModel> series =  em.createQuery("from SerieModel s", SerieModel.class).getResultList();
	if(series!=null){
	    for(SerieModel s:series){
		s.setFiles(listarSeriesFiles(s.getId()));
		tmp.add(s);
	    }
	}
	return tmp;
    }

    
    
    public List<SerieFileModel> listarSeriesFiles(Integer idSerie) {
	@SuppressWarnings("unchecked")
	List<SerieFileModel> sfm = (List<SerieFileModel>) em.createQuery(
		   "SELECT c FROM SerieFileModel c WHERE c.pk.idSerie = :serieId ")
		   .setParameter("serieId", idSerie)
		   .getResultList();
	 return sfm;
    }

}