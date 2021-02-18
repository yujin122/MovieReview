package com.movie.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.movie.service.Pagination;

@Repository
public class ReviewDAOImpl implements ReviewDAO{
	
	@Autowired SqlSessionTemplate sqlSession;
	
	@Override
	public int insertReview(ReviewDTO dto) {
		return sqlSession.insert("insertReview", dto);
	}
	
	@Override
	public List<ReviewDTO> reviewAllList(Pagination pagination) {
		return sqlSession.selectList("reviewAllList", pagination);
	}
	
	@Override
	public int reviewCnt() {
		return sqlSession.selectOne("reviewCnt");
	}
	
	@Override
	public ReviewInfoDTO reviewCont(int review_num) {
		return sqlSession.selectOne("reviewCont", review_num);
	}
	
	@Override
	public void reviewHit(int review_num) {
		sqlSession.update("reviewHit", review_num);
	}
	
	@Override
	public int updateReview(ReviewDTO dto) {
		return sqlSession.update("updateReview", dto);
	}
	
	@Override
	public int deleteReview(int review_num) {
		return sqlSession.delete("deleteReview", review_num);
	}
	
	@Override
	public int reviewSearchCnt(String label, String search) {
		
		search = "%" + search + "%";
		
		if(label.equals("title")) {
			return sqlSession.selectOne("reviewTitleCnt", search);
		}else if(label.equals("cont")) {
			return sqlSession.selectOne("reviewContCnt", search);
		}else {
			return sqlSession.selectOne("reviewWriterCnt", search);
		}
	}
	
	@Override
	public List<ReviewDTO> reviewSearchList(String label, String search, Pagination pagination) {
		search = "%" + search + "%";
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("search", search);
		map.put("startNo", pagination.getStartNo());
		map.put("rowsize", pagination.getRowsize());
		
		if(label.equals("title")) {
			return sqlSession.selectList("reviewTitleList", map);
		}else if(label.equals("cont")) {
			return sqlSession.selectList("reviewContList", map);
		}else {
			return sqlSession.selectList("reviewWriterList", map);
		}
	}
	
	@Override
	public int myReviewCnt(String mem_id) {
		return sqlSession.selectOne("myReviewCnt", mem_id);
	}
	
	@Override
	public List<ReviewDTO> myReviewList(String mem_id, Pagination pagination) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mem_id", mem_id);
		map.put("startNo", pagination.getStartNo());
		map.put("rowsize", pagination.getRowsize());
		
		return sqlSession.selectList("myReviewList", map);
	}
	
	@Override
	public List<String> reviewRating(String mov_code) {
		return sqlSession.selectList("reviewRating", mov_code);
	}
	
	@Override
	public int checkReview(String mov_code, String mem_id) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("mov_code", mov_code);
		map.put("mem_id", mem_id);
		
		return sqlSession.selectOne("checkReview", map);
	}

	@Override
	public List<ChartDTO> myReviewChart(String mem_id) {
		return sqlSession.selectList("myReviewChart", mem_id);
	}
	
	@Override
	public List<Integer> myReviewBest(String mem_id) {
		return sqlSession.selectList("myReviewBest", mem_id);
	}
	
	@Override
	public float myReviewRatingAvg(String mov_code) {
		return sqlSession.selectOne("myReviewRatingAvg", mov_code);
	}
	
	@Override
	public int checkReview(String mov_code) {
		return sqlSession.selectOne("checkMyReview", mov_code);
	}
	
	@Override
	public List<ExpectRatingDTO> expectRating(List<Integer> genreNumList, String mem_id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("genreNumList", genreNumList);
		map.put("mem_id", mem_id);
		
		return sqlSession.selectList("expectRating", map);
	}
}
