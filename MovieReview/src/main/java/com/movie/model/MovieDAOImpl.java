package com.movie.model;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MovieDAOImpl implements MovieDAO {

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Override
	public int checkMovie(String mov_num) {
		return sqlSession.selectOne("checkMovie", mov_num);
	}
	
	@Override
	public MovieDTO getMovie(String mov_num) {
		return sqlSession.selectOne("getMovie", mov_num);
	}
	
	@Override
	public int insertMovie(MovieDTO dto) {
		return sqlSession.insert("insertMovie", dto);
	}
	
	@Override
	public List<MovieDTO> getRecommendMovie(List<Integer> list) {
		return sqlSession.selectList("getRecommendList", list);
	}
}
