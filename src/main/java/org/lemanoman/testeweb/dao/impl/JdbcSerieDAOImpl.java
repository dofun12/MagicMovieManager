package org.lemanoman.testeweb.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.lemanoman.testeweb.dao.JdbcSerieDAO;
import org.lemanoman.testeweb.model.EpisodioModel;
import org.lemanoman.testeweb.model.MediaFileModel;
import org.lemanoman.testeweb.model.SerieModel;
import org.lemanoman.testeweb.model.rest.NovaSerieModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcSerieDAOImpl extends JdbcBaseDAOImpl<EpisodioModel> implements JdbcSerieDAO {

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
	
	public List<SerieModel> listarSeries() {
		List<SerieModel> tmp = new ArrayList<SerieModel>();
		List<SerieModel> series = em.createQuery("from SerieModel s", SerieModel.class).getResultList();
		if (series != null) {
			for (SerieModel s : series) {
			    //s.setFiles(listarSeriesFiles(s.getId()));
			    tmp.add(s);
			}
		}
		return tmp;
	}

	public void adicionarSerie(NovaSerieModel novaSerie) {
		SerieModel serie = null;
		if(novaSerie.getSerie()!=null){
			if(novaSerie.getSerie().getId()!=null){
				serie = find(SerieModel.class, novaSerie.getSerie().getId());
			}else{
				serie = new SerieModel();
				serie.setName(novaSerie.getSerie().getName());
				if(novaSerie.getSerie()!=null && novaSerie.getSerie().getVisible()){
				    serie.setVisible(novaSerie.getSerie().getVisible()); 
				}else{
				    serie.setVisible(true);
				}
				
				em.persist(serie);
				em.flush();
			}	
		}
			

		Integer idSerie = serie.getId();
		for (EpisodioModel ep : novaSerie.getEpisodios()) {
		    MediaFileModel mediaFileModel = getMediaFileModel(ep.getIdMediafile());
		    if(mediaFileModel==null){
			mediaFileModel = new MediaFileModel();
		    }
		    
		}

	}
	public MediaFileModel getMediaFileModel(Integer id){
	    return em.find(MediaFileModel.class, id);
	}
	
	public <T> T find(Class<T> type, Object key) {
		return em.find(type, key);
	}
	
	/**
	public void updateCatalogo() {
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

	public EpisodioModel getEpisodio(Integer idSerie, String nameEpisodio, Integer temporada) {
	    	EpisodioPK pk = new EpisodioPK();
		pk.setName(nameEpisodio);
		pk.setIdSerie(idSerie);
		pk.setTemporada(temporada);
		return em.find(EpisodioModel.class, pk);
	}

	
	
	
	
	public List<SerieFileModel> listarSerieSecreta(SerieModel serieModel) {
		List<SerieFileModel> sfms = new ArrayList();
		File defaultPath = new File("F:\\DataFiles\\temp");
		if(serieModel!=null && serieModel.getSecret()){
			for(File f:defaultPath.listFiles()){
				if(f.getName().matches(".*flv")){
					SerieFileModel model = new SerieFileModel();
					model.setFile(f);
					model.setFilePath(f.getAbsolutePath());
					SerieFilePK pk = new SerieFilePK();
					Pattern pattern = Pattern.compile(".*([0-9]{3,4}).flv");
					Matcher matcher = pattern.matcher(f.getName());
					if (matcher.matches()) {
						pk.setEpisodio(matcher.group(1));
					}else{
						pk.setEpisodio("???");
					}
					pk.setTemporada(0);
					pk.setIdSerie(serieModel.getId());
					model.setPk(pk);
					sfms.add(model);
				}
			}
		}	
		return sfms;
	}

	public List<SerieFileModel> listarSeriesFiles(Integer idSerie) {
		@SuppressWarnings("unchecked")
		List<SerieFileModel> sfm = (List<SerieFileModel>) em
				.createQuery("SELECT c FROM SerieFileModel c WHERE c.pk.idSerie = :serieId ")
				.setParameter("serieId", idSerie).getResultList();
		return sfm;
	}

	@Override
	public List<EpisodioModel> listarSeriesFilesByPath(String path) {
		@SuppressWarnings("unchecked")
		List<EpisodioModel> sfm = (List<EpisodioModel>) em
				.createQuery("SELECT c FROM EpisodioModel c WHERE c.filePath = :path ").setParameter("path", path)
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
	
	@Override
	public void setAllSecretSeriesInvisible() {
		try {
			List<SerieModel> series = new ArrayList<SerieModel>();
			Query q = em.createQuery(
					"SELECT s FROM SerieModel s WHERE s.secret > 0");
			;
			series = q.getResultList();
			if(series!=null && series.size()>0){
				for(SerieModel s:series){
					s.setVisible(false);
					em.persist(s);
				}
			}
		} catch (NoResultException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void setAllSecretSeriesVisible() {
		try {
			List<SerieModel> series = new ArrayList<SerieModel>();
			Query q = em.createQuery(
					"SELECT s FROM SerieModel s WHERE s.secret > 0 ");
			;
			series = q.getResultList();
			if(series!=null && series.size()>0){
				for(SerieModel s:series){
					s.setVisible(true);
					em.persist(s);
				}
			}
		} catch (NoResultException e) {
			e.printStackTrace();
		}
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
	**/
}