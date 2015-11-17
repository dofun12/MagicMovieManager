package org.lemanoman.testeweb.job;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component("controler")
public class ControladorQuartz {
    public void metodoQuartz() {
	System.out.println("Executando Quartz em: "
		+ new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date((System.currentTimeMillis()))));
	
	System.out.println("Executando Quartz em: "
		+ new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date((System.currentTimeMillis()))));
    }
}
