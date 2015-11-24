package org.lemanoman.testeweb.configuration;

import java.util.Date;
import java.util.List;

import org.lemanoman.testeweb.model.HistoricoModel;
import org.lemanoman.testeweb.model.HistoricoPK;
import org.lemanoman.testeweb.model.SerieFileModel;
import org.lemanoman.testeweb.model.rest.MPHCResponseModel;
import org.lemanoman.testeweb.model.rest.MPHCStatusType;
import org.lemanoman.testeweb.restcontroller.MPHCController;
import org.lemanoman.testeweb.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class ShedulerConfig {
    @Autowired
    private MPHCController controller;
    
    @Autowired
    private SerieService service;
    
    @Scheduled(fixedDelay=5000)
    public void doSomething() {
    	MPHCResponseModel status = controller.getStatus();
    	if(status!=null && status.getStatusType()!=null && status.getStatusType().equals(MPHCStatusType.RUNNING)){
    		List<SerieFileModel> smfs = service.listarSeriesByPath(status.getFilepath());
    		if(smfs!=null && smfs.size()>0){
    			for(SerieFileModel sfm:smfs){
	    			HistoricoPK pk = new HistoricoPK();
	    			pk.setEpisodio(sfm.getPk().getEpisodio());
	    			pk.setIdSerie(sfm.getPk().getIdSerie());
	    			pk.setTemporada(sfm.getPk().getTemporada());
	    			
	    			HistoricoModel history = new HistoricoModel();
	    			history.setLastTimeWatched(new Date());
	    			
	    			Double percent = 0d;
	    			Double positionDouble = Double.parseDouble(status.getPosition().toString());
	    			Double durationDouble = Double.parseDouble(status.getDuration().toString());
	    			
	    			percent = positionDouble/durationDouble;
	    			percent = percent*100;
	    			
	    			history.setPercentWatched(percent);
	    			history.setLastPosition(status.getPosition());
	    			history.setPositionstring(status.getPositionstring());
	    			history.setPk(pk);
	    			service.salvarHistorico(history);
	    			
    			}	
    		}
    		
    	}
    	
    	
    }
}
