package com.movie.service;

import com.movie.model.MemberDTO;

public interface UserService {
	public String login(String id, String pwd);
	public int checkId(String mem_id);
	public int checkEmail(String mem_email);
	public void join(MemberDTO dto);
	public int emailConfirm(String mem_email, String key);
	public String idSearch(String name, String email);
	public String pwdSearch(String id,String name, String email);
	
}
