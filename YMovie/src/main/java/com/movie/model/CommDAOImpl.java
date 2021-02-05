package com.movie.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


@Repository
public class CommDAOImpl implements CommDAO{
	
	@Autowired
	private JdbcTemplate temp;
	String sql;
	
	/*
	 * @Override public JsonArray getCommList(int bnum, Pagination pagination) {
	 * final JsonArray jarr = new JsonArray();
	 * 
	 * sql =
	 * "select comm.*, mem.mem_img from mov_comm comm join mov_member mem on comm.comm_writer = mem.mem_id "
	 * + "where comm_board = ? order by comm_group, comm_step limit ?,?";
	 * 
	 * temp.query(sql, new RowMapper<JsonObject>() {
	 * 
	 * @Override public JsonObject mapRow(ResultSet rs, int rowNum) throws
	 * SQLException { JsonObject comment = new JsonObject();
	 * 
	 * comment.addProperty("comm_num", rs.getInt(1));
	 * comment.addProperty("comm_board", rs.getInt(2));
	 * comment.addProperty("comm_cont", rs.getString(3));
	 * comment.addProperty("comm_writer", rs.getString(4));
	 * comment.addProperty("comm_regdate", rs.getString(5));
	 * comment.addProperty("comm_group", rs.getInt(6));
	 * comment.addProperty("comm_step", rs.getInt(7));
	 * comment.addProperty("comm_indent", rs.getInt(8));
	 * comment.addProperty("mem_img", rs.getString(9));
	 * 
	 * jarr.add(comment);
	 * 
	 * return comment; } }, bnum, pagination.getStartNo()-1,
	 * pagination.getRowsize());
	 * 
	 * return jarr; }
	 */
	
	@Override
	public int insertComm(final CommDTO dto) {
		sql = "select max(comm_num) from mov_comm";
		
		final int group = temp.queryForInt(sql);
		
		sql = "insert into mov_comm(comm_board, comm_cont, comm_writer, comm_regdate, comm_group, comm_step, comm_indent)"
				+ " values(?, ?, ?, now(), ?, default, default)";
		
		return temp.update(sql, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, dto.getComm_board());
				ps.setString(2, dto.getComm_cont());
				ps.setString(3, dto.getComm_writer());
				ps.setInt(4, group+1);
			}
		});
	}
	
	@Override
	public int deleteComm(int comm_num) {
		sql = "delete from mov_comm where comm_num = ?";
		
		return temp.update(sql, comm_num);
	}
	
	@Override
	public int deleteBoardComm(int bnum) {
		sql = "delete from mov_comm where comm_board = ?";
		
		return temp.update(sql, bnum);
	}
	
	@Override
	public int updateComm(final int comm_num, final String comm_cont) {
		sql = "update mov_comm set comm_cont = ? where comm_num = ?";
		
		return temp.update(sql, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, comm_cont);
				ps.setInt(2, comm_num);
			}
		});
	}
	
	@Override
	public CommDTO getCommCont(int comm_num) {
		
		sql = "select * from mov_comm where comm_num = ?";
		
		return temp.queryForObject(sql, new RowMapper<CommDTO>() {
			@Override
			public CommDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				CommDTO dto = new CommDTO();
			
				dto.setComm_num(rs.getInt(1));
				dto.setComm_board(rs.getInt(2));
				dto.setComm_cont(rs.getString(3));
				dto.setComm_writer(rs.getString(4));
				dto.setComm_regdate(rs.getString(5));
				dto.setComm_group(rs.getInt(6));
				dto.setComm_step(rs.getInt(7));
				dto.setComm_indent(rs.getInt(8));
				
				return dto;
			}
		}, comm_num);
	}
	
	@Override
	public int getMaxStep(int comm_group) {
		
		sql = "select max(comm_step) from mov_comm where comm_group = ?";
		
		return temp.queryForInt(sql, comm_group);
	}
	
	@Override
	public int insertCommReply(final CommDTO dto) {
		sql = "insert into mov_comm(comm_board, comm_cont, comm_writer, comm_regdate, comm_group, comm_step, comm_indent)"
				+ "values(?, ?, ?, now(), ?, ?, ?)";
		
		return temp.update(sql, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, dto.getComm_board());
				ps.setString(2, dto.getComm_cont());
				ps.setString(3, dto.getComm_writer());
				ps.setInt(4, dto.getComm_group());
				ps.setInt(5, dto.getComm_step()+1);
				ps.setInt(6, dto.getComm_indent()+1);
			}
		});
	}
	
	@Override
	public int getCommCnt(int bnum) {
		sql = "select count(*) from mov_comm where comm_board = ?";
		
		return temp.queryForInt(sql, bnum);
	}
	
	@Override
	public int getMyCommCnt(String mem_id) {
		sql = "select count(*) from mov_comm where comm_writer = ?";
		
		return temp.queryForInt(sql, mem_id);
	}
	
	@Override
	public List<CommDTO> getMyCommList(String mem_id, Pagination pagination) {
		List<CommDTO> list = null;
		
		sql = "select comm.*, b.board_title, b.board_category from mov_comm comm join mov_board b on comm.comm_board = b.board_num where comm_writer = ? order by comm_regdate desc limit ?,?";
		
		return list = temp.query(sql, new RowMapper<CommDTO>() {
			@Override
			public CommDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				CommDTO dto = new CommDTO();
				BoardDTO b_dto = new BoardDTO();
				
				dto.setComm_num(rs.getInt(1));
				dto.setComm_board(rs.getInt(2));
				dto.setComm_cont(rs.getString(3));
				dto.setComm_writer(rs.getString(4));
				dto.setComm_regdate(rs.getString(5));
				dto.setComm_group(rs.getInt(6));
				dto.setComm_step(rs.getInt(7));
				dto.setComm_indent(rs.getInt(8));
				
				b_dto.setBoard_title(rs.getString(9));
				b_dto.setBoard_category(rs.getString(10));
				
				dto.setBoard(b_dto);
				
				return dto;
			}
		}, mem_id, pagination.getStartNo()-1, pagination.getRowsize());
	}
}
