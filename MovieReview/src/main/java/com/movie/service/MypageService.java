package com.movie.service;

import java.util.List;
import java.util.Map;

import com.movie.model.ChartDTO;
import com.movie.model.MovieDTO;

public interface MypageService {
	public Map<String, Object> myInfo(String mem_id);
	public List<MovieDTO> movieRecommend(String mem_id);
	public List<ChartDTO> movieReviewChart(String mem_id);
}
