package com.movie.model;

public interface ReviewLikeDAO {
	public int countReviewLike(int review_num);
	public int checkReviewLike(ReviewLikeDTO dto);
	public int insertReviewLike(ReviewLikeDTO dto);
	public int deleteReviewLike(ReviewLikeDTO dto);
	public int deleteReviewLike(int review_num);
}
