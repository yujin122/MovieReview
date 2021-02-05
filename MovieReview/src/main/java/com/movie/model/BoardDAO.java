package com.movie.model;

import java.util.List;

import com.movie.service.Pagination;

public interface BoardDAO {
	public List<BoardDTO> getBoardList(Pagination pagination);		// 전체리스트
	public BoardDTO getBoardCont(int bnum);							// 상세내역
	public int setHit(int bnum);									// 조회수
	public void setCommCount(int bnum);								// 댓글 등록시 댓글수
	public void setdelCommCnt(int bnum, int cnt);							// 댓글 삭제시 댓글수
	public int getBoardCnt();										// 전체 게시물 수
	public int insertBoard(BoardDTO dto);							// 게시물 등록
	public int updateBoard(BoardDTO dto);							// 게시물 수정
	public int deleteBoard(int bnum);								// 게시물 삭제
	public int insertBoardReply(BoardDTO dto);						// 게시물 답글 등록
	public void updateReply(BoardDTO dto);							// 답글 등록시 step 수정
	public List<BoardDTO> searchBoardList(String label, String keyword, Pagination pagination);		// 게시물 검색
	public int searchBoardCnt(String label, String keyword);						// 검색한 게시물 수
	public List<BoardDTO> getMyBoardList(String mem_id, Pagination pagination);		// 마이페이지 내가 쓴 글
	public int getMyBoardCnt(String mem_id);							// 내가 쓴 글 수
	public int getBoardMaxNum();
}
