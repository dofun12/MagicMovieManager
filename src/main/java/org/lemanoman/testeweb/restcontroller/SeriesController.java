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
import org.lemanoman.testeweb.model.GreetingModel;
import org.lemanoman.testeweb.model.MPHCResponseModel;
import org.lemanoman.testeweb.model.MPHCStatusType;
import org.lemanoman.testeweb.model.MediaFileModel;
import org.lemanoman.testeweb.model.SerieSource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
public class SeriesController {

	@RequestMapping("/listarSeries")
	public List<SerieSource> listarSeries() {
		List<SerieSource> lista = new ArrayList<SerieSource>();
		SerieSource bigbang = new SerieSource("Big Bang Theory","F:\\Series\\BigBang", ".*(S[0-9]{2}E[0-9]{2}).*");
		SerieSource fearoftwd = new SerieSource("Fear of the Walking Dead","F:\\Series\\FearOFWalkingDead", ".*([0-9]{2}).mp4");
		SerieSource naruto = new SerieSource("Naruto","F:\\Series\\Naruto", ".*([0-9]{3}).mp4");
		SerieSource onepiece = new SerieSource("One Piece","F:\\Series\\OnePiece", ".*([0-9]{3}).mp4");

		lista.add(bigbang);
		lista.add(naruto);
		lista.add(onepiece);
		lista.add(fearoftwd);
		
		return lista;
	}

}