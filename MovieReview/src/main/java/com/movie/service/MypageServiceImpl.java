package com.movie.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.movie.model.BoardDAO;
import com.movie.model.ChartDTO;
import com.movie.model.CommDAO;
import com.movie.model.GenreDAO;
import com.movie.model.LikeDAO;
import com.movie.model.MemberDAO;
import com.movie.model.MemberDTO;
import com.movie.model.MovieDAO;
import com.movie.model.MovieDTO;
import com.movie.model.ReviewDAO;
import com.movie.model.ReviewLikeDAO;

@Repository
public class MypageServiceImpl implements MypageService{
	@Autowired private BoardDAO board_dao;
	@Autowired private CommDAO comm_dao;
	@Autowired private ReviewDAO rev_dao;
	@Autowired private ReviewLikeDAO rl_dao;
	@Autowired private LikeDAO like_dao;
	@Autowired private MovieDAO mov_dao;
	@Autowired private MemberDAO mem_dao;
	@Autowired private GenreDAO gen_dao;
	
	@Override
	public Map<String, Object> myInfo(String mem_id) {
		MemberDTO mem_dto = mem_dao.getMemberCont(mem_id);
		int boardCnt = board_dao.getMyBoardCnt(mem_id);
		int reviewCnt = rev_dao.myReviewCnt(mem_id);
		int likeCnt = like_dao.getLikeCnt(mem_id);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("member", mem_dto);
		map.put("review", reviewCnt);
		map.put("board", boardCnt);
		map.put("like", likeCnt);
		
		return map;
	}
	
	@Override
	public List<MovieDTO> movieRecommend(String mem_id) {
		// 리뷰를 제일 많이 쓴 장르 / 평점이 제일 높은 장르 순으로 6개 가져오기
		List<Integer> genreList = rev_dao.myReviewBest(mem_id);
		// 장르를 포함한 영화 랜덤으로 6개 가져오기
		return mov_dao.getRecommendMovie(genreList);
	}
	
	@Override
	public List<ChartDTO> movieReviewChart(String mem_id) {
		List<ChartDTO> list = rev_dao.myReviewChart(mem_id);
		
		int cnt = 0;
		for(int i = 7; i < list.size();) {
			cnt += list.get(i).getCnt();
			list.remove(i);
		}
		
		if(cnt != 0) {
			ChartDTO dto = new ChartDTO();
			dto.setMov_genre_name("기타");
			dto.setCnt(cnt);
			list.add(dto);
		}
		
		return list;
	}
}
