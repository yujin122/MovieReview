package com.movie.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.movie.model.BoardDTO;
import com.movie.model.CommDTO;
import com.movie.service.BoardService;
import com.movie.service.Pagination;

@Controller
public class BoardController {
	
	@Autowired private BoardService service;
	
	// 게시판 전체리스트
	@RequestMapping("/board_list.do")
	public String board_list(@RequestParam(required = false, defaultValue = "1") int page, Model model) {
		
		int rowSize = 10;
		int listCnt = service.boardAllCnt();
		
		Pagination pagination = new Pagination();
		pagination.pageInfo(page, rowSize, listCnt);
		
		List<BoardDTO> list = service.boardAllList(pagination);
		
		model.addAttribute("pagination", pagination);
		model.addAttribute("list", list);
		
		return "board/board_list";
	}
	
	// 게시판 상세페이지
	@RequestMapping("/board_cont.do")
	public String board_cont(@RequestParam(required = false, defaultValue = "1") int page, 
							Model model, @RequestParam("bnum") int bnum,
							@RequestParam(required = false) String label, @RequestParam(required = false) String keyword) {
		
		BoardDTO board_dto = service.boardCont(bnum);
		
		model.addAttribute("board_dto", board_dto);
		model.addAttribute("page", page);
		model.addAttribute("label", label);
		model.addAttribute("keyword", keyword);
		
		return "board/board_post";
	}
	
	// 게시판 글쓰기 폼
	@RequestMapping("/board_write_form.do")
	public String board_write_form() {
		return "board/board_form";
	}
	
	// 게시판 글쓰기
	@RequestMapping("/board_write.do")
	public String board_write(BoardDTO dto, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String board_writer = session.getAttribute("session_mem_id").toString();
		dto.setBoard_writer(board_writer);
		
		int res = service.boardInsert(dto);
		
		if(res > 0) {
			return "redirect:board_list.do";
		}else {
			return "redirect:board_write_form.do";
		}
	}
	
	// 수정 폼
	@RequestMapping("/board_update_form.do")
	public String board_update_form(@RequestParam int page, @RequestParam int bnum, Model model,
			@RequestParam(required = false) String label, @RequestParam(required = false) String keyword) {
		
		BoardDTO dto = service.boardUpdateForm(bnum);
		
		model.addAttribute("dto", dto);
		model.addAttribute("page", page);
		model.addAttribute("label", label);
		model.addAttribute("keyword", keyword);
		
		return "board/board_update";
	}
	
	// 수정
	@RequestMapping("/board_update.do")
	public String board_update(@RequestParam int page, BoardDTO dto, RedirectAttributes redirect,
			@RequestParam(required = false) String label, @RequestParam(required = false) String keyword) {
		int res = service.boardUpdate(dto);
		
		redirect.addAttribute("label", label);
		redirect.addAttribute("keyword", keyword);
		
		if(res > 0) {
			return "redirect:board_cont.do?bnum="+dto.getBoard_num()+"&page="+page;
		}else {
			return "redirect:board_update_form.do?bnum="+dto.getBoard_num()+"&page="+page;
		}
	}
	
	// 삭제
	@RequestMapping("/board_delete.do")
	public String board_delete(@RequestParam int bnum) {
		int res = service.boardDelete(bnum);
		
		if(res > 0) {
			return "redirect:board_list.do";
		}else {
			return "redirect:board_cont.do?bnum="+bnum;
		}
	}
	
	// 답글 폼
	@RequestMapping("/board_reply_form.do")
	public String board_reply_form(@RequestParam int bnum, Model model) {
		BoardDTO dto = service.boardReplyForm(bnum);
		
		model.addAttribute("dto", dto);
		
		return "board/board_reply_form";
	}
	
	// 답글
	@RequestMapping("/board_reply.do")
	public String board_reply(BoardDTO dto, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String board_writer = session.getAttribute("session_mem_id").toString();
		dto.setBoard_writer(board_writer);
		
		int res = service.boardReply(dto);
		
		if(res > 0) {
			return "redirect:board_list.do";
		}else {
			return "redirect:board_reply_form.do"+dto.getBoard_num();
		}
	}
	
	// 검색
	@RequestMapping("/board_search.do")
	public String board_search(@RequestParam(required = false, defaultValue = "1") int page, 
					@RequestParam String label, @RequestParam String keyword, Model model) {
		
		int rowsize = 10;
		int totalRecord = service.boardSearchCnt(label, keyword);
		
		Pagination pagination = new Pagination();
		pagination.pageInfo(page, rowsize, totalRecord);
		
		List<BoardDTO> list = service.boardSearch(label, keyword, pagination);
		
		model.addAttribute("list", list);
		model.addAttribute("label", label);
		model.addAttribute("keyword", keyword);
		model.addAttribute("pagination", pagination);
		
		return "board/board_search";
	}
	
	// 댓글 리스트
	@ResponseBody
	@RequestMapping(value = "/comm_list.do")
	public Map<String, Object> comm_list(@RequestParam int page, @RequestParam int bnum) {
		int rowsize = 5;
		int totalRecord = service.CommAllCnt(bnum);
		
		Pagination pagination = new Pagination();
		pagination.pageInfo(page, rowsize, totalRecord);
		
		List<CommDTO> list = service.CommAllList(bnum, pagination);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("comm", list);
		result.put("paging", pagination);
		
		return result;	
	}
	
	// 댓글 쓰기
	@ResponseBody
	@RequestMapping("/comm_insert.do")
	public int commInsert(@ModelAttribute CommDTO dto, HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		String comm_writer = session.getAttribute("session_mem_id").toString();
		dto.setComm_writer(comm_writer);
		
		return service.commInsert(dto);
	}
	
	// 댓글 삭제
	@ResponseBody
	@RequestMapping("/comm_delete.do")
	public int comm_delete(@RequestParam int comm_num) {
		return service.commDelete(comm_num);
	}
	
	// 댓글 수정
	@ResponseBody
	@RequestMapping("/comm_update.do")
	public int comm_update(@ModelAttribute CommDTO dto){
		return service.commUpdate(dto);
	}
	
	// 답댓글
	@ResponseBody
	@RequestMapping("/comm_reply.do")
	public int comm_reply(@ModelAttribute CommDTO dto, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String comm_writer = session.getAttribute("session_mem_id").toString();
		dto.setComm_writer(comm_writer);
		
		return service.commReplyInsert(dto);
	}
}
