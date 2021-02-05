package com.movie.service;

import java.util.List;

import com.movie.model.BoardDTO;
import com.movie.model.CommDTO;

public interface BoardService {
	public int boardAllCnt();
	public List<BoardDTO> boardAllList(Pagination pagination);;
	public BoardDTO boardCont(int bnum);
	public int boardInsert(BoardDTO dto);
	public BoardDTO boardUpdateForm(int bnum);
	public int boardUpdate(BoardDTO dto);
	public int boardDelete(int bnum);
	public BoardDTO boardReplyForm(int bnum);
	public int boardReply(BoardDTO dto);
	public int boardSearchCnt(String label, String keyword);
	public List<BoardDTO> boardSearch(String label, String keyword, Pagination pagination);
	public int CommAllCnt(int bnum);
	public List<CommDTO> CommAllList(int bnum, Pagination pagination);
	public int commInsert(CommDTO dto);
	public int commDelete(int comm_num);
	public int commUpdate(CommDTO dto);
	public int commReplyInsert(CommDTO dto);
}
