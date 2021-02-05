package com.movie.model;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class GenreDAOImpl implements GenreDAO {
	
	@Autowired private SqlSessionTemplate sqlSession;
	
	@Override
	public int checkGenre(String genre) {
		return sqlSession.selectOne("checkGenre", genre);
	}
	
	@Override
	public List<GenreDTO> getGenre(String mov_code) {
		return sqlSession.selectList("getGenre", mov_code);
	}
}
