package com.movie.review;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.movie.model.BoardDTO;
import com.movie.model.Pagination;
import com.movie.service.BoardService;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService service;
	
	@RequestMapping("/board_list.do")
	public String list(@RequestParam(required = false, defaultValue = "1") int page, Model model) {
		
		int rowSize = 10;
		int listCnt = service.allBoardCnt();
		
		Pagination pagination = new Pagination();
		pagination.pageInfo(page, rowSize, listCnt);
		
		List<BoardDTO> list = service.list(pagination);
		
		model.addAttribute("pagination", pagination);
		model.addAttribute("list", list);
		
		return "board/board_list";
	}
	
	@RequestMapping("/board_cont.do")
	public String board_cont(@RequestParam(required = false, defaultValue = "1") int page, 
							@RequestParam(required = false) String label, @RequestParam(required = false) String keyword, 
							@RequestParam("bnum") int bnum, Model model) {

		BoardDTO board_dto = service.cont(bnum);
		
		model.addAttribute("board_dto", board_dto);
		model.addAttribute("page", page);
		model.addAttribute("label", label);
		model.addAttribute("keyword", keyword);
		
		return "board/board_post";
	}
	
	@RequestMapping("/board_write_form.do")
	public String board_write_form() {
		return "board/board_form";
	}
	
	@RequestMapping("/board_write.do")
	public String board_write(@ModelAttribute BoardDTO dto, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String board_writer = session.getAttribute("session_mem_id").toString();
		dto.setBoard_writer(board_writer);
		
		int res = service.insert(dto);
		
		if(res > 0) {
			return "redirect:board_list.do";
		}else {
			return "redirect:board_write_form.do";
		}
	}
	
	@RequestMapping("/board_update_form.do")
	public String board_update_form(@RequestParam int page, @RequestParam int bnum, Model model,
			@RequestParam(required = false) String label, @RequestParam(required = false) String keyword) {
		
		BoardDTO dto = service.cont(bnum);
		
		model.addAttribute("dto", dto);
		model.addAttribute("page", page);
		model.addAttribute("label", label);
		model.addAttribute("keyword", keyword);
		
		return "board/board_update";
	}
	
	@RequestMapping("/board_update.do")
	public String board_update(@RequestParam int page, @ModelAttribute BoardDTO dto, RedirectAttributes redirect,
			@RequestParam(required = false) String label, @RequestParam(required = false) String keyword) {
		int res = service.update(dto);
		
		redirect.addAttribute("label", label);
		redirect.addAttribute("keyword", keyword);
		
		if(res > 0) {
			return "redirect:board_cont.do?bnum="+dto.getBoard_num()+"&page="+page;
		}else {
			return "redirect:board_update_form.do?bnum="+dto.getBoard_num()+"&page="+page;
		}
	}
	
	@RequestMapping("/board_delete.do")
	public String board_delete(@RequestParam int bnum) throws Exception {
		int res = service.delete(bnum);
		
		if(res > 0) {
			return "redirect:board_list.do";
		}else {
			return "redirect:board_cont.do?bnum="+bnum;
		}
	}
	
	@RequestMapping("/board_reply_form.do")
	public String board_reply_form(@RequestParam int bnum, Model model) {
		BoardDTO dto = service.cont(bnum);
		
		model.addAttribute("dto", dto);
		
		return "board/board_reply_form";
	}
	
	@RequestMapping("/board_reply.do")
	public String board_reply(BoardDTO dto, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String board_writer = session.getAttribute("session_mem_id").toString();
		
		int res = service.reply_insert(dto, board_writer);
		
		if(res > 0) {
			return "redirect:board_list.do";
		}else {
			return "redirect:board_reply_form.do"+dto.getBoard_num();
		}
	}
	
	@RequestMapping("/board_search.do")
	public String board_search(@RequestParam(required = false, defaultValue = "1") int page, 
					@RequestParam String label, @RequestParam String keyword, Model model) {
		
		int rowsize = 10;
		int totalRecord = service.searchBoardCnt(label, keyword);
		
		Pagination pagination = new Pagination();
		pagination.pageInfo(page, rowsize, totalRecord);
		
		List<BoardDTO> list = service.searchList(label, keyword, pagination);
		
		model.addAttribute("list", list);
		model.addAttribute("label", label);
		model.addAttribute("keyword", keyword);
		model.addAttribute("pagination", pagination);
		
		return "board/board_search";
	}
}
