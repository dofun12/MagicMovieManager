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
import org.lemanoman.testeweb.model.MPHCPlayModel;
import org.lemanoman.testeweb.model.MPHCResponseModel;
import org.lemanoman.testeweb.model.MPHCStatusType;
import org.lemanoman.testeweb.model.SerieFileModel;
import org.lemanoman.testeweb.model.SerieModel;
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

	@RequestMapping(value = "/play", method = RequestMethod.POST)
	public void playMovie(@RequestBody MPHCPlayModel media) {
		if(media!=null){
			try {
				StringBuilder command = new StringBuilder();
				command.append("C:\\Program Files\\MPC-HC\\mpc-hc64.exe ");
				if(media.isFullscreen()){
					command.append("/fullscreen ");
				}
				if(media.getPosition()!=null){
					command.append("/startpos "+media.getPosition()+" ");
				}else{
					command.append("/play ");
				}
				command.append(media.getSerie().getFilePath());
				String commandStr = command.toString();
				System.out.println(commandStr);
				Runtime.getRuntime().exec(commandStr);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}