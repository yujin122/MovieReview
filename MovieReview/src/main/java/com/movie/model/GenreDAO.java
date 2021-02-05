package com.movie.model;

import java.util.List;

public interface GenreDAO {
	public int checkGenre(String genre);
	public List<GenreDTO> getGenre(String mov_code);
}
