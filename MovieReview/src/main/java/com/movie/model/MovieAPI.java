package com.movie.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MovieAPI {
	
	/* @Autowired private GenreDAO genre_dao; */
	
	public StringBuffer openAPI(String apiURL, String search_txt) {
		HttpURLConnection con = null;
		URL url = null;
		
		String key = "a179f4fd35061d9d5995897d3fec67f3";
		
		String rURL = apiURL + "?key=" + key + "&" + search_txt;
		
		StringBuffer result = new StringBuffer();
		
		try {
			url = new URL(rURL);
			
			con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("GET");
			
			int responseCode = con.getResponseCode();
			
			BufferedReader br;
			
			if(responseCode == 200) {
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			}else {
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			
			String inputLine;
			
			while((inputLine = br.readLine()) != null) {
				result.append(inputLine + "\n");
			}
			
			br.close(); 
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	// 영화 번호 가져오기
	public Map<String, Object> getMovieCd(String txt, int page) throws UnsupportedEncodingException{
		List<String> list = new ArrayList<String>();

		String apiURL = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.json";
		
		txt += "&curPage="+page; 
		
		StringBuffer res = openAPI(apiURL,txt);
		
		JsonParser jsonParser = new JsonParser();
		JsonObject jobj = (JsonObject)jsonParser.parse(res.toString());
		JsonObject mov_res = (JsonObject)jobj.get("movieListResult");
		
		String mov_cnt = mov_res.get("totCnt").toString(); 
		
		JsonArray mov_list = (JsonArray)mov_res.get("movieList");
		
		for(int i = 0; i< mov_list.size(); i++) {
			
			JsonObject movieCdList = (JsonObject)mov_list.get(i);
			
			list.add(movieCdList.get("movieCd").toString().replaceAll("\\\"", ""));
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mov_code", list);
		map.put("mov_cnt", mov_cnt);
		
		return map;
	}
	
	// 영화 리스트 가져오기
	/*
	 * public JsonArray getMovieInfo(List<String> mCdList){ JsonArray jarr = new
	 * JsonArray();
	 * 
	 * String apiURL =
	 * "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.json";
	 * String txt;
	 * 
	 * for(int i = 0; i < mCdList.size(); i++) { txt = "movieCd="+mCdList.get(i);
	 * 
	 * StringBuffer br = openAPI(apiURL, txt); JsonParser parser = new JsonParser();
	 * JsonObject res = (JsonObject)parser.parse(br.toString()); JsonObject mov_list
	 * = (JsonObject)res.get("movieInfoResult");
	 * 
	 * jarr.add(mov_list); }
	 * 
	 * return jarr; }
	 */
	
	public Map<String, Object> getMovieCont(String mov_num, float rating) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		String apiURL = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.json";
		String txt = "movieCd="+mov_num;
		
		StringBuffer br = openAPI(apiURL, txt);
		
		JsonParser parser = new JsonParser();
		JsonObject parser_mov = (JsonObject)parser.parse(br.toString());
		JsonObject mov_res = (JsonObject)parser_mov.get("movieInfoResult");
		JsonObject mov_info = (JsonObject)mov_res.get("movieInfo");
		
		MovieDTO dto = new MovieDTO();
		dto.setMov_code(mov_info.get("movieCd").toString().replaceAll("\\\"", ""));
		dto.setMov_title(mov_info.get("movieNm").toString().replaceAll("\\\"", ""));
		dto.setMov_showtm(mov_info.get("showTm").toString().replaceAll("\\\"", ""));
		dto.setMov_opendt(mov_info.get("openDt").toString().replaceAll("\\\"", ""));
		dto.setMov_prdtyear(mov_info.get("prdtYear").toString().replaceAll("\\\"", ""));
		if(rating != 0) {
			dto.setMov_avgrating(String.valueOf(rating));
		}
		
		JsonArray nations = (JsonArray)mov_info.get("nations");
		String nationList = "";
		
		// 국가
		for(int j = 0; j < nations.size(); j++) {
			JsonObject nation = (JsonObject)nations.get(j);
			
			nationList += nation.get("nationNm").toString().replaceAll("\\\"", "") + " ";
		}
		dto.setMov_nations(nationList);
		
		// 장르
		JsonArray genres = (JsonArray)mov_info.get("genres");
		String genreList = "";
		
		for(int j = 0; j < genres.size(); j++) {
			JsonObject genre = (JsonObject)genres.get(j);
			
			genreList += genre.get("genreNm").toString().replaceAll("\\\"", "") + "|";
		}
		
		map.put("genre", genreList);
		
		// 감독
		JsonArray directors = (JsonArray)mov_info.get("directors");
		String directorList = "";
		
		for(int j = 0; j < directors.size(); j++) {
			JsonObject director = (JsonObject)directors.get(j);
			
			directorList += director.get("peopleNm").toString().replaceAll("\\\"", "") + " ";
		}
		dto.setMov_director(directorList);
		
		// 배우
		JsonArray actors = (JsonArray)mov_info.get("actors");
		String actorList = "";
		
		if(actors.size() > 5) {
			for(int j = 0; j < 5; j++) {
				JsonObject actor = (JsonObject)actors.get(j);
				
				actorList += actor.get("peopleNm").toString().replaceAll("\\\"", "") + " ";				
			}
			
			actorList += "외 " + (actors.size()-5) + "명";
		}else {
			for(int j = 0; j < actors.size(); j++) {
				JsonObject actor = (JsonObject)actors.get(j);
				
				actorList += actor.get("peopleNm").toString().replaceAll("\\\"", "") + " ";				
			}
		}
		dto.setMov_actors(actorList);
		
		map.put("mov_info", dto);
		
		return map;
	}
}
