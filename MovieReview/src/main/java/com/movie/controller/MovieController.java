package com.movie.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.movie.model.LikeDAO;
import com.movie.model.MovieAPI;
import com.movie.model.MovieDTO;
import com.movie.model.NaverAPI;
import com.movie.service.MovieService;
import com.movie.service.Pagination;

@Controller
public class MovieController {
	
	@Autowired MovieService service;
	
	@Autowired LikeDAO like_dao;
	
	@RequestMapping("/movie_main.do")
	public String movie_main() {
		return "movie/movie_search";
	}
	
	@ResponseBody
	@RequestMapping("/mov_list.do")
	public Map<String, Object> movie_list(@RequestParam int page, @RequestParam String txt, HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 영화 번호
		Map<String, Object> code_map =  movie_code(txt, page);
		
		int totCnt = Integer.parseInt((String)code_map.get("mov_cnt"));
		List<String> code_list = (List<String>)code_map.get("mov_code");
		// 영화 상세정보
		List<Map<String, Object>> mov_info = movie_info(code_list);
		code_map.put("mov_info", mov_info);
		// 영화 포스터 링크 정보
		List<Map<String, String>> mov_poster = movie_poster(mov_info);
		code_map.put("mov_poster", mov_poster);
		// 영화 좋아요 정보
		List<String> mov_like = movie_like(code_list, request);
		code_map.put("mov_like", mov_like);
		// 페이징
		Pagination pagination = new Pagination(); 
		pagination.pageInfo(page, 10, totCnt); 
		code_map.put("pagination", pagination);
		
		return code_map;
	}
	
	// 영화 번호 가져오기
	public Map<String, Object> movie_code(String text, int page) throws UnsupportedEncodingException {
		
		text = "movieNm=" + text;
		
		MovieAPI movieAPI = new MovieAPI();
		
		Map<String, Object> map = movieAPI.getMovieCd(text, page);
		
		return map;
	}
	
	// 영화 상세 정보
	public List<Map<String, Object>> movie_info(List<String> code_list) {
		List<Map<String, Object>> info_list = new ArrayList<Map<String,Object>>();
		
		MovieAPI movieAPI = new MovieAPI();
		
		for(int i = 0; i  < code_list.size(); i++) {
			float rating = 0;
			String code = code_list.get(i);
			
			List<String> rating_list = service.reviewRating(code);
			for(int j = 0; j < rating_list.size(); j++) {
				rating += Float.parseFloat(rating_list.get(j));
			}
			if(rating != 0) {
				rating = rating / rating_list.size();
			}
			
			info_list.add(movieAPI.getMovieCont(code, rating));
		}
		return info_list;
	}
	
	// 영화 포스터, 링크
	public List<Map<String, String>> movie_poster(List<Map<String, Object>> mov_info) throws IOException {
		List<Map<String, String>> poster_list = new ArrayList<Map<String,String>>();
		NaverAPI naverAPI = new NaverAPI();
		
		for(int i = 0; i < mov_info.size(); i++) {
			Map<String, Object> map = mov_info.get(i);
			MovieDTO dto = (MovieDTO)map.get("mov_info");
			
			Map<String, String> poster_map = naverAPI.getPoster(dto.getMov_title(), dto.getMov_prdtyear(), dto.getMov_opendt());
			poster_list.add(poster_map);
		}
		
		return poster_list;
	}
	
	// 영화 좋아요 가져오기
	public List<String> movie_like(List<String> code_list, HttpServletRequest request) {
		List<String> like_list = new ArrayList<String>();
		HttpSession session = request.getSession();
		
		if(session.getAttribute("session_mem_id") != null) {
			String mem_id = session.getAttribute("session_mem_id").toString();
			
			for(int i = 0; i < code_list.size(); i++) {
				String mov_code = code_list.get(i);
				
				int res = service.likeCheck(mov_code, mem_id);
				
				if(res > 0) {
					like_list.add("heart.png");
				}else {
					like_list.add("heart_empty.png");
				}
			}
		}else {
			like_list.add("heart_empty.png");
		}
		
		return like_list;
	}
	
	@ResponseBody
	@RequestMapping("/like.do")
	public String like(@RequestParam String mov_num, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		HttpSession session = request.getSession();
		String mem_id = session.getAttribute("session_mem_id").toString();

		int res = service.likeCheck(mov_num, mem_id);
		
		if(res > 0) {
			service.likeDelete(mov_num, mem_id);
			return "heart_empty.png";
		}else {
			// 영화 저장
			int check = service.movieCheck(mov_num);
			float rating = 0;
			
			List<String> rating_list = service.reviewRating(mov_num);
			for(int j = 0; j < rating_list.size(); j++) {
				rating += Float.parseFloat(rating_list.get(j));
			}
			if(rating != 0) {
				rating = rating / rating_list.size();
			}
			
			if(check == 0) {
				
				MovieAPI mov_api = new MovieAPI();
				
				Map<String, Object> map = mov_api.getMovieCont(mov_num,rating);
				
				MovieDTO dto = (MovieDTO)map.get("mov_info");
				String genre = (String)map.get("genre");
				
				NaverAPI n_api = new NaverAPI();
				MovieDTO mov_dto = n_api.getPoster(dto);
				
				service.movieInsert(mov_dto, genre);
			}
			// 좋아요 추가
			service.likeInsert(mov_num, mem_id);
			
			return "heart.png";
		}
	}
}
