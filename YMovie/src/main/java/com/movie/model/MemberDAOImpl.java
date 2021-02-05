package com.movie.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDAOImpl implements MemberDAO {
	
	@Autowired
	private JdbcTemplate temp;
	String sql;
	
	@Override
	public MemberDTO getMemberCont(String mem_id) {
		
		sql = "select * from mov_member where mem_id = ?";
		
		return temp.queryForObject(sql, new RowMapper<MemberDTO>() {
			@Override
			public MemberDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				MemberDTO dto = new MemberDTO();
				
				dto.setMem_num(rs.getInt(1));
				dto.setMem_name(rs.getString(2));
				dto.setMem_id(rs.getString(3));
				dto.setMem_pwd(rs.getString(4));
				dto.setMem_birth(rs.getString(5));
				dto.setMem_gender(rs.getString(6));
				dto.setMem_addr(rs.getString(7));
				dto.setMem_phone(rs.getString(8));
				dto.setMem_email(rs.getString(9));
				dto.setMem_img(rs.getString(10));
				
				return dto;
			}
		}, mem_id);
	}
	
	@Override
	public int checkId(String mem_id) {
		sql = "select count(*) from mov_member where mem_id = ?";
		
		return temp.queryForInt(sql, mem_id);
	}
	
	@Override
	public String getPwd(String mem_id) {
		sql = "select mem_pwd from mov_member where mem_id = ?";
		
		return temp.queryForObject(sql, String.class, mem_id);
	}
	
	@Override
	public int id_check(String mem_id) {
		sql = "select count(mem_id) from mov_member where mem_id = ?";
		
		return temp.queryForInt(sql, mem_id);
	}
	
	@Override
	public int email_check(String mem_email) {
		sql = "select count(*) from mov_member where mem_email = ?";
		
		return temp.queryForInt(sql, mem_email);
	}
	
	@Override
	public String id_search(String name, String email) {
		sql = "select mem_id from mov_member where mem_name = ? and mem_email = ?";
		
		try {
			return temp.queryForObject(sql, new Object[] {name, email}, String.class);
		}catch(DataAccessException e) {
			return null;
		}
		
	}
	
	@Override
	public String pwd_search(String id, String name, String email) {
		sql = "select mem_pwd from mov_member where mem_name = ? and mem_email = ? and mem_id = ?";
		
		try {
			return temp.queryForObject(sql, new Object[] {name, email, id}, String.class);
		}catch(DataAccessException e) {
			return null;
		}
	}

}
