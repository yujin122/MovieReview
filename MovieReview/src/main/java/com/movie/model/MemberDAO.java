package com.movie.model;

public interface MemberDAO {
	public MemberDTO getMemberCont(String mem_id);			// 회원 정보
	public int id_check(String mem_id);						// 중복 아이디
	public int email_check(String mem_email);				// 중복 이메일
	public String id_search(String name, String email);		// 아이디 찾기
	public String pwd_search(String id, String name, String email); 	// 비밀번호 찾기
	public String login_pwd(String id);
	public int join(MemberDTO dto);
	public int emailConfirm(String mem_email, String key);
	public void updateStatus(String mem_email);
	public int emailAuthCheck(String mem_id);
	public int updateMember(MemberDTO dto);
}
