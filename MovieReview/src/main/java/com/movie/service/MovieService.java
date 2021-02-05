package com.movie.service;

import java.util.List;

import com.movie.model.MovieDTO;

public interface MovieService {
	public List<String> reviewRating(String mov_code);
	public int likeCheck(String mov_code, String mem_id);
	public void likeDelete(String mov_code, String mem_id);
	public int movieCheck(String mov_code);
	public void movieInsert(MovieDTO dto, String genre);
	public void likeInsert(String mov_code, String mem_id);
}
