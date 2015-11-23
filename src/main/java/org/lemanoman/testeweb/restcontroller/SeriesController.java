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
import javax.swing.JFrame;

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
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
public class SeriesController {

    @Autowired
    public SerieService service;

    @RequestMapping(value = "/listarSeries", method = RequestMethod.GET)
    public List<SerieModel> listarSeries() {
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
		} else {
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
	if (novaSerie != null) {
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
	JFrame teste = new JFrame("Gambiarra");
	teste.setVisible(true);
	teste.setSize(10, 10);
	teste.getContentPane().add(fc);
	fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
	fc.setMultiSelectionEnabled(true);
	fc.setVisible(true);
	fc.showOpenDialog(teste);

	if (fc.getSelectedFiles() != null) {
	    for (File sf : fc.getSelectedFiles()) {
		FileModel f = new FileModel();
		f.setPath(sf.getAbsolutePath());
		f.setName(sf.getName());
		f.setSize(sf.length());
		list.add(f);
	    }
	}
	teste.setVisible(false);
	teste.dispose();
	return list;
    }

    @RequestMapping(value = "/validarSeries", method = RequestMethod.POST)
    public ObjectNode validarSeries(@RequestBody NovaSerieModel novaSerie) {
	ObjectMapper mapper = new ObjectMapper();
	ObjectNode response = mapper.createObjectNode();
	boolean isValid = true;
	String message = "";
	if (novaSerie != null && novaSerie.getSerie() != null && novaSerie.getSerie().getName() != null
		&& novaSerie.getSerie().getName().length() > 0) {
	    if (novaSerie.getEpisodios() != null && novaSerie.getEpisodios().size() > 0) {
		for (FileModel fm : novaSerie.getEpisodios()) {
		    if (fm.getEpisodio() != null) {
			if (!fm.getEpisodio().matches(".*[0-9].*")) {
			    isValid = false;
			    message = "O episodio deve conter pelo menos um numero";
			}
		    } else {
			isValid = false;
		    }
		    if (novaSerie.getTemporada() == null) {
			isValid = false;
			message = "Selecione uma Temporada";
		    }
		}
	    } else {
		message = "Adicione pelo menos um episodio";
		isValid = false;
	    }
	} else {
	    message = "Selecione ou escreva o nome da Serie";
	    isValid = false;
	}
	response.put("message", message);
	response.put("isValid", isValid);
	return response;
    }

    @RequestMapping(value = "/viewDirectory", method = RequestMethod.POST)
    public List<FileModel> viewDirectory(@RequestBody FileModel fileModel) {
	List<FileModel> filesModel = new ArrayList<FileModel>();
	if (fileModel != null && fileModel.getPath() != null && fileModel.getPath().length()>0) {
	    File file = new File(fileModel.getPath());
	    if (file.isDirectory()) {
		int id = 1;
		File[] tmpArray = file.listFiles();
		if(tmpArray!=null && tmpArray.length>0){
		    for (File fileChild : file.listFiles()) {
			FileModel model = fillFileModel(fileChild,id);
			filesModel.add(model);
			id++;
		    }
		}
	    } else {
		return null;
	    }
	} else {
	    if (new File("/").exists()) {
		File fileChild = new File("/");
		FileModel model = fillFileModel(fileChild,0);
		filesModel.add(model);
	    } else {
		char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		int id = 1;
		for (char abc : alphabet) {
		    String windowDevices = abc + ":\\";
		    windowDevices = windowDevices.toUpperCase();
		    if (new File(windowDevices).exists()) {
			File fileChild = new File(windowDevices);
			FileModel model = fillFileModel(fileChild,id);
			filesModel.add(model);
		    }
		    id++;
		}
	    }
	}
	return filesModel;
    }
    @RequestMapping(value = "/viewParentDirectory", method = RequestMethod.POST)
    public ObjectNode viewParentDirectory(@RequestBody FileModel fileModel) {
	ObjectMapper mapper = new ObjectMapper();
	ArrayNode filesModel = mapper.createArrayNode();
	ObjectNode parentFile = null;
	if (fileModel != null && fileModel.getPath() != null && fileModel.getPath().length()>0) {
	    File file = new File(fileModel.getPath());
	    if(file.exists()){
		file = file.getParentFile();
		if(file == null){
		    if (new File("/").exists()) {
			File fileChild = new File("/");
			FileModel model = fillFileModel(fileChild,0);
			filesModel.add(mapper.convertValue(model, ObjectNode.class));
		    } else {
			char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
			int id = 1;
			for (char abc : alphabet) {
			    String windowDevices = abc + ":\\";
			    windowDevices = windowDevices.toUpperCase();
			    if (new File(windowDevices).exists()) {
				File fileChild = new File(windowDevices);
				FileModel model = fillFileModel(fileChild,id);
				filesModel.add(mapper.convertValue(model, ObjectNode.class));
			    }
			    id++;
			}

		    }

		}else{
		    FileModel parentModel = fillFileModel(file,0);
		    parentFile = mapper.convertValue(parentModel, ObjectNode.class);
		    if (file.isDirectory()) {
			int id = 1;
			for (File fileChild : file.listFiles()) {
			    FileModel model = fillFileModel(fileChild,id);
			    filesModel.add(mapper.convertValue(model, ObjectNode.class));
			    id++;
			}
		    } else {
			return null;
		    }    
		}
	    } else {
		return null;
	    } 
	}
	ObjectNode response = mapper.createObjectNode();
	response.set("files",filesModel);
	if(parentFile!=null && parentFile.size()>0){
	    response.set("parentfile",parentFile);	    
	}else{
	    response.set("parentfile",null);
	}
	
	return response;
    }

    public FileModel fillFileModel(File fileChild,Integer id){
	FileModel model = new FileModel();
	model.setId(id);
	model.setPath(fileChild.getAbsolutePath());
	if(fileChild.getName()!=null && fileChild.getName().length()>0){
	    model.setName(fileChild.getName());
	}else{
	    model.setName(fileChild.getPath());
	}
	model.setSize(fileChild.length());
	model.setDirectory(fileChild.isDirectory());
	return model;
    }

}