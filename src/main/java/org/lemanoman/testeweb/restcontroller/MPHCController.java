package org.lemanoman.testeweb.restcontroller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
public class MPHCController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();
	private ObjectMapper mapper = new ObjectMapper();

	@RequestMapping("/statusMPHC")
	public MPHCResponseModel getStatus() {
		HttpClient httpclient = HttpClientBuilder.create().build();
		HttpGet httpGet = new HttpGet("http://localhost:13579/variables.html");
		
		StringBuffer result = new StringBuffer();
		try {
			HttpResponse response = httpclient.execute(httpGet);
			
			if (response.getStatusLine().getStatusCode() == 200) {
				BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

				String line = "";
				while ((line = rd.readLine()) != null) {
					result.append(line);
				}
				// System.out.println(result);
				
				String html = result.toString();
				Document doc = Jsoup.parse(html);
				ObjectNode node = mapper.createObjectNode();
				for (Element el : doc.getElementsByTag("p")) {
					if(el.text().contains("NaN")){
						node.put(el.id(), "0");
					}else{
						node.put(el.id(), el.text());
					}
					
				}
				MPHCResponseModel responseModel = mapper.convertValue(node, MPHCResponseModel.class);
				responseModel.setStatusType(MPHCStatusType.RUNNING);
				return responseModel; 
			}else{
				return new MPHCResponseModel();
			}
		} catch (HttpHostConnectException e) {
			MPHCResponseModel response = new MPHCResponseModel();
			response.setStatusType(MPHCStatusType.STOPPED);
			return response;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		MPHCResponseModel response = new MPHCResponseModel();
		response.setStatusType(MPHCStatusType.ERROR);
		return response;
	}

}