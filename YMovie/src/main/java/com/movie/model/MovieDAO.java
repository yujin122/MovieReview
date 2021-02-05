package com.movie.model;

public interface MovieDAO {

	public int checkMovie(String mov_num);
	public MovieDTO getMovie(String mov_num);
}
