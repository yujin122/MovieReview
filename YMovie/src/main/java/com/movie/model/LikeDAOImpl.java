package com.movie.model;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

@Repository
public class LikeDAOImpl implements LikeDAO{
	
	@Autowired
	private JdbcTemplate temp;
	String sql;
	
	@Override
	public int like_check(String mov_num, String mem_id) {
		
		sql = "select count(*) from mov_like where like_mov_fk = ? and like_mem_fk = ?";
		
		return temp.queryForInt(sql, new Object[] {mov_num, mem_id});
	}
	
	@Override
	public int like_add(final String mov_num, final String mem_id) {
		sql = "insert into mov_like values(?, ?)";
		
		return temp.update(sql, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, mov_num);
				ps.setString(2, mem_id);
			}
		});
	}
	
	@Override
	public int like_del(final String mov_num, final String mem_id) {
		sql = "delete from mov_like where like_mov_fk = ? and like_mem_fk = ?";
		
		return temp.update(sql, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, mov_num);
				ps.setString(2, mem_id);
			}
		});
	}
}
