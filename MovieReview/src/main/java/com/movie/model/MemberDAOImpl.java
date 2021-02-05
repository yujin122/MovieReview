package com.movie.model;

import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDAOImpl implements MemberDAO {
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Override
	public MemberDTO getMemberCont(String mem_id) {
		return sqlSession.selectOne("getMemberCont", mem_id);
	}
	
	@Override
	public int id_check(String mem_id) {
		return sqlSession.selectOne("idCheck", mem_id);
	}
	
	@Override
	public int email_check(String mem_email) {
		return sqlSession.selectOne("emailCheck", mem_email);
	}
	
	@Override
	public String id_search(String name, String email) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", name);
		map.put("email", email);
		
		return sqlSession.selectOne("idSearch", map);
	}
	
	@Override
	public String pwd_search(String id, String name, String email) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("name", name);
		map.put("email", email);
		
		return sqlSession.selectOne("pwdSearch", map);
	}
	
	@Override
	public String login_pwd(String id) {
		return sqlSession.selectOne("loginPwd", id);
	}
	
	@Override
	public int join(MemberDTO dto) {
		return sqlSession.insert("join", dto);
	}
	
	@Override
	public int emailConfirm(String mem_email, String key) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("mem_email", mem_email);
		map.put("authkey", key);
		
		return sqlSession.selectOne("emailConfirm", map);
	}
	
	@Override
	public void updateStatus(String mem_email) {
		sqlSession.update("updateStatus", mem_email);
	}
	
	@Override
	public int emailAuthCheck(String mem_id) {
		return sqlSession.selectOne("emailAuthCheck", mem_id);
	}
	
	@Override
	public int updateMember(MemberDTO dto) {
		return sqlSession.update("updateMember", dto);
	}
}
