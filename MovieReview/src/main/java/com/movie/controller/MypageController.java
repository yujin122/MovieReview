package com.movie.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
import com.movie.service.MypageService;
import com.movie.service.Pagination;
import com.movie.service.Upload;

@Controller
public class MypageController {
	
	@Autowired private MypageService service;
	
	@Autowired private BoardDAO board_dao;
	@Autowired private CommDAO comm_dao;
	@Autowired private ReviewDAO rev_dao;
	@Autowired private ReviewLikeDAO rl_dao;
	@Autowired private LikeDAO like_dao;
	@Autowired private MovieDAO mov_dao;
	@Autowired private MemberDAO mem_dao;
	@Autowired private GenreDAO gen_dao;
	@Autowired private Upload upload;
	
	@RequestMapping("/mypage_main.do")
	public String movie_main(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String mem_id = session.getAttribute("session_mem_id").toString();
		
		Map<String, Object> map = service.myInfo(mem_id);
		
		if((Integer)map.get("review") == 0) {
			model.addAttribute("mov_list", service.movieRecommend(mem_id));
		}
		// 찜한 리스트
		List<MovieDTO> likeList = like_dao.like_list(mem_id);
					
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
		
		MemberDTO mem_dto = mem_dao.getMemberCont(mem_id);
		int boardCnt = board_dao.getMyBoardCnt(mem_id);
		int reviewCnt = rev_dao.myReviewCnt(mem_id);
		int likeCnt = like_dao.getLikeCnt(mem_id);
		
		int rowsize = 10;
		int totalRecord = board_dao.getMyBoardCnt(mem_id);
		
		Pagination pagination = new Pagination();
		pagination.pageInfo(page, rowsize, totalRecord);
		
		List<BoardDTO> list = board_dao.getMyBoardList(mem_id, pagination);
		
		model.addAttribute("mem_dto", mem_dto);
		model.addAttribute("boardCnt", boardCnt);
		model.addAttribute("reviewCnt", reviewCnt);
		model.addAttribute("likeCnt", likeCnt);
		model.addAttribute("list", list);
		model.addAttribute("pagination", pagination);
		
		return "mypage/manage_board";
		
	}
	
	// 내가 쓴 댓글
	@RequestMapping("/my_comm_list.do")
	public String comm_list(@RequestParam(required = false, defaultValue = "1") int page, HttpServletRequest request,
								Model model) {
		HttpSession session = request.getSession();
		String mem_id = session.getAttribute("session_mem_id").toString();
		
		MemberDTO mem_dto = mem_dao.getMemberCont(mem_id);
		int boardCnt = board_dao.getMyBoardCnt(mem_id);
		int reviewCnt = rev_dao.myReviewCnt(mem_id);
		int likeCnt = like_dao.getLikeCnt(mem_id);
		
		int rowsize = 10;
		int totalRecord = comm_dao.getMyCommCnt(mem_id);
		
		Pagination pagination = new Pagination();
		pagination.pageInfo(page, rowsize, totalRecord);
		
		List<CommDTO> list = comm_dao.getMyCommList(mem_id, pagination);
		
		model.addAttribute("mem_dto", mem_dto);
		model.addAttribute("boardCnt", boardCnt);
		model.addAttribute("reviewCnt", reviewCnt);
		model.addAttribute("likeCnt", likeCnt);
		model.addAttribute("list", list);
		model.addAttribute("pagination", pagination);
		
		return "mypage/manage_comment";
	}
	
	@RequestMapping("/comm_list_delete.do")
	public String comm_list_del(@RequestParam int[] check, RedirectAttributes redirect) {
		int res = 0;
		
		for(int i = 0; i < check.length; i++) {
			int bnum = comm_dao.getCommCont(check[i]).getComm_board();
			
			res = comm_dao.deleteComm(check[i]);
			
			if(res > 0) {
				board_dao.setdelCommCnt(bnum, check.length);
			}
		}
		
		if(res > 0) {
			return "redirect:my_comm_list.do";
		}else {
			redirect.addFlashAttribute("msg", "댓글 삭제를 실패했습니다.");
			
			return "redirect:my_comm_list.do";
		}
	}
	
	@RequestMapping("board_list_delete.do")
	public String board_list_del(@RequestParam int[] check, RedirectAttributes redirect) {
		int res = 0;
		
		for(int i = 0; i < check.length; i++) {
			res = board_dao.deleteBoard(check[i]);
			
			if(res > 0) {
				comm_dao.deleteBoardComm(check[i]);
			}
		}
		
		if(res > 0) {
			return "redirect:my_board_list.do";
		}else {
			redirect.addFlashAttribute("msg", "게시글 삭제를 실패했습니다.");
			
			return "redirect:my_board_list.do";
		}
	}
	
	// 내가 쓴 리뷰
	@RequestMapping("/my_review_list.do")
	public String myReviewList(HttpServletRequest request, @RequestParam(required = false, defaultValue = "1") int page, Model model) {
		HttpSession session = request.getSession();
		String mem_id = session.getAttribute("session_mem_id").toString();
		
		MemberDTO mem_dto = mem_dao.getMemberCont(mem_id);
		int boardCnt = board_dao.getMyBoardCnt(mem_id);
		int reviewCnt = rev_dao.myReviewCnt(mem_id);
		int likeCnt = like_dao.getLikeCnt(mem_id);
		
		int totalRecord = rev_dao.myReviewCnt(mem_id);
		
		Pagination pagination = new Pagination();
		pagination.pageInfo(page, 5, totalRecord);
		
		List<ReviewDTO> list = rev_dao.myReviewList(mem_id, pagination);
		
		model.addAttribute("mem_dto", mem_dto);
		model.addAttribute("boardCnt", boardCnt);
		model.addAttribute("reviewCnt", reviewCnt);
		model.addAttribute("likeCnt", likeCnt);
		model.addAttribute("list", list);
		model.addAttribute("pagination", pagination);
		
		return "mypage/manage_review";
	}
	
	@RequestMapping("/my_review_del.do")
	public String myReviewDel(@RequestParam int[] check, RedirectAttributes rtts) {
		int res = 0;
		
		for(int i = 0; i < check.length; i++) {
			int review_num = check[i];
			res += rev_dao.deleteReview(review_num);
			rl_dao.deleteReviewLike(review_num);
		}
		
		if(res == check.length) {
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
		
		MemberDTO mem_dto = mem_dao.getMemberCont(mem_id);
		int boardCnt = board_dao.getMyBoardCnt(mem_id);
		int reviewCnt = rev_dao.myReviewCnt(mem_id);
		int likeCnt = like_dao.getLikeCnt(mem_id);
		
		int totalRecord = like_dao.getLikeCnt(mem_id);
		
		Pagination pagination = new Pagination();
		pagination.pageInfo(page, 8, totalRecord);
		
		List<MovieDTO> list = like_dao.likeAllList(mem_id, pagination);
		
		for(int i = 0; i < list.size(); i++) {
			MovieDTO dto = list.get(i);
			int cnt = rev_dao.checkReview(dto.getMov_code());
			float avg = 0;
			if(cnt > 0) {
				avg = rev_dao.myReviewRatingAvg(dto.getMov_code());
			}
			dto.setMov_avgrating(String.valueOf(avg));
		}
		
		for(int j = 0; j < list.size(); j++) {
			MovieDTO dto = list.get(j);
			List<GenreDTO> gen_list = gen_dao.getGenre(dto.getMov_code());
			String genre = "";
			for(int k = 0; k < gen_list.size(); k++) {
				genre += gen_list.get(k).getMov_genre_name() + " ";
			}
			dto.setMov_genres(genre);
		}
		
		model.addAttribute("mem_dto", mem_dto);
		model.addAttribute("boardCnt", boardCnt);
		model.addAttribute("reviewCnt", reviewCnt);
		model.addAttribute("likeCnt", likeCnt);
		model.addAttribute("list", list);
		model.addAttribute("pagination", pagination);
		
		return "mypage/manage_like";
	}
	
	@RequestMapping("/my_info_edit.do")
	public String myInfoEdit(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String mem_id = session.getAttribute("session_mem_id").toString();
		
		MemberDTO dto = mem_dao.getMemberCont(mem_id);
		
		model.addAttribute("dto", dto);
		
		return "mypage/user_info_edit";
	}
	
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
		
		int res = mem_dao.updateMember(dto);
		
		if(res > 0) {
			return "redirect:my_info_edit.do";
		}else {
			rtts.addFlashAttribute("msg", "회원정보 수정 실패");
			return "redirect:my_info_edit.do";
		}
	}
}
