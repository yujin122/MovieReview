package com.movie.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.movie.model.BoardDAO;
import com.movie.model.BoardDTO;
import com.movie.model.ChartDTO;
import com.movie.model.CommDAO;
import com.movie.model.CommDTO;
import com.movie.model.GenreDAO;
import com.movie.model.GenreDTO;
import com.movie.model.LikeDAO;
import com.movie.model.MemberDAO;
import com.movie.model.MemberDTO;
import com.movie.model.MovieDAO;
import com.movie.model.MovieDTO;
import com.movie.model.ReviewDAO;
import com.movie.model.ReviewDTO;
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
	
	@Override
	public List<MovieDTO> movieLikeList(String mem_id) {
		return like_dao.like_list(mem_id);
	}
	
	@Override
	public int myBoardCnt(String mem_id) {
		return board_dao.getMyBoardCnt(mem_id);
	}
	
	@Override
	public List<BoardDTO> myBoardList(String mem_id, Pagination pagination) {
		return board_dao.getMyBoardList(mem_id, pagination);
	}
	
	@Override
	public int myCommCnt(String mem_id) {
		return comm_dao.getMyCommCnt(mem_id);
	}
	
	@Override
	public List<CommDTO> myCommList(String mem_id, Pagination pagination) {
		return comm_dao.getMyCommList(mem_id, pagination);
	}
	
	@Override
	public int myCommDelete(int[] check) {
		int res = 0;
		
		for(int i = 0; i < check.length; i++) {
			res += comm_dao.deleteComm(check[i]);
			
		}
		
		return res;
	}
	
	@Transactional
	@Override
	public int myBoardDelete(int[] check) {
		int res = 0;
		
		for(int i = 0; i < check.length; i++) {
			comm_dao.deleteBoardComm(check[i]);
			
			res += board_dao.deleteBoard(check[i]);
		}
		
		return res;
	}
	
	@Override
	public int myReviewCnt(String mem_id) {
		return rev_dao.myReviewCnt(mem_id);
	}
	
	@Override
	public List<ReviewDTO> myReviewList(String mem_id, Pagination pagination) {
		return rev_dao.myReviewList(mem_id, pagination);
	}
	
	@Transactional
	@Override
	public int myReviewDelete(int[] check) {
		int res = 0;
		
		for(int i = 0; i < check.length; i++) {
			int review_num = check[i];
			rl_dao.deleteReviewLike(review_num);
			res += rev_dao.deleteReview(review_num);
		}
		
		return res;
	}
	
	@Override
	public int myLikeCnt(String mem_id) {
		return like_dao.getLikeCnt(mem_id);
	}
	
	@Override
	public List<MovieDTO> myLikeList(String mem_id, Pagination pagination) {
		List<MovieDTO> list = like_dao.likeAllList(mem_id, pagination);
		
		// 평균별점
		for(int i = 0; i < list.size(); i++) {
			MovieDTO dto = list.get(i);
			int cnt = rev_dao.checkReview(dto.getMov_code());
			float avg = 0;
			if(cnt > 0) {
				avg = rev_dao.myReviewRatingAvg(dto.getMov_code());
			}
			dto.setMov_avgrating(String.valueOf(avg));
		}
		// 장르
		for(int j = 0; j < list.size(); j++) {
			MovieDTO dto = list.get(j);
			List<GenreDTO> gen_list = gen_dao.getGenre(dto.getMov_code());
			String genre = "";
			for(int k = 0; k < gen_list.size(); k++) {
				genre += gen_list.get(k).getMov_genre_name() + " ";
			}
			dto.setMov_genres(genre);
		}
		return list;
	}
	
	@Override
	public MemberDTO myInfoEditForm(String mem_id) {
		return mem_dao.getMemberCont(mem_id);
	}
	
	@Override
	public int myInfoEdit(MemberDTO dto) {
		// TODO Auto-generated method stub
		return mem_dao.updateMember(dto);
	}
	
}
