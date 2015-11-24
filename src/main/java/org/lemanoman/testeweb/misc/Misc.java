package org.lemanoman.testeweb.misc;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.xml.ws.handler.MessageContext;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.WinReg;


public class Misc {
	private String flvPath = "F:\\DataFiles\\temp";
	private String imagePath = flvPath+"\\bin";
	private String downloadPath = "C:\\Users\\KevimDesktop\\Downloads\\Ant Videos";
	private String workImages = "C:\\Users\\KevimDesktop\\workspace\\MagicMovieManager\\src\\main\\webapp\\images";
	
	private List<String> responses = new ArrayList<String>();
	private ObjectMapper mapper = new ObjectMapper();
	private File dataFile = new File(flvPath+"\\"+"data.json");
	private ArrayNode entrys = mapper.createArrayNode();
	private ObjectNode node = mapper.createObjectNode();
	//private CryptUtil cryptUtil = new CryptUtil(dataFile);
	
	private static String MPC_EXE="";

	public Misc() throws IOException{
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

	public void hideAll(){
		changVisibility(false);
	}
	public void showAll(){
		changVisibility(true);
	}

	private void changVisibility(boolean visibility){
		try {
			List<File> files = new ArrayList<File>();
			File file = new File(flvPath);
			File file2 = new File(flvPath+"\\bin");
			files.addAll(Arrays.asList(file.listFiles()));
			files.addAll(Arrays.asList(file2.listFiles()));
			
			for(File f:files){
				String name = f.getName();
				if(visibility){
					if(name.matches(".*dat")){
						String strNum = name.substring(0,name.length()-4);
						f.renameTo(new File(flvPath+"\\"+strNum+".flv"));
					}
					if(name.matches(".*dll")){
						String strNum = name.substring(0,name.length()-4);
						f.renameTo(new File(imagePath+"\\"+strNum+".png"));
					}
				}else{
					if(name.matches(".*flv")){
						String strNum = name.substring(0,name.length()-4);
						f.renameTo(new File(flvPath+"\\"+strNum+".dat"));
					}
					if(name.matches(".*png")){
						String strNum = name.substring(0,name.length()-4);
						f.renameTo(new File(imagePath+"\\"+strNum+".dll"));
					}
				}	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void gerarImagens(){
		File file = new File(flvPath);
		
		for(File f:file.listFiles()){
			try {
				File tmpFile = new File(imagePath+"\\"+f.getName()+".png"); 
				if(!tmpFile.exists()){
					if(!new File(imagePath).exists()){
						new File(imagePath).mkdir();
					}
					if(f.getName().matches(".*flv")){
						//C:\ffmpeg\bin\ffmpeg.exe -i F:\DataFiles\temp\00117.flv -vf fps=1/180 F:\DataFiles\temp\images\00117.bmp
						//ffmpeg -i input.flv -ss 00:00:14.435 -vframes 1 out.png
						//String command = "C:\\ffmpeg\\bin\\ffmpeg.exe -i "+f.getAbsolutePath()+" -vf fps=1/180 "flvPath\\images\\"+f.getName()+".png";
						String name = f.getName().replaceAll("flv", "");
						String command = "C:\\ffmpeg\\bin\\ffmpeg.exe -i "+f.getAbsolutePath()+" -ss 00:00:14.435 -vframes 1 "+imagePath+"//"+name+"png";
						System.out.println(command);
						Process p = Runtime.getRuntime().exec(command);
						System.out.print(readCommandOutput(p));
					}	
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}


	public String readCommandOutput(Process p){
		String answer = "";
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			StringBuffer sb = new StringBuffer();
			String line;

			while ((line = br.readLine()) != null) {
				sb.append(line).append("\n");
			}
			answer = sb.toString();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return answer;
	}

	public void listarUltimosAssistidos(){
		// 	Read a string

		String regepath = "Software\\MPC-HC\\MPC-HC\\Recent File List".replaceAll(" ", "\\ ");

		boolean hasAlterations = false;
		for(String x:Advapi32Util.registryGetValues(WinReg.HKEY_CURRENT_USER, regepath).keySet()){
			String value = Advapi32Util.registryGetStringValue(
					WinReg.HKEY_CURRENT_USER, regepath, x);

			String fileName = FilenameUtils.getName(value);
			String code = getCode(fileName);

			addMessage(fileName);
			addMessage(code);

			ObjectNode nodeTmp = getEntry(code);
			if(nodeTmp!=null){
				if(nodeTmp.get("watched")!=null){
					Integer watched = nodeTmp.get("watched").asInt();
					nodeTmp.put("watched",(watched+1));
					nodeTmp.put("lastTimeWatched",new Date().getTime());
				}else{
					nodeTmp.put("watched",1);
					nodeTmp.put("lastTimeWatched",new Date().getTime());
				}
			}else{	
				nodeTmp = mapper.createObjectNode();
				nodeTmp.put("hash","");
				nodeTmp.put("code",code);
				nodeTmp.put("realName","");
				nodeTmp.put("lastTimeWatched",new Date().getTime());
				nodeTmp.put("watched",1);
				entrys.add(nodeTmp);
			}

			addMessage(nodeTmp);
			Advapi32Util.registryDeleteValue(WinReg.HKEY_CURRENT_USER, regepath, x);
			hasAlterations = true;
		}
		if(hasAlterations){
			updateJson();
		}else{
			addMessage("Nada novo");
		}	
	}
	
	public void removerImagens(){
		File file = new File(workImages);
		for(File f:file.listFiles()){
			f.delete();
		}
	}
	
	public void copiarImagens(){
		File file = new File(imagePath);
		for(File f:file.listFiles()){
			try {
				FileUtils.copyFile(f, new File(workImages+"\\"+f.getName()));
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}
	}
	
		
	
	public void searchAndMove(){
		File file = new File(downloadPath);
		List<File> toMove = new ArrayList<File>();
		for(File f:file.listFiles()){
			String name = f.getName();
			if(name.matches(".*flv") && f.length()>1024){
				toMove.add(f);
			}
		}

		int lastNum = getLastName();
		int before = lastNum;


		for(File t:toMove){


			lastNum++;
			String codigo = "00"+lastNum;
			String newFileName = codigo+".dat";

			ObjectNode entry = getEntry(codigo);

			if(entry==null){
				entry = mapper.createObjectNode();
				entry.put("hash",t.getName().toUpperCase().hashCode());
				entry.put("code",codigo);
				entry.put("realName",t.getName());
				entry.put("dateAdded",new Date().getTime());
				entrys.add(entry);
			}



			addMessage("Coping "+t.getName()+" to "+flvPath+"\\"+newFileName);


			try {
				FileUtils.copyFile(t, new File(flvPath+"\\"+newFileName));	
				t.delete();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		addMessage((lastNum-before)+" Novos adicionados");
		updateJson();
	}

	private void updateJson(){
		node.set("entrys", entrys);
		try {
			mapper.writeValue(dataFile, node);
			addMessage("Json Atualizado");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	public ObjectNode getEntry(String code){
		if(entrys != null){
			for(int i=0;i<entrys.size();i++){
				ObjectNode node = mapper.convertValue(entrys.get(i),ObjectNode.class);
				if(node.get("code") != null && code.equals(node.get("code").asText())){
					return node;
				}
			}		
		}
		return null;
	}

	public int getLastName(){
		File file = new File(flvPath);
		int lastNum = 0;
		for(File f:file.listFiles()){
			String name = f.getName();
			if(name.matches(".*dat") || name.matches(".*flv")){
				String strNum = getCode(name);
				Integer num = Integer.parseInt(strNum);
				if(num>lastNum){
					lastNum=num;
				}
			}
		}
		return lastNum;
	}

	private void addMessage(Object obj){
		System.out.println(obj);
		this.responses.add(obj != null?String.valueOf(obj):"");
	}

	public String getResponse(){
		StringBuilder responseBuilder = new StringBuilder("");
		for(String g:responses){

			responseBuilder.append(g);
			responseBuilder.append("\n");
		}
		responses = new ArrayList<String>();
		return responseBuilder.toString();
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
					addMessage(tmp);
					objNodes.add(tmp);
				}
			}
		}
		return objNodes;
	}

	public List<ObjectNode> listNeverWatched(){
		List<ObjectNode> objNodes = new ArrayList<ObjectNode>();
		for(int i=0;i<entrys.size();i++){
			ObjectNode tmp = mapper.convertValue(entrys.get(i),ObjectNode.class);
			if(tmp.get("watched")==null){
				addMessage(tmp);
				objNodes.add(tmp);
			}
		}
		return objNodes;
	}

	public void updateObjectNode(String code,ObjectNode newNode){
		ObjectNode tmp = getEntry(code);
		tmp.setAll(newNode);
		updateJson();
	}

	public void scanFiles(){
		File file = new File(flvPath);
		boolean hasNewFiles = false;
		for(File f:file.listFiles()){
			String name = f.getName();
			if(name.matches(".*dat") || name.matches(".*flv")){
				String code = getCode(name);
				if(getEntry(code)==null){
					hasNewFiles = true;
					ObjectNode nodeTmp = mapper.createObjectNode();
					nodeTmp.put("hash","");
					nodeTmp.put("code",code);
					nodeTmp.put("realName","");
					nodeTmp.put("dateAdded",f.lastModified());
					entrys.add(nodeTmp);	
				}

			}
		}
		if(hasNewFiles)updateJson();
	}

	public void playVideo(String code){
		try {
			File dat = new File(flvPath+"\\"+code+".dat");
			if(dat.exists()){
				dat.renameTo(new File(flvPath+"\\"+code+".flv"));
				Runtime.getRuntime().exec("C:\\Program Files\\MPC-HC\\mpc-hc64.exe /play "+flvPath+"\\"+code+".flv");
			}else{
				File flv = new File(flvPath+"\\"+code+".flv");
				if(flv.exists()){
					Runtime.getRuntime().exec("C:\\Program Files\\MPC-HC\\mpc-hc64.exe /play "+flv.getAbsolutePath());
				}	
			}
			System.out.println("END");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void playVideos(List<String> codes){
		try {
			StringBuilder commandBuilder = new StringBuilder("C:\\Program Files\\MPC-HC\\mpc-hc64.exe /play ");
			for(String code:codes){
				commandBuilder.append(flvPath);
				commandBuilder.append(code);
				commandBuilder.append(".flv ");
			}
			Runtime.getRuntime().exec(commandBuilder.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getCode(String filename){
		return filename.substring(0,filename.length()-4);
	}
}
