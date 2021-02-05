package com.movie.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.movie.service.Pagination;

@Repository
public class BoardDAOImpl implements BoardDAO{
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Override
	public List<BoardDTO> getBoardList(Pagination pagination) {
		return sqlSession.selectList("getBoardList", pagination);
	}
	
	@Override
	public BoardDTO getBoardCont(int bnum) {
		return sqlSession.selectOne("getBoardCont", bnum);
	}
	
	@Override
	public int setHit(int bnum) {
		return sqlSession.update("setHit", bnum);
	}
	
	@Override
	public void setCommCount(int bnum) {
		sqlSession.update("setCommount", bnum);
	}
	
	@Override
	public void setdelCommCnt(int bnum, int cnt) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("bnum", bnum);
		map.put("cnt", cnt);
		
		sqlSession.update("setdelCommCnt", map);
	}
	
	@Override
	public int getBoardCnt() {
		return sqlSession.selectOne("getBoardCnt");
	}
	
	@Override
	public int insertBoard(final BoardDTO dto) {
		return sqlSession.insert("insertBoard", dto);
	}
	
	@Override
	public int updateBoard(final BoardDTO dto) {
		return sqlSession.update("updateBoard", dto);
	}
	
	@Override
	public int deleteBoard(int bnum) {
		return sqlSession.delete("deleteBoard", bnum);
	}
	
	@Override
	public int insertBoardReply(BoardDTO dto) {
		return sqlSession.insert("insertBoardReply", dto);
	}
	
	@Override
	public void updateReply(final BoardDTO dto) {
		sqlSession.update("updateReply", dto);
	}
	
	@Override
	public List<BoardDTO> searchBoardList(String label, String keyword, Pagination pagination) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("label", label);
		map.put("keyword", "%"+keyword+"%");
		map.put("startNum", pagination.getStartNo());
		map.put("rowsize", pagination.getRowsize());
		
		if(label.equals("category")) {
			return sqlSession.selectList("category_board_search", map);
		}else if(label.equals("cont")) {
			return sqlSession.selectList("cont_board_search", map);
		}else {
			return sqlSession.selectList("writer_board_search", map);
		}
	}
	
	@Override
	public int searchBoardCnt(String label, String keyword) {
		keyword = "%"+keyword+"%";
		
		if(label.equals("category")) {
			return sqlSession.selectOne("category_searchBoardCnt",keyword);
		}else if(label.equals("cont")) {
			return sqlSession.selectOne("cont_searchBoardCnt",keyword);
		}else {
			return sqlSession.selectOne("writer_searchBoardCnt",keyword);
		}
	}
	
	@Override
	public int getMyBoardCnt(String mem_id) {
		return sqlSession.selectOne("getMyBoardCnt", mem_id);
	}
	
	@Override
	public List<BoardDTO> getMyBoardList(String mem_id, Pagination pagination) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mem_id", mem_id);
		map.put("startnum", pagination.getStartNo());
		map.put("rowsize", pagination.getRowsize());
		
		return sqlSession.selectList("getMyBoardList", map);
	}
	
	@Override
	public int getBoardMaxNum() {
		return sqlSession.selectOne("getBoardMaxNum");
	}
}
