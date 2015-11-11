package org.lemanoman.testeweb.storage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.lemanoman.testeweb.model.HistoryModel;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class StorageManager {
	private ObjectMapper mapper = new ObjectMapper();
	private File dataFile = new File("F:\\Series\\data.json");
	private ArrayNode entrys = mapper.createArrayNode();
	private ObjectNode node = mapper.createObjectNode();
	
	public StorageManager() throws IOException{
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		if(!dataFile.exists()){
			dataFile.createNewFile();
			ObjectNode node = mapper.createObjectNode();
			try {
				mapper.writeValue(dataFile, node);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}else{
			try {
				node = mapper.readValue(dataFile, ObjectNode.class);
				
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			if(node.get("entrys") != null){
				entrys = mapper.convertValue(node.get("entrys"),ArrayNode.class);
			}
		}
	}
	
	public void save(HistoryModel history){
		ObjectNode node = mapper.convertValue(history, ObjectNode.class);
		ObjectNode lastNode = getEntry(history.getMedia().getEpisodio()); 
		if(lastNode==null){
			entrys.add(node);
		}else{
			lastNode = node;
		}
		updateJson();
		
	}
	
	private void updateJson(){
		node.set("entrys", entrys);
		try {
			mapper.writeValue(dataFile, node);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public ObjectNode getEntry(String code){
		if(entrys != null){
			for(int i=0;i<entrys.size();i++){
				ObjectNode node = mapper.convertValue(entrys.get(i),ObjectNode.class);
				if(node.get("status") != null && code.equals(node.get("status").asText())){
					ObjectNode status = mapper.convertValue(node.get("status"),ObjectNode.class);
					if(status.get("file") != null && code.equals(status.get("file").asText())){
						return node;
					}	
				}
			}		
		}
		return null;
	}
	
	public List<ObjectNode> listWatchedToday(){
		List<ObjectNode> objNodes = new ArrayList<ObjectNode>();
		for(int i=0;i<entrys.size();i++){
			ObjectNode tmp = mapper.convertValue(entrys.get(i),ObjectNode.class);
			if(tmp.get("lastTimeWatched")!=null){
				long time = tmp.get("lastTimeWatched").asLong();
				
				Calendar todayStart = Calendar.getInstance();
				todayStart.set(Calendar.HOUR_OF_DAY,0);
				todayStart.set(Calendar.MINUTE,0);
				todayStart.set(Calendar.SECOND,0);
				
				
				Calendar todayEnd = Calendar.getInstance();
				todayEnd.set(Calendar.HOUR_OF_DAY,23);
				todayEnd.set(Calendar.MINUTE,59);
				todayEnd.set(Calendar.SECOND,59);
				
				if(time>=todayStart.getTimeInMillis() && time<=todayEnd.getTimeInMillis()){
					objNodes.add(tmp);
				}
			}
		}
		return objNodes;
	}
}
