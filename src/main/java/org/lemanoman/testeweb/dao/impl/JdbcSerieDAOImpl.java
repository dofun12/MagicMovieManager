package org.lemanoman.testeweb.dao.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.spi.DirStateFactory.Result;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.lemanoman.testeweb.dao.JdbcSerieDAO;
import org.lemanoman.testeweb.model.FileModel;
import org.lemanoman.testeweb.model.HistoricoModel;
import org.lemanoman.testeweb.model.NovaSerieModel;
import org.lemanoman.testeweb.model.SerieFileMapper;
import org.lemanoman.testeweb.model.SerieFileModel;
import org.lemanoman.testeweb.model.SerieFilePK;
import org.lemanoman.testeweb.model.SerieModel;
import org.lemanoman.testeweb.model.SerieMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import javax.persistence.Query;

@Repository
public class JdbcSerieDAOImpl extends JdbcBaseDAOImpl<SerieFileModel> implements JdbcSerieDAO {

	@PersistenceContext
	protected EntityManager em;

	@Autowired
	private MappingJackson2HttpMessageConverter jacksonConverter;

	public List<SerieModel> listarSeriesOffline() {
		SerieModel serieModel = new SerieModel();
		serieModel.setName("Teste");

		List<SerieModel> series = em.createQuery("from SerieModel s", SerieModel.class).getResultList();

		if (series == null) {
			em.persist(serieModel);
		} else if (series.size() == 0) {
			em.persist(serieModel);
		}

		return em.createQuery("from SerieModel s", SerieModel.class).getResultList();
	}

	public void adicionarSerie(NovaSerieModel novaSerie) {
		SerieModel serie = null;
		if(novaSerie.getSerie()!=null){
			if(novaSerie.getSerie().getId()!=null){
				serie = find(SerieModel.class, novaSerie.getSerie().getId());
			}else{
				serie = new SerieModel();
				serie.setName(novaSerie.getSerie().getName());
				em.persist(serie);
				em.flush();
			}	
		}
			

		Integer idSerie = serie.getId();
		for (FileModel f : novaSerie.getEpisodios()) {
			if (idSerie != null && f.getEpisodio() != null && f.getEpisodio() != "") {
				SerieFileModel sfm = getSerieFileModel(idSerie, f.getEpisodio(), novaSerie.getTemporada());
				if (sfm == null) {
					sfm = new SerieFileModel();
					SerieFilePK pk = new SerieFilePK();
					pk.setEpisodio(f.getEpisodio());
					pk.setIdSerie(idSerie);
					pk.setTemporada(novaSerie.getTemporada());
					sfm.setPk(pk);
				}

				sfm.setFilePath(f.getPath());
				em.persist(sfm);
			}
		}

	}

	public void updateCatalogo() {
		/**
		 * List<SerieModel> sources = listarSeriesOffline(); for (SerieModel
		 * source : sources) { Integer id = source.getId(); File file = new
		 * File(source.getFilepath()); for (File media : file.listFiles()) {
		 * String name = media.getName();
		 * 
		 * String epName = getVarEpisodio(name, source.getRegex());
		 * 
		 * SerieFileModel mediaFile = getSerieFileModel(id, epName);
		 * 
		 * if (mediaFile == null) { mediaFile = new SerieFileModel(); }
		 * 
		 * SerieFilePK pk = new SerieFilePK(); pk.setEpisodio(epName);
		 * pk.setIdSerie(id);
		 * 
		 * mediaFile.setPk(pk); mediaFile.setFile(media);
		 * mediaFile.setFilePath(mediaFile.getFile().getAbsolutePath());
		 * em.persist(mediaFile); } }
		 **/
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

	public SerieFileModel getSerieFileModel(Integer idSerie, String episodio, Integer temporada) {
		SerieFilePK pk = new SerieFilePK();
		pk.setEpisodio(episodio);
		pk.setIdSerie(idSerie);
		pk.setTemporada(temporada);
		return em.find(SerieFileModel.class, pk);
	}

	public List<SerieModel> listarSeries() {
		List<SerieModel> tmp = new ArrayList<SerieModel>();
		List<SerieModel> series = em.createQuery("from SerieModel s", SerieModel.class).getResultList();
		if (series != null) {
			for (SerieModel s : series) {
				s.setFiles(listarSeriesFiles(s.getId()));
				tmp.add(s);
			}
		}
		return tmp;
	}

	public List<SerieFileModel> listarSeriesFiles(Integer idSerie) {
		@SuppressWarnings("unchecked")
		List<SerieFileModel> sfm = (List<SerieFileModel>) em
				.createQuery("SELECT c FROM SerieFileModel c WHERE c.pk.idSerie = :serieId ")
				.setParameter("serieId", idSerie).getResultList();
		return sfm;
	}

	@Override
	public List<SerieFileModel> listarSeriesFilesByPath(String path) {
		@SuppressWarnings("unchecked")
		List<SerieFileModel> sfm = (List<SerieFileModel>) em
				.createQuery("SELECT c FROM SerieFileModel c WHERE c.filePath = :path ").setParameter("path", path)
				.getResultList();
		return sfm;
	}

	@Override
	public HistoricoModel ultimoEpisodioAssistido(Integer idSerie) {
		try {
			Query q = em.createQuery(
					"SELECT h FROM HistoricoModel h WHERE h.pk.idSerie = :idSerie ORDER BY h.lastTimeWatched DESC ");
			q.setParameter("idSerie", idSerie);
			q.setMaxResults(1);

			return (HistoricoModel) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public <T> T find(Class<T> type, Object key) {
		return em.find(type, key);
	}
	
	public void deleteAllSeriesFileModel(Integer idSerie){
	    List<SerieFileModel> seriesFileModel = listarSeriesFiles(idSerie);
	    if(seriesFileModel!=null){
		for(SerieFileModel serieF:seriesFileModel){
		    em.remove(serieF);
		}
	    }
	}

	@Override
	public void salvarHistorico(HistoricoModel historicoModel) {
		HistoricoModel hist;

		hist = em.find(HistoricoModel.class, historicoModel.getPk());
		if (hist == null) {
			em.persist(historicoModel);
		} else {
			hist.setLastPosition(historicoModel.getLastPosition());
			hist.setLastTimeWatched(historicoModel.getLastTimeWatched());
			hist.setPercentWatched(historicoModel.getPercentWatched());
			hist.setPositionstring(historicoModel.getPositionstring());
			em.persist(hist);
		}
	}

}