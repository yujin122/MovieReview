package com.movie.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movie.model.GenreDAO;
import com.movie.model.LikeDAO;
import com.movie.model.MovieDAO;
import com.movie.model.MovieDTO;
import com.movie.model.MovieGenreDAO;
import com.movie.model.ReviewDAO;

@Service
public class MovieServiceImpl implements MovieService{
	
	@Autowired private ReviewDAO rev_dao;
	@Autowired private LikeDAO like_dao;
	@Autowired private MovieDAO mov_dao;
	@Autowired private GenreDAO gen_dao;
	@Autowired private MovieGenreDAO mg_dao;
	
	@Override
	public List<String> reviewRating(String mov_code) {
		return rev_dao.reviewRating(mov_code);
	}

	@Override
	public int likeCheck(String mov_code, String mem_id) {
		return like_dao.like_check(mov_code, mem_id);
	}
	
	@Override
	public void likeDelete(String mov_code, String mem_id) {
		like_dao.like_del(mov_code, mem_id);
	}
	
	@Override
	public int movieCheck(String mov_code) {
		return mov_dao.checkMovie(mov_code);
	}
	
	@Transactional
	@Override
	public void movieInsert(MovieDTO dto, String genre) {
		mov_dao.insertMovie(dto);
		
		String[] genre_list = genre.split("\\|");
		
		for(int i = 0; i < genre_list.length; i++) {	// | 기준으로 자르고 for 돌리기
			String checkGenre = genre_list[i];
			// 장르 번호 가져오기
			int genre_num = gen_dao.checkGenre(checkGenre);
			// 장르 넣기
			mg_dao.insertMovieGenre(genre_num, dto.getMov_code());
		}
	}
	
	@Override
	public void likeInsert(String mov_code, String mem_id) {
		like_dao.like_add(mov_code, mem_id);
	}
}
