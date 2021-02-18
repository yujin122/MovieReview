package com.movie.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MovieGenreDAOImpl implements MovieGenreDAO{
	
	@Autowired private SqlSessionTemplate sqlSession;
	
	@Override
	public int insertMovieGenre(int genre_num, String mov_code) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("genre_num", genre_num);
		map.put("mov_code", mov_code);
		
		return sqlSession.insert("insertMovieGenre", map);
	}
	
}
