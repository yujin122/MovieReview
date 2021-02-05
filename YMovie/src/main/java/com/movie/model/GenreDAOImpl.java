package com.movie.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class GenreDAOImpl implements GenreDAO {
	
	@Autowired private JdbcTemplate temp;
	String sql;
	
	@Override
	public int checkGenre(String genre) {
		sql = "select count(*) from mov_genres_info where mov_genre_name = ?";
		
		return temp.queryForInt(sql, genre);
	}

	@Override
	public int insertGenre(String genre) {
		sql = "insert into mov_genres_info(mov_genre_name) values(?)";
		
		return temp.update(sql, genre);
	}

}
