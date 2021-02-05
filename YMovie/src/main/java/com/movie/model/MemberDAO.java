package com.movie.model;

public interface MemberDAO {
	public MemberDTO getMemberCont(String mem_id);			// 회원 정보
	public int checkId(String mem_id);
	public String getPwd(String mem_id);
	public int id_check(String mem_id);						// 중복 아이디
	public int email_check(String mem_email);				// 중복 이메일
	public String id_search(String name, String email);		// 아이디 찾기
	public String pwd_search(String id, String name, String email); 	// 비밀번호 찾기
}
