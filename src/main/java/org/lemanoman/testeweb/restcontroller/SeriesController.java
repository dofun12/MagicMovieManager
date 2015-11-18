package org.lemanoman.testeweb.restcontroller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;

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
import org.lemanoman.testeweb.dao.JdbcSerieDAO;
import org.lemanoman.testeweb.model.FileModel;
import org.lemanoman.testeweb.model.GreetingModel;
import org.lemanoman.testeweb.model.HistoricoModel;
import org.lemanoman.testeweb.model.HistoricoPK;
import org.lemanoman.testeweb.model.MPHCResponseModel;
import org.lemanoman.testeweb.model.MPHCStatusType;
import org.lemanoman.testeweb.model.NovaSerieModel;
import org.lemanoman.testeweb.model.RegexTesterModel;
import org.lemanoman.testeweb.model.SerieFileModel;
import org.lemanoman.testeweb.model.SerieModel;
import org.lemanoman.testeweb.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
public class SeriesController {

    @Autowired
    public SerieService service;

    @RequestMapping(value = "/listarSeries", method = RequestMethod.GET)
    public List<SerieModel> listarSeries() {
	service.updateCatalogo();
	return service.listarSeries();
    }

    @RequestMapping(value = "/testarRegex", method = RequestMethod.POST)
    public List<FileModel> testarRegex(@RequestBody RegexTesterModel regex) {
	List<FileModel> list = new ArrayList<FileModel>();
	for (FileModel file : regex.getFiles()) {
	    try {
		Pattern pattern = Pattern.compile(regex.getRegex());
		Matcher matcher = pattern.matcher(file.getName());
		if (matcher.matches()) {
		    file.setEpisodio(matcher.group(1));
		}else{
		    file.setEpisodio("???");
		}
		list.add(file);
	    } catch (Exception e) {
		e.printStackTrace();
	    }	
	}
	return list;
    }

    @RequestMapping(value = "/adicionarSerie", method = RequestMethod.POST)
    public void adicionarSerie(@RequestBody NovaSerieModel novaSerie) {
	if(novaSerie!=null){
	    service.adicionarSerie(novaSerie);
	}
    }

    @RequestMapping(value = "/ultimaSerieAssistida", method = RequestMethod.POST)
    public HistoricoModel ultimaSerieAssistida(@RequestBody SerieModel serie) {
	return service.getUltimaSerieAssistida(serie.getId());
    }

    @RequestMapping(value = "/buscarHistoricoEpisodio", method = RequestMethod.POST)
    public HistoricoModel ultimaSerieAssistida(@RequestBody SerieFileModel serie) {
	HistoricoPK pk = new HistoricoPK();
	pk.setEpisodio(serie.getPk().getEpisodio());
	pk.setIdSerie(serie.getPk().getIdSerie());
	return service.getHistoricoModel(pk);
    }

    @RequestMapping(value = "/fileChooser", method = RequestMethod.GET)
    public List<FileModel> fileChooser() {
	List<FileModel> list = new ArrayList<FileModel>();
	JFileChooser fc = new JFileChooser();

	fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
	fc.setMultiSelectionEnabled(true);
	fc.showOpenDialog(null);

	
	if (fc.getSelectedFiles() != null) {
	    for (File sf : fc.getSelectedFiles()) {
		FileModel f = new FileModel();
		f.setPath(sf.getAbsolutePath());
		f.setName(sf.getName());
		f.setSize(sf.length());
		list.add(f);
	    }
	}
	return list;
    }

}