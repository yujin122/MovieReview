package com.movie.service;

import java.util.List;
import java.util.Map;

import com.movie.model.BoardDTO;
import com.movie.model.ChartDTO;
import com.movie.model.CommDTO;
import com.movie.model.MemberDTO;
import com.movie.model.MovieDTO;
import com.movie.model.ReviewDTO;

public interface MypageService {
	public Map<String, Object> myInfo(String mem_id);
	public List<MovieDTO> movieRecommend(String mem_id);
	public List<ChartDTO> movieReviewChart(String mem_id);
	public List<MovieDTO> movieLikeList(String mem_id);
	public int myBoardCnt(String mem_id);
	public List<BoardDTO> myBoardList(String mem_id, Pagination pagination);
	public int myBoardDelete(int[] check);
	public int myCommCnt(String mem_id);
	public List<CommDTO> myCommList(String mem_id, Pagination pagination);
	public int myCommDelete(int[] check);
	public int myReviewCnt(String mem_id);
	public List<ReviewDTO> myReviewList(String mem_id, Pagination pagination);
	public int myReviewDelete(int[] check);
	public int myLikeCnt(String mem_id);
	public List<MovieDTO> myLikeList(String mem_id, Pagination pagination);
	public MemberDTO myInfoEditForm(String mem_id);
	public int myInfoEdit(MemberDTO dto);
}
