package com.movie.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import com.movie.model.MemberDAO;
import com.movie.model.MemberDTO;

@Service
public class UserServiceImpl implements UserService{
	@Autowired private MemberDAO mem_dao;
	
	@Override
	public String login(String id, String pwd) {
		String msg = "";
		
		int id_check = mem_dao.id_check(id);
		
		if(id_check > 0) {
			String getPwd = mem_dao.login_pwd(id);
			
			if(getPwd.equals(pwd)) {
				int email_check = mem_dao.emailAuthCheck(id);
				
				if(email_check > 0) {
					msg = "1";
								
				}else {
					msg = "이메일 인증을 해주세요.";
				}
			}else {
				msg = "비밀번호가 틀렸습니다.";
			}
		}else {
			msg = "아이디가 존재하지 않습니다.";
		}
		
		return msg;
	}
	
	@Override
	public int checkEmail(String mem_email) {
		return mem_dao.email_check(mem_email);
	}
	
	@Override
	public int checkId(String mem_id) {
		return mem_dao.id_check(mem_id);
	}
	
	@Override
	public void join(MemberDTO dto) {
		mem_dao.join(dto);
	}
	
	@Override
	public int emailConfirm(String mem_email, String key) {
		int res = mem_dao.emailConfirm(mem_email, key);
		
		if(res > 0) {
			mem_dao.updateStatus(mem_email);
			res = 1;
		}else {
			res = 0;
		}
		return res;
	}
	
	@Override
	public String idSearch(String name, String email) {
		return mem_dao.id_search(name, email);
	}
	
	@Override
	public String pwdSearch(String id, String name, String email) {
		return mem_dao.pwd_search(id, name, email);
	}
}
