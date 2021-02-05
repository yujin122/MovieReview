package com.movie.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movie.model.GenreDAO;
import com.movie.model.GenreDTO;
import com.movie.model.MovieDAO;
import com.movie.model.MovieDTO;
import com.movie.model.MovieGenreDAO;
import com.movie.model.ReviewDAO;
import com.movie.model.ReviewDTO;
import com.movie.model.ReviewInfoDTO;
import com.movie.model.ReviewLikeDAO;
import com.movie.model.ReviewLikeDTO;

@Service
public class ReviewServiceImpl implements ReviewService{
	@Autowired private MovieDAO mov_dao;
	@Autowired private ReviewDAO rev_dao;
	@Autowired private GenreDAO gen_dao;
	@Autowired private MovieGenreDAO mg_dao;
	@Autowired private ReviewLikeDAO rl_dao;
	
	@Override
	public int movieCheck(String mov_code) {
		return mov_dao.checkMovie(mov_code);
	}
	
	@Override
	public List<String> reviewRating(String mov_code) {
		return rev_dao.reviewRating(mov_code);
	}
	
	@Override
	public MovieDTO movieCont(String mov_code) {
		return mov_dao.getMovie(mov_code);
	}
	
	@Override
	public List<GenreDTO> genreList(String mov_code) {
		return gen_dao.getGenre(mov_code);
	}
	
	@Transactional
	@Override
	public Map<String , Integer> movieInsert(MovieDTO dto, String genre) {
		int mov_res = mov_dao.insertMovie(dto);
		int mg_res = 0;
		String[] genre_list = genre.split("\\|");
		
		for(int i = 0; i < genre_list.length; i++) {	// | 기준으로 자르고 for 돌리기
			String checkGenre = genre_list[i];
			// 장르 번호 가져오기
			int genre_num = gen_dao.checkGenre(checkGenre);
			// 장르 넣기
			mg_res += mg_dao.insertMovieGenre(genre_num, dto.getMov_code());
		}
		
		Map<String , Integer> map = new HashMap<String, Integer>();
		map.put("movie", mov_res);
		map.put("genre", mg_res);
		
		return map;
	}
	
	@Override
	public int reviewInsert(ReviewDTO dto) {
		return rev_dao.insertReview(dto);
	}
	
	@Override
	public int reviewAllCnt() {
		return rev_dao.reviewCnt();
	}
	
	@Override
	public List<ReviewDTO> reviewAllList(Pagination pagination) {
		return rev_dao.reviewAllList(pagination);
	}
	
	@Override
	public Map<String, Object> reviewPost(int review_num) {
		rev_dao.reviewHit(review_num);
		
		ReviewInfoDTO dto = rev_dao.reviewCont(review_num);
		List<GenreDTO> g_list = gen_dao.getGenre(dto.getMov_code());
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("review", dto);
		map.put("genre", g_list);
		
		return map;
	}
	
	@Override
	public int reviewLikeCheck(ReviewLikeDTO dto) {
		return rl_dao.checkReviewLike(dto);
	}
	
	@Override
	public int reviewLikeCnt(int review_num) {
		return rl_dao.countReviewLike(review_num);
	}
	
	@Override
	public int reviewLike(ReviewLikeDTO dto) {
		
		int cnt = rl_dao.checkReviewLike(dto);
		
		int res = 0;
		
		if(cnt > 0) {
			res = rl_dao.deleteReviewLike(dto);
		}else {
			res = rl_dao.insertReviewLike(dto);
		}
		
		return res;
	}
	
	@Override
	public Map<String, Object> reviewUpdateForm(int review_num) {
		ReviewInfoDTO dto = rev_dao.reviewCont(review_num);
		List<GenreDTO> g_list = gen_dao.getGenre(dto.getMov_code());
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("review", dto);
		map.put("genre", g_list);
		
		return map;
	}
	
	@Override
	public int reviewUpdate(ReviewDTO dto) {
		return rev_dao.updateReview(dto);
	}
	
	@Override
	public int reviewDelete(int review_num) {
		return rev_dao.deleteReview(review_num);
	}
	
	@Override
	public int reviewSearchCnt(String label, String search) {
		return rev_dao.reviewSearchCnt(label, search);
	}
	
	@Override
	public List<ReviewDTO> reviewSearch(String label, String search, Pagination pagination) {
		return rev_dao.reviewSearchList(label, search, pagination);
	}
	
	@Override
	public int reviewCheck(String mov_code, String mem_id) {
		return rev_dao.checkReview(mov_code, mem_id);
	}
}
