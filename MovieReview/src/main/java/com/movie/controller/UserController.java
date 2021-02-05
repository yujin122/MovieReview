package com.movie.controller;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.movie.model.MemberDTO;
import com.movie.service.UserService;

@Controller
public class UserController {
	@Autowired UserService service;
	@Autowired private JavaMailSenderImpl mailSender;
	
	// 로그인페이지
	@RequestMapping("/login_main.do")
	public String login_main() {
		return "user/login";
	}
	
	// 로그인
	@RequestMapping("/login.do")
	public String login(@RequestParam String id, @RequestParam String pwd, RedirectAttributes redirect, HttpServletRequest request) {
		String msg = service.login(id, pwd);
		
		if(msg.equals("1")) {
			HttpSession session = request.getSession();
			session.setAttribute("session_mem_id", id);
			return "redirect:/";
		}else {
			redirect.addFlashAttribute("msg", msg);
			return "redirect:login_main.do"; 
		}
	}
	
	// 로그아웃
	@RequestMapping("/logout.do")
	public String logout(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		session.invalidate();
		
		return "redirect:/";
	}
	
	// 회원가입페이지
	@RequestMapping("/join_main.do")
	public String join_main() {
		return "user/join";
	}
	// 아이디 중복
	@ResponseBody
	@RequestMapping("/check_id.do")
	public int check_id(@RequestParam String mem_id) {
		return service.checkId(mem_id);
	}
	// 이메일 중복
	@ResponseBody
	@RequestMapping("/email_check.do")
	public int check_email(@RequestParam String mem_email) {
		return service.checkEmail(mem_email);
	}
	// 회원가입
	@RequestMapping("/join.do")
	public String join(@ModelAttribute MemberDTO dto, HttpServletRequest request, RedirectAttributes rtts) throws MessagingException, UnsupportedEncodingException {
		String mem_phone = request.getParameter("phone1").trim() + "-" + request.getParameter("phone2").trim() + "-" + request.getParameter("phone3").trim();
		String mem_addr = request.getParameter("zip").trim() + "/" + request.getParameter("addr1").trim() + "/" + request.getParameter("addr2").trim();
		
		dto.setMem_phone(mem_phone);
		dto.setMem_addr(mem_addr);
		
		String key = new TempKey().getKey(50, false);
		dto.setAuthkey(key);
		
		service.join(dto);
		
		MailHandler sendMail = new MailHandler(mailSender);
		sendMail.setSubject("[ymovie 이메일 인증]");
		sendMail.setText(
				"<h1>메일 인증</h1>" + 
				"<a href = 'http://localhost:8787/controller/emailConfirm.do?mem_email=" + dto.getMem_email() +
				"&key=" + key +
				"' target='_blenk'>이메일 인증 확인</a>");
		sendMail.setFrom("ymovie@gmail.com", "ymovie");
		sendMail.setTo(dto.getMem_email());
		sendMail.send();
		
		rtts.addFlashAttribute("msg", "이메일 인증 후 로그인 가능합니다.");
		return "redirect:login_main.do";
	}
	
	// 이메일 인증 확인
	@RequestMapping("/emailConfirm.do")
	public String emailConfirm(@RequestParam String mem_email, @RequestParam String key, RedirectAttributes rtts) {
		int res = service.emailConfirm(mem_email, key);
		
		if(res > 0) {
			rtts.addFlashAttribute("msg", "이메일 인증 성공");
			return "redirect:login_main.do";
		}else {
			rtts.addFlashAttribute("msg", "이메일 인증 실패");
			return "redirect:login_main.do";
		}
	}
	
	// 아이디 비번 찾기
	@RequestMapping("/search_idpwd_main.do")
	public String search_idpwd_main() {
		return "user/idpwd_search";
	}
	
	// 아이디 찾기
	@RequestMapping("/id_search.do")
	public String id_search(@RequestParam String name, @RequestParam String email, Model model) {
		String mem_id = service.idSearch(name, email);
		
		if(mem_id != null) {
			model.addAttribute("mem_id", mem_id);
			return "user/id_success";
		}else {
			model.addAttribute("msg", "회원정보가 존재하지 않습니다.");
			return "user/idpwd_search";
		}
	}
	// 비밀번호 찾기
	@RequestMapping("/pwd_search.do")
	public String pwd_search(@RequestParam String name, @RequestParam String email, @RequestParam String id, Model model) {
		String mem_pwd = service.pwdSearch(id, name, email);
		
		if(mem_pwd != null) {
			model.addAttribute("mem_pwd", mem_pwd);
			return "user/pwd_success";
		}else {
			model.addAttribute("msg", "회원정보가 존재하지 않습니다.");
			return "user/idpwd_search";
		}
	}
}
