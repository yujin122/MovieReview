package com.movie.controller;

import java.io.IOException;
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

import com.movie.model.GenreDTO;
import com.movie.model.MovieAPI;
import com.movie.model.MovieDTO;
import com.movie.model.NaverAPI;
import com.movie.model.ReviewDTO;
import com.movie.model.ReviewInfoDTO;
import com.movie.model.ReviewLikeDTO;
import com.movie.service.Pagination;
import com.movie.service.ReviewService;

@Controller
public class ReviewController {
	
	@Autowired private ReviewService service;
	
	@RequestMapping("/review_write_form.do")
	public String review_write_form(@RequestParam String mov_num, Model model, HttpSession session) throws IOException {
		int check = service.movieCheck(mov_num);
		float rating = 0;
		
		List<String> rating_list = service.reviewRating(mov_num);
		for(int j = 0; j < rating_list.size(); j++) {
			rating += Float.parseFloat(rating_list.get(j));
		}
		if(rating != 0) {
			rating = rating / rating_list.size();
		}
		
		if(check > 0) {
			MovieDTO dto = service.movieCont(mov_num);
			dto.setMov_avgrating(String.valueOf(rating));
			List<GenreDTO> list = service.genreList(mov_num);
			
			String genre = "";
			for(int i = 0; i < list.size(); i++) {
				genre += list.get(i).getMov_genre_name() + "|";
			}
			
			model.addAttribute("genre", genre);
			model.addAttribute("dto", dto);
			model.addAttribute("expect", expect_rating(genre,session));
			
			return "movie/review_form";
		}else {
			MovieAPI mov_api = new MovieAPI();
			
			Map<String, Object> map = mov_api.getMovieCont(mov_num,rating);
			
			MovieDTO dto = (MovieDTO)map.get("mov_info");
			String genre = (String)map.get("genre");
			
			NaverAPI n_api = new NaverAPI();
			MovieDTO mov_dto = n_api.getPoster(dto);
			
			model.addAttribute("dto", mov_dto);
			model.addAttribute("genre", genre);
			model.addAttribute("expect", expect_rating(genre,session));
			
			return "movie/review_form";
		}
		
	}
	
	public float expect_rating(String genres, HttpSession session) {
		String mem_id = session.getAttribute("session_mem_id").toString();
		String[] genre_list = genres.split("\\|");
		
		
		return service.expectRating(genre_list, mem_id);
	}
	
	@RequestMapping("/review_insert.do")
	public String review_insert(@ModelAttribute MovieDTO m_dto, @RequestParam String mov_genre, @ModelAttribute ReviewDTO r_dto, 
						RedirectAttributes rtts, HttpServletRequest request) {
		
		// 장르에 값이 없을 때
		if(mov_genre.equals("")) {
			mov_genre = "기타";
		}
		// 영화 정보가 이미 있는지 확인
		int checkMovie = service.movieCheck(m_dto.getMov_code());
		
		if(checkMovie == 0) {	// 없을때
			
			Map<String , Integer> map = service.movieInsert(m_dto, mov_genre);
			
			if(map.get("movie") <= 0) {
				rtts.addFlashAttribute("msg","영화 정보 넣기 실패");
				return "redirect:review_write_form.do?mov_num="+m_dto.getMov_code();
			}
			
			if(map.get("genre") <= 0) {
				rtts.addFlashAttribute("msg", "장르 넣기 실패");
				return "redirect:review_write_form.do?mov_num="+m_dto.getMov_code();
			}
		}
		
		HttpSession session = request.getSession();
		String mem_id = session.getAttribute("session_mem_id").toString();
		
		r_dto.setReview_mov(m_dto.getMov_code());
		r_dto.setReview_writer(mem_id);
		
		int rev_result = service.reviewInsert(r_dto);
		
		if(rev_result > 0) {
			return "redirect:/";
		}else {
			rtts.addFlashAttribute("msg", "리뷰 등록 실패");
			return "redirect:review_write_form.do?mov_num="+m_dto.getMov_code();
		}
	}
	
	@RequestMapping("/")
	public String review_list(@RequestParam(required = false, defaultValue = "1") int page, Model model) {
		
		int totalRecord = service.reviewAllCnt();
		
		Pagination pagination = new Pagination();
		pagination.pageInfo(page, 10, totalRecord);
		
		List<ReviewDTO> list = service.reviewAllList(pagination);
		
		model.addAttribute("list",list);
		model.addAttribute("pagination", pagination);
		
		return "home";
	}
	
	@RequestMapping("/review_post.do")
	public String review_post(@RequestParam int review_num, Model model, @RequestParam(required = false, defaultValue = "1") int page,
							@RequestParam(required = false) String search, @RequestParam(required = false) String label) {
		
		Map<String, Object> map = service.reviewPost(review_num);
		ReviewInfoDTO dto = (ReviewInfoDTO)map.get("review");
		List<GenreDTO> g_list = (List<GenreDTO>)map.get("genre");
		
		model.addAttribute("dto", dto);
		model.addAttribute("g_list", g_list);
		model.addAttribute("search", search);
		model.addAttribute("label", label);
		model.addAttribute("page", page);
		
		return "review/review_post";
	}
	
	@ResponseBody
	@RequestMapping("/like_check.do")
	public String like_check(@RequestParam int review_num, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String mem_id = session.getAttribute("session_mem_id").toString();
		
		ReviewLikeDTO dto = new ReviewLikeDTO();
		dto.setLike_review_mem(mem_id);
		dto.setLike_review_num(review_num);
		
		int cnt = service.reviewLikeCheck(dto);
		
		return String.valueOf(cnt);
	}
	
	@ResponseBody
	@RequestMapping("/like_cnt.do")
	public String likeCnt(@RequestParam int review_num) {
		int likeCnt = service.reviewLikeCnt(review_num);
		
		return String.valueOf(likeCnt);
	}
	
	@ResponseBody
	@RequestMapping("/review_like.do")
	public int review_like(@RequestParam int review_num, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String mem_id = session.getAttribute("session_mem_id").toString();
		
		ReviewLikeDTO dto = new ReviewLikeDTO();
		dto.setLike_review_mem(mem_id);
		dto.setLike_review_num(review_num);
		
		return service.reviewLike(dto);
	}
	
	// 리뷰
	@RequestMapping("/review_update_form.do")
	public String review_update_form(@RequestParam int review_num, Model model) {
		
		Map<String, Object> map = service.reviewUpdateForm(review_num);
		ReviewInfoDTO dto = (ReviewInfoDTO)map.get("review");
		List<GenreDTO> g_list = (List<GenreDTO>)map.get("genre");
		
		model.addAttribute("dto", dto);
		model.addAttribute("g_list", g_list);
		
		return "review/review_update_form";
	}
	
	// 리뷰 수정
	@RequestMapping("/review_update.do")
	public String review_update(@ModelAttribute ReviewDTO dto, RedirectAttributes rtts) {
		int res = service.reviewUpdate(dto);
		
		if(res > 0) {
			return "redirect:review_post.do?review_num="+dto.getReview_num();
		}else {
			rtts.addFlashAttribute("msg", "수정 실패");
			return "redirect:review_update_form.do?review_num="+dto.getReview_num();
		}
	}
	
	// 리뷰 삭제
	@RequestMapping("/review_delete.do")
	public String review_delete(@RequestParam int review_num, RedirectAttributes rtts) {
		int res = service.reviewDelete(review_num);
		
		if(res > 0) {
			return "redirect:/";
		}else {
			rtts.addFlashAttribute("msg", "삭제 실패");
			return "redirect:review_post.do?review_num="+review_num;
		}
	}
	
	// 리뷰 검색
	@RequestMapping("/review_search.do")
	public String review_search(@RequestParam(required = false, defaultValue = "1") int page, 
							@RequestParam String label, @RequestParam String search, Model model) {
		int totalRecord = service.reviewSearchCnt(label, search);
		
		Pagination pagination = new Pagination();
		pagination.pageInfo(page, 10, totalRecord);
		
		List<ReviewDTO> list = service.reviewSearch(label, search, pagination);
		
		model.addAttribute("list", list);
		model.addAttribute("label", label);
		model.addAttribute("search", search);
		model.addAttribute("pagination", pagination);
		
		return "review/review_search";
	}
	
	// 리뷰 썼는지 확인
	@ResponseBody
	@RequestMapping("/review_check.do")
	public int review_check(@RequestParam String mov_code, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String mem_id = session.getAttribute("session_mem_id").toString();
		
		return service.reviewCheck(mov_code, mem_id);
	}
}
