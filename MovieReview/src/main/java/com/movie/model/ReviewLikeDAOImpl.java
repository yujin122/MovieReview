package com.movie.model;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ReviewLikeDAOImpl implements ReviewLikeDAO{
	
	@Autowired SqlSessionTemplate sqlSession;
	
	@Override
	public int countReviewLike(int review_num) {
		return sqlSession.selectOne("countReviewLike", review_num);
	}
	
	@Override
	public int checkReviewLike(ReviewLikeDTO dto) {
		return sqlSession.selectOne("checkReviewLike", dto);
	}
	
	@Override
	public int insertReviewLike(ReviewLikeDTO dto) {
		return sqlSession.insert("insertReviewLike", dto);
	}
	
	@Override
	public int deleteReviewLike(ReviewLikeDTO dto) {
		return sqlSession.delete("deleteReviewLike", dto);
	}
	
	@Override
	public int deleteReviewLike(int review_num) {
		return sqlSession.delete("deleteReviewLikeNum", review_num);
	}
}
