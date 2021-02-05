package com.movie.model;

import java.util.List;

import com.movie.service.Pagination;

public interface ReviewDAO {
	public int insertReview(ReviewDTO dto);
	public List<ReviewDTO> reviewAllList(Pagination pagination);
	public int reviewCnt();
	public void reviewHit(int review_num);
	public ReviewInfoDTO reviewCont(int review_num);
	public int updateReview(ReviewDTO dto);
	public int deleteReview(int review_num);
	public int reviewSearchCnt(String label, String search);
	public List<ReviewDTO> reviewSearchList(String label, String search, Pagination pagination);
	public int myReviewCnt(String mem_id);
	public List<ReviewDTO> myReviewList(String mem_id, Pagination pagination);
	public List<String> reviewRating(String mov_code);
	public int checkReview(String mov_code, String mem_id);
	public int checkReview(String mov_code);
	public List<ChartDTO> myReviewChart(String mem_id);
	public List<Integer> myReviewBest(String mem_id);
	public float myReviewRatingAvg(String mov_code);
}
