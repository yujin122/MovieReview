package com.movie.controller;

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
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.movie.model.BoardDTO;
import com.movie.model.ChartDTO;
import com.movie.model.CommDTO;
import com.movie.model.MemberDTO;
import com.movie.model.MovieDTO;
import com.movie.model.ReviewDTO;
import com.movie.service.MypageService;
import com.movie.service.Pagination;
import com.movie.service.Upload;

@Controller
public class MypageController {
	
	@Autowired private MypageService service;
	@Autowired private Upload upload;
	
	@RequestMapping("/mypage_main.do")
	public String movie_main(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String mem_id = session.getAttribute("session_mem_id").toString();
		
		Map<String, Object> map = service.myInfo(mem_id);
		
		if((Integer)map.get("review") != 0) {
			model.addAttribute("mov_list", service.movieRecommend(mem_id));
		}
		// 찜한 리스트
		List<MovieDTO> likeList = service.movieLikeList(mem_id);
			
		model.addAttribute("like_list", likeList);
		model.addAttribute("mem_dto", (MemberDTO)map.get("member"));
		model.addAttribute("boardCnt", (Integer)map.get("board"));
		model.addAttribute("reviewCnt", (Integer)map.get("review"));
		model.addAttribute("likeCnt", (Integer)map.get("like"));
		
		return "mypage/mypage_main";
	}
	
	// 리뷰 장르 차트
	@ResponseBody
	@RequestMapping("/mov_review_chart.do")
	public List<ChartDTO> mov_review_chart(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String mem_id = session.getAttribute("session_mem_id").toString();
		
		return service.movieReviewChart(mem_id);
	}
	
	// 내가 쓴 글
	@RequestMapping("/my_board_list.do")
	public String board_list(@RequestParam(required = false, defaultValue = "1") int page, HttpServletRequest request,
								Model model) {
		HttpSession session = request.getSession();
		String mem_id = session.getAttribute("session_mem_id").toString();
		
		int rowsize = 10;
		int totalRecord = service.myBoardCnt(mem_id);
		
		Pagination pagination = new Pagination();
		pagination.pageInfo(page, rowsize, totalRecord);
		
		List<BoardDTO> list = service.myBoardList(mem_id, pagination);
		
		Map<String, Object> map = service.myInfo(mem_id);
		
		model.addAttribute("mem_dto", (MemberDTO)map.get("member"));
		model.addAttribute("boardCnt", (Integer)map.get("board"));
		model.addAttribute("reviewCnt", (Integer)map.get("review"));
		model.addAttribute("likeCnt", (Integer)map.get("like"));
		model.addAttribute("list", list);
		model.addAttribute("pagination", pagination);
		
		return "mypage/manage_board";
		
	}
	
	// 게시글 삭제
	@RequestMapping("board_list_delete.do")
	public String board_list_del(@RequestParam int[] check, RedirectAttributes redirect) {
		
		if(service.myBoardDelete(check) > 0) {
			return "redirect:my_board_list.do";
		}else {
			redirect.addFlashAttribute("msg", "게시글 삭제를 실패했습니다.");
			
			return "redirect:my_board_list.do";
		}
	}
	
	// 내가 쓴 댓글
	@RequestMapping("/my_comm_list.do")
	public String comm_list(@RequestParam(required = false, defaultValue = "1") int page, HttpServletRequest request,
								Model model) {
		HttpSession session = request.getSession();
		String mem_id = session.getAttribute("session_mem_id").toString();
		
		int rowsize = 10;
		int totalRecord = service.myCommCnt(mem_id);
		
		Pagination pagination = new Pagination();
		pagination.pageInfo(page, rowsize, totalRecord);
		
		List<CommDTO> list = service.myCommList(mem_id, pagination);
		
		Map<String, Object> map = service.myInfo(mem_id);
		
		model.addAttribute("mem_dto", (MemberDTO)map.get("member"));
		model.addAttribute("boardCnt", (Integer)map.get("board"));
		model.addAttribute("reviewCnt", (Integer)map.get("review"));
		model.addAttribute("likeCnt", (Integer)map.get("like"));
		model.addAttribute("list", list);
		model.addAttribute("pagination", pagination);
		
		return "mypage/manage_comment";
	}
	
	// 댓글 삭제
	@RequestMapping("/comm_list_delete.do")
	public String comm_list_del(@RequestParam int[] check, RedirectAttributes redirect) {
		
		if(service.myCommDelete(check) > 0) {
			return "redirect:my_comm_list.do";
		}else {
			redirect.addFlashAttribute("msg", "댓글 삭제를 실패했습니다.");
			
			return "redirect:my_comm_list.do";
		}
	}
	
	// 내가 쓴 리뷰
	@RequestMapping("/my_review_list.do")
	public String myReviewList(HttpServletRequest request, @RequestParam(required = false, defaultValue = "1") int page, Model model) {
		HttpSession session = request.getSession();
		String mem_id = session.getAttribute("session_mem_id").toString();
		
		int totalRecord = service.myReviewCnt(mem_id);
		
		Pagination pagination = new Pagination();
		pagination.pageInfo(page, 10, totalRecord);
		
		List<ReviewDTO> list = service.myReviewList(mem_id, pagination);
		
		Map<String, Object> map = service.myInfo(mem_id);
		
		model.addAttribute("mem_dto", (MemberDTO)map.get("member"));
		model.addAttribute("boardCnt", (Integer)map.get("board"));
		model.addAttribute("reviewCnt", (Integer)map.get("review"));
		model.addAttribute("likeCnt", (Integer)map.get("like"));
		model.addAttribute("list", list);
		model.addAttribute("pagination", pagination);
		
		return "mypage/manage_review";
	}
	// 리뷰 삭제
	@RequestMapping("/my_review_del.do")
	public String myReviewDel(@RequestParam int[] check, RedirectAttributes rtts) {
		
		if(service.myReviewDelete(check) == check.length) {
			return "redirect:my_review_list.do";
		}else {
			rtts.addFlashAttribute("msg", "삭제 실패");
			return "redirect:my_review_list.do";
		}
	}
	
	// 찜한 영화
	@RequestMapping("/my_like_mov.do")
	public String MyLikeMov(@RequestParam(required = false, defaultValue = "1") int page,HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		String mem_id = session.getAttribute("session_mem_id").toString();
	
		int totalRecord = service.myLikeCnt(mem_id);
		
		Pagination pagination = new Pagination();
		pagination.pageInfo(page, 8, totalRecord);
		
		List<MovieDTO> list = service.myLikeList(mem_id, pagination);
		
		Map<String, Object> map = service.myInfo(mem_id);
		
		model.addAttribute("mem_dto", (MemberDTO)map.get("member"));
		model.addAttribute("boardCnt", (Integer)map.get("board"));
		model.addAttribute("reviewCnt", (Integer)map.get("review"));
		model.addAttribute("likeCnt", (Integer)map.get("like"));
		model.addAttribute("list", list);
		model.addAttribute("pagination", pagination);
		
		return "mypage/manage_like";
	}
	// 정보수정 폼
	@RequestMapping("/my_info_edit.do")
	public String myInfoEdit(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String mem_id = session.getAttribute("session_mem_id").toString();
		
		MemberDTO dto = service.myInfoEditForm(mem_id);
		
		model.addAttribute("dto", dto);
		
		return "mypage/user_info_edit";
	}
	// 정보수정
	@RequestMapping("/info_update.do")
	public String infoUpdate(@ModelAttribute MemberDTO dto, MultipartHttpServletRequest mRequest, RedirectAttributes rtts){
		String study_img = upload.fileUplaod(mRequest);
		
		if(!study_img.equals("")) {
			dto.setMem_img(study_img);
		}
		
		String mem_addr = mRequest.getParameter("zip") + "/" + mRequest.getParameter("addr1") + "/" + mRequest.getParameter("addr2");
		String mem_phone = mRequest.getParameter("phone1") + "-" + mRequest.getParameter("phone2") + "-" + mRequest.getParameter("phone3");
		
		dto.setMem_addr(mem_addr);
		dto.setMem_phone(mem_phone);
		
		int res = service.myInfoEdit(dto);
		
		if(res > 0) {
			return "redirect:my_info_edit.do";
		}else {
			rtts.addFlashAttribute("msg", "회원정보 수정 실패");
			return "redirect:my_info_edit.do";
		}
	}
}
