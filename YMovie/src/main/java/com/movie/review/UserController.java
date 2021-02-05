package com.movie.review;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.movie.service.UserService;

@Controller
public class UserController {
	
	@Autowired UserService service;
	
	@RequestMapping("/login_main.do")
	public String login_main() {
		return "user/login";
	}
	
	@RequestMapping("/login.do")
	public String login(@RequestParam("id") String id, @RequestParam("pwd") String pwd, RedirectAttributes rtts, HttpServletRequest request) {
		
		int res = service.login(id, pwd);
		
		String msg = "";
		
		if(res == 1) {
			HttpSession session = request.getSession();
			
			session.setAttribute("session_mem_id", id);
			
			return "redirect:/";
		}else if(res == -1) {
			msg = "비밀번호가 틀렸습니다.";
		}else if(res == 2) {
			msg = "아이디가 존재하지 않습니다.";
		}
		System.out.println(res);
		rtts.addFlashAttribute("msg", msg);
		
		return "redirect:login_main.do"; 
	}
}
