package com.movie.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.movie.service.Pagination;

@Repository
public class LikeDAOImpl implements LikeDAO{
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	String sql;
	
	@Override
	public int like_check(String mov_num, String mem_id) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("mov_num", mov_num);
		map.put("mem_id", mem_id);
		
		return sqlSession.selectOne("likeCheck", map);
	}
	
	@Override
	public int like_add(final String mov_num, final String mem_id) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("mov_num", mov_num);
		map.put("mem_id", mem_id);
		
		return sqlSession.insert("likeAdd", map);
	}
	
	@Override
	public int like_del(final String mov_num, final String mem_id) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("mov_num", mov_num);
		map.put("mem_id", mem_id);
		
		return sqlSession.delete("likeDelete", map);
	}
	
	@Override
	public List<MovieDTO> like_list(String mem_id) {
		return sqlSession.selectList("likeList", mem_id);
	}
	
	@Override
	public int getLikeCnt(String mem_id) {
		return sqlSession.selectOne("getLikeCnt", mem_id);
	}
	
	@Override
	public List<MovieDTO> likeAllList(String mem_id, Pagination pagination) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mem_id", mem_id);
		map.put("startNo", pagination.getStartNo());
		map.put("rowsize", pagination.getRowsize());
		
		return sqlSession.selectList("likeAllList", map);
	}
}
