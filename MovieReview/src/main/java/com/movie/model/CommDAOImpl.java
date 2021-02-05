package com.movie.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.movie.service.Pagination;

@Repository
public class CommDAOImpl implements CommDAO{
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	String sql;
	
	@Override
	public List<CommDTO> getCommList(int bnum, Pagination pagination) {
		Map<String,	Object> map = new HashMap<String, Object>();
		map.put("bnum", bnum);
		map.put("startNo", pagination.getStartNo());
		map.put("rowsize", pagination.getRowsize());
		
		return sqlSession.selectList("getCommList", map);
	}
	
	@Override
	public int insertComm(CommDTO dto) {
		return sqlSession.insert("insertComm", dto);
	}
	
	@Override
	public int deleteComm(int comm_num) {
		return sqlSession.delete("deleteComm", comm_num);
	}
	
	@Override
	public int deleteBoardComm(int bnum) {
		return sqlSession.delete("deleteBoardComm", bnum);
	}
	
	@Override
	public int updateComm(CommDTO dto) {
		return sqlSession.update("updateComm", dto);
	}
	
	@Override
	public CommDTO getCommCont(int comm_num) {
		return sqlSession.selectOne("getCommCont", comm_num);
	}
	
	@Override
	public int getMaxStep(int comm_group) {
		return sqlSession.selectOne("getMaxStep", comm_group);
	}
	
	@Override
	public int insertCommReply(final CommDTO dto) {
		return sqlSession.insert("insertCommReply", dto);
	}
	
	@Override
	public int getCommCnt(int bnum) {
		return sqlSession.selectOne("getCommCnt", bnum);
	}
	
	@Override
	public int getMyCommCnt(String mem_id) {
		return sqlSession.selectOne("getMyCommCnt", mem_id);
	}
	
	@Override
	public List<CommDTO> getMyCommList(String mem_id, Pagination pagination) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mem_id", mem_id);
		map.put("startNum", pagination.getStartNo());
		map.put("rowsize", pagination.getRowsize());
		
		return sqlSession.selectList("getMyCommList", map);
	}
	
	@Override
	public int getMaxNum(int comm_board) {
		return sqlSession.selectOne("getMaxNum", comm_board);
	}
}
