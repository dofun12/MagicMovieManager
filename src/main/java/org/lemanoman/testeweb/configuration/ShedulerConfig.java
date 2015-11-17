package org.lemanoman.testeweb.configuration;

import java.util.List;

import org.lemanoman.testeweb.model.MPHCResponseModel;
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
    
    @Scheduled(fixedDelay=3000)
    public void doSomething() {
	MPHCResponseModel status = controller.getStatus();
	System.out.println("OK: "+status.getState());
    }
}
