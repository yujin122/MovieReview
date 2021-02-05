package com.movie.service;

import com.movie.model.MemberDTO;

public interface UserService {
	
	public MemberDTO Cont(String mem_id);			// 회원 정보
	public int login(String mem_id, String mem_pwd);		// 로그인
	public int id_check(String mem_id);						// 중복 아이디
	public int email_check(String mem_email);				// 중복 이메일
	public String id_search(String name, String email);		// 아이디 찾기
	public String pwd_search(String id, String name, String email); 	// 비밀번호 찾기
}
