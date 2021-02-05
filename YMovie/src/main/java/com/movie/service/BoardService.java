package com.movie.service;

import java.util.List;

import com.movie.model.BoardDTO;
import com.movie.model.Pagination;

public interface BoardService {
	
	public List<BoardDTO> list(Pagination pagination);					// 전체리스트
	public int allBoardCnt();											// 전체 게시물 수 
	public BoardDTO cont(int bnum);
	public int insert(BoardDTO dto);
	public int update(BoardDTO dto);
	public int delete(int bnum) throws Exception;
	public int reply_insert(BoardDTO dto, String board_writer);
	public List<BoardDTO> searchList(String label, String keyword, Pagination pagination);
	public int searchBoardCnt(String label, String keyword);
}
