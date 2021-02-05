package com.movie.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movie.model.BoardDAO;
import com.movie.model.BoardDTO;
import com.movie.model.CommDAO;
import com.movie.model.Pagination;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired private BoardDAO b_dao;
	@Autowired private CommDAO c_dao;
	
	@Override
	public List<BoardDTO> list(Pagination pagination) {
		
		return b_dao.getBoardList(pagination);
	}
	
	@Override
	public int allBoardCnt() {
		return b_dao.getBoardCnt();
	}
	
	@Override
	public BoardDTO cont(int bnum) {
		
		b_dao.setHit(bnum);
		
		return b_dao.getBoardCont(bnum);
	}
	
	@Override
	public int insert(BoardDTO dto) {
		return b_dao.insertBoard(dto);
	}
	
	@Override
	public int update(BoardDTO dto) {
		return b_dao.updateBoard(dto);
	}
	
	@Transactional
	@Override
	public int delete(int bnum) throws Exception {
		try {
			b_dao.deleteBoard(bnum);
			c_dao.deleteBoardComm(bnum);
			
			return 1;
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
	}
	
	@Override
	public int reply_insert(BoardDTO dto, String board_writer) {
		
		BoardDTO pre_dto = b_dao.getBoardCont(dto.getBoard_num());
		
		b_dao.updateReply(pre_dto);
		
		dto.setBoard_writer(board_writer);
		dto.setBoard_group(pre_dto.getBoard_group());
		dto.setBoard_step(pre_dto.getBoard_step());
		dto.setBoard_indent(pre_dto.getBoard_indent());
		
		return b_dao.insertBoardReply(dto);
	}
	
	@Override
	public int searchBoardCnt(String label, String keyword) {
		return b_dao.searchBoardCnt(label, keyword);
	}	
	
	@Override
	public List<BoardDTO> searchList(String label, String keyword, Pagination pagination) {
		return b_dao.searchBoardList(label, keyword, pagination);
	}

}
