package com.movie.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movie.model.BoardDAO;
import com.movie.model.BoardDTO;
import com.movie.model.CommDAO;
import com.movie.model.CommDTO;

@Service
public class BoardServiceImpl implements BoardService{
	@Autowired private BoardDAO board_dao;
	@Autowired private CommDAO comm_dao;
	
	@Override
	public int boardAllCnt() {
		return board_dao.getBoardCnt();
	}
	
	@Override
	public List<BoardDTO> boardAllList(Pagination pagination) {
		return board_dao.getBoardList(pagination);
	}
	
	@Override
	public BoardDTO boardCont(int bnum) {
		board_dao.setHit(bnum);
		
		return board_dao.getBoardCont(bnum);
	}
	
	@Override
	public int boardInsert(BoardDTO dto) {
		
		int cnt = board_dao.getBoardCnt();
		int board_num = 1;
		
		if(cnt > 0) {
			board_num = board_dao.getBoardMaxNum()+1;
		}
		
		dto.setBoard_num(board_num);
		
		return board_dao.insertBoard(dto);
	}
	
	@Override
	public BoardDTO boardUpdateForm(int bnum) {
		return board_dao.getBoardCont(bnum);
	}
	
	@Override
	public int boardUpdate(BoardDTO dto) {
		return board_dao.updateBoard(dto);
	}
	
	@Transactional
	@Override
	public int boardDelete(int bnum) {
		comm_dao.deleteBoardComm(bnum);
		return board_dao.deleteBoard(bnum);
	}
	
	@Override
	public BoardDTO boardReplyForm(int bnum) {
		return board_dao.getBoardCont(bnum);
	}
	
	@Override
	public int boardReply(BoardDTO dto) {
		BoardDTO pre_dto = board_dao.getBoardCont(dto.getBoard_num());
		board_dao.updateReply(pre_dto);
		
		dto.setBoard_group(pre_dto.getBoard_group());
		dto.setBoard_step(pre_dto.getBoard_step());
		dto.setBoard_indent(pre_dto.getBoard_indent());
		
		return board_dao.insertBoardReply(dto);
	}
	
	@Override
	public int boardSearchCnt(String label, String keyword) {
		return board_dao.searchBoardCnt(label, keyword);
	}
	
	@Override
	public List<BoardDTO> boardSearch(String label, String keyword, Pagination pagination) {
		return board_dao.searchBoardList(label, keyword, pagination);
	}
	
	@Override
	public int CommAllCnt(int bnum) {
		return comm_dao.getCommCnt(bnum);
	}
	
	@Override
	public List<CommDTO> CommAllList(int bnum, Pagination pagination) {
		return comm_dao.getCommList(bnum, pagination);
	}
	
	@Override
	public int commInsert(CommDTO dto) {
		int comm_cnt = comm_dao.getCommCnt(dto.getComm_board());
		int comm_num = 1;
		
		if(comm_cnt > 0) {
			comm_num = comm_dao.getMaxNum(dto.getComm_board())+1;
		}
		dto.setComm_num(comm_num);
		dto.setComm_group(comm_num);
		
		return comm_dao.insertComm(dto);
	}
	
	@Override
	public int commDelete(int comm_num) {
		return comm_dao.deleteComm(comm_num);
	}
	
	@Override
	public int commUpdate(CommDTO dto) {
		return comm_dao.updateComm(dto);
	}
	
	@Override
	public int commReplyInsert(CommDTO dto) {
		// 원댓글 정보
		CommDTO pre_dto = comm_dao.getCommCont(dto.getComm_num());
		// 댓글 그룹의 step 최대값
		int comm_step = comm_dao.getMaxStep(pre_dto.getComm_group());
		dto.setComm_step(comm_step);
		dto.setComm_board(pre_dto.getComm_board());
		dto.setComm_group(pre_dto.getComm_group());
		
		return comm_dao.insertCommReply(dto);
	}
}
