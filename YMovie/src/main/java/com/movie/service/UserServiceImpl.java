package com.movie.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movie.model.MemberDAO;
import com.movie.model.MemberDTO;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired MemberDAO m_dao;
	
	@Override
	public int login(String mem_id, String mem_pwd) {
		
		if(m_dao.checkId(mem_id) > 0) {
			String pwd = m_dao.getPwd(mem_id);
			
			if(pwd.equals(mem_pwd)) {
				return 1;
			}else {
				return -1;
			}
		}else {
			return 2;
		}
	}
	
	@Override
	public MemberDTO Cont(String mem_id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int email_check(String mem_email) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int id_check(String mem_id) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public String id_search(String name, String email) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String pwd_search(String id, String name, String email) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
