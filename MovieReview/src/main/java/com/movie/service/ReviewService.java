package com.movie.service;

import java.util.List;
import java.util.Map;

import com.movie.model.GenreDTO;
import com.movie.model.MovieDTO;
import com.movie.model.ReviewDTO;
import com.movie.model.ReviewLikeDTO;

public interface ReviewService {
	public int movieCheck(String mov_code);
	public List<String> reviewRating(String mov_code);
	public MovieDTO movieCont(String mov_code);
	public List<GenreDTO> genreList(String mov_code);
	public Map<String , Integer> movieInsert(MovieDTO dto, String genre);
	public int reviewInsert(ReviewDTO dto);
	public int reviewAllCnt();
	public List<ReviewDTO> reviewAllList(Pagination pagination);
	public Map<String, Object> reviewPost(int review_num);
	public int reviewLikeCheck(ReviewLikeDTO dto);
	public int reviewLikeCnt(int review_num);
	public int reviewLike(ReviewLikeDTO dto);
	public Map<String, Object> reviewUpdateForm(int review_num);
	public int reviewUpdate(ReviewDTO dto);
	public int reviewDelete(int review_num);
	public int reviewSearchCnt(String label, String search);
	public List<ReviewDTO> reviewSearch(String label, String search, Pagination pagination);
	public int reviewCheck(String mov_code, String mem_id);
	public float expectRating(String[] genre_list, String mem_id);
}
