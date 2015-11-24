package org.lemanoman.testeweb.restcontroller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.lemanoman.testeweb.misc.Misc;
import org.lemanoman.testeweb.model.SerieFileModel;
import org.lemanoman.testeweb.model.SerieModel;
import org.lemanoman.testeweb.model.rest.GreetingModel;
import org.lemanoman.testeweb.model.rest.MPHCPlayModel;
import org.lemanoman.testeweb.model.rest.MPHCResponseModel;
import org.lemanoman.testeweb.model.rest.MPHCStatusType;
import org.lemanoman.testeweb.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
public class MovieSessionController {
    @Autowired
    private MPHCController mphcController;

    @Autowired
    private SerieService service;

    @RequestMapping(value = "/play", method = RequestMethod.POST)
    public void playMovie(@RequestBody MPHCPlayModel media) {
	if (media != null) {
	    try {
		StringBuilder command = new StringBuilder();
		command.append("C:\\Program Files\\MPC-HC\\mpc-hc64.exe ");
		if (media.getSerie() != null && media.getSerie().getFilePath() != null) {
		    command.append(media.getSerie().getFilePath() + " ");
		} else {
		    MPHCResponseModel response = mphcController.getStatus();
		    command.append(response.getFilepath() + " ");
		}

		if (media.isFullscreen()) {
		    command.append("/fullscreen ");
		}
		if (media.getPositionLong() != null) {
		    command.append("/start " + media.getPositionLong() + " ");
		} else if (media.getPosition() != null) {
		    command.append("/startpos " + media.getPosition() + " ");
		} else {
		    command.append("/play ");
		}

		String commandStr = command.toString();
		System.out.println(commandStr);
		Runtime.getRuntime().exec(commandStr);
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
    }

    @RequestMapping(value = "/desligar", method = RequestMethod.POST)
    public void desligar() {
	try {
	    StringBuilder command = new StringBuilder();
	    command.append("shutdown /s");

	    String commandStr = command.toString();
	    System.out.println(commandStr);
	    Runtime.getRuntime().exec(commandStr);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    @RequestMapping(value = "/showAll", method = RequestMethod.POST)
    public void shoAll(@RequestBody MPHCPlayModel media) {
	Misc misc;
	try {
	    if (media.getPassword().equals("teste")) {
		misc = new Misc();
		misc.showAll();
		misc.copiarImagens();
		service.setAllSecretSeriesVisible();
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}

    }

    @RequestMapping(value = "/atualizar", method = RequestMethod.POST)
    public void atualizar(@RequestBody MPHCPlayModel media) {
	Misc misc;
	try {
	    if (media.getPassword().equals("teste")) {
		misc = new Misc();
		misc.searchAndMove();

	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}

    }

    @RequestMapping(value = "/hideAll", method = RequestMethod.POST)
    public void hideAll(@RequestBody MPHCPlayModel media) {
	Misc misc;
	try {
	    if (media.getPassword().equals("teste")) {
		misc = new Misc();
		misc.removerImagens();
		misc.hideAll();
		service.setAllSecretSeriesInvisible();
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}

    }

}