package org.lemanoman.testeweb.restcontroller;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
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
import org.lemanoman.testeweb.model.GreetingModel;
import org.lemanoman.testeweb.model.HistoryModel;
import org.lemanoman.testeweb.model.MPHCResponseModel;
import org.lemanoman.testeweb.model.MPHCStatusType;
import org.lemanoman.testeweb.model.SerieFileModel;
import org.lemanoman.testeweb.model.SerieModel;
import org.lemanoman.testeweb.storage.StorageManager;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
public class HistoryController {
	private ObjectMapper mapper = new ObjectMapper();
		
	@RequestMapping(value = "/saveHistory", method = RequestMethod.POST)
	public void saveHistory(@RequestBody HistoryModel historyModel) {
		historyModel.setLastTimeWatched(new Date());
		
		/**
		try {
			StorageManager manager;
			manager = new StorageManager();
			manager.save(historyModel);
		} catch (IOException e) {
			e.printStackTrace();
		}
		**/
	}

}