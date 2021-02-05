package com.movie.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class NaverAPI {
	
	public StringBuffer OpenAPI(String txt) throws IOException {
		HttpURLConnection con = null;
		URL url = null;
		
		String clientId = "Ro6lPkPlr5hgHkIkspco";
		String clienSecret = "SXNMGQfnK7";
		
		String apiURL = "https://openapi.naver.com/v1/search/movie.json";
		String rURL = apiURL + "?query="+txt;
		
		StringBuffer response = new StringBuffer();
		
		url = new URL(rURL);
		con = (HttpURLConnection)url.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("X-Naver-Client-Id", clientId);
		con.setRequestProperty("X-Naver-Client-secret", clienSecret);
		
		int responseCode = con.getResponseCode();
		BufferedReader br;
		
		if(responseCode == 200) {
			br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		}else {
			br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
		}
		
		String inputLine;
		
		while((inputLine = br.readLine()) != null) {
			response.append(inputLine + "\n");
		}
		
		br.close();
		
		return response;
	}
	
	public Map<String, String> getPoster(String query, String yearfrom, String yearto) throws IOException {
		query = URLEncoder.encode(query,"UTF-8");
		
		if(yearto.equals("")) {
			yearto = yearfrom;
		}else {
			yearto = yearto.substring(0, 4);
		}
		
		String txt = query + "&yearfrom=" + yearfrom + "&yearto=" + yearto + "&display=1";
		
		StringBuffer br = OpenAPI(txt);
		
		JsonParser jsonParser = new JsonParser();
		JsonObject nav_mov = (JsonObject)jsonParser.parse(br.toString());
		JsonArray mov_arr = (JsonArray)nav_mov.get("items");
		
		String mov_poster = "";
		String mov_link = "";
		
		try { 
			JsonObject mov = (JsonObject)mov_arr.get(0);
			mov_poster = mov.get("image").toString().replaceAll("\\\"", "").trim();
			mov_link = mov.get("link").toString().replaceAll("\\\"", "").trim();
			
		}catch (Exception e) {
			mov_poster = "./resources/img/nophoto.png";
			mov_link = "#";
		}
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("mov_poster", mov_poster);
		map.put("mov_link", mov_link);
		
		return map;
	}
	
	
	public Map<String, List<String>> getImage(JsonArray movie_info) throws IOException {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		
		List<String> mov_poster = new ArrayList<String>();
		List<String> mov_link = new ArrayList<String>();
		
		for(int i = 0; i < movie_info.size(); i++) {
			JsonObject mov_info = (JsonObject)movie_info.get(i);
			JsonObject jobj = (JsonObject)mov_info.get("movieInfo");
			
			String title = jobj.get("movieNm").toString().replaceAll("\\\"", "");
			String openDt = jobj.get("openDt").toString().replaceAll("\\\"", "");
			String prdtYear = jobj.get("prdtYear").toString().replaceAll("\\\"", "");
			
			title = URLEncoder.encode(title, "utf-8"); 
			
			if(openDt.equals("")) {
				openDt = prdtYear;
			}else {
				openDt = openDt.substring(0, 4);
			}
			
			String txt = title + "&yearfrom=" + prdtYear + "&yearto=" + openDt + "&display=1";
			
			StringBuffer br = OpenAPI(txt);
			JsonParser jsonParser = new JsonParser();
			JsonObject nav_mov = (JsonObject)jsonParser.parse(br.toString());
			JsonArray mov_arr = (JsonArray)nav_mov.get("items");
			
			try { 
				JsonObject mov = (JsonObject)mov_arr.get(0);
				mov_poster.add(mov.get("image").toString().replaceAll("\\\"", "").trim());
				mov_link.add(mov.get("link").toString().replaceAll("\\\"", "").trim());
				
			}catch (Exception e) {
				mov_poster.add("${pageContext.request.contextPath }/resources/img/nophoto.png");
				mov_link.add("#");
			}
		}
		
		map.put("mov_poster", mov_poster);
		map.put("mov_link", mov_link); 
		
		return map;
	}
	
	public MovieDTO getPoster(MovieDTO dto) throws IOException {
		
		String title = dto.getMov_title();
		String openDt = dto.getMov_opendt();
		String prdtYear = dto.getMov_prdtyear();
		
		title = URLEncoder.encode(title, "utf-8"); 
		
		if(openDt.equals("")) {
			openDt = prdtYear;
		}else {
			openDt = openDt.substring(0, 4);
		}
		
		String txt = title + "&yearfrom=" + prdtYear + "&yearto=" + openDt + "&display=1";
		
		StringBuffer br = OpenAPI(txt);
		JsonParser jsonParser = new JsonParser();
		JsonObject nav_mov = (JsonObject)jsonParser.parse(br.toString());
		JsonArray mov_arr = (JsonArray)nav_mov.get("items");
		
		try { 
			JsonObject mov = (JsonObject)mov_arr.get(0);
			dto.setMov_poster(mov.get("image").toString().replaceAll("\\\"", "").trim());
			dto.setMov_link(mov.get("link").toString().replaceAll("\\\"", "").trim());
		}catch (Exception e) {
			dto.setMov_poster(null);
			dto.setMov_link(null);
		}
		
		return dto;
	}

}