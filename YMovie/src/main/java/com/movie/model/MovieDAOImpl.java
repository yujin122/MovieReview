package com.movie.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class MovieDAOImpl implements MovieDAO {

	@Autowired
	JdbcTemplate temp;
	String sql;
	
	@Override
	public int checkMovie(String mov_num) {
		sql = "select count(*) from mov_movie_info where mov_code = ?";
		
		return temp.queryForInt(sql, mov_num);
	}
	
	@Override
	public MovieDTO getMovie(String mov_num) {
		sql = "select * from mov_movie_info where mov_code = ?";
		
		return temp.queryForObject(sql, new RowMapper<MovieDTO>() {
			@Override
			public MovieDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				MovieDTO dto = new MovieDTO();
				
				dto.setMov_code(rs.getString(1));
				dto.setMov_title(rs.getString(2));
				dto.setMov_director(rs.getString(3));
				dto.setMov_opendt(rs.getString(4));
				dto.setMov_actors(rs.getString(5));
				dto.setMov_nations(rs.getString(6));
				dto.setMov_showtm(rs.getString(7));
				dto.setMov_actors(rs.getString(8));
				dto.setMov_prdtyear(rs.getString(9));
				
				return dto;
			}
		});
	}

}
