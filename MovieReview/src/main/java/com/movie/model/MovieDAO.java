package com.movie.model;

import java.util.List;

public interface MovieDAO {

	public int checkMovie(String mov_num);
	public MovieDTO getMovie(String mov_num);
	public int insertMovie(MovieDTO dto);
	public List<MovieDTO> getRecommendMovie(List<Integer> list);
}
