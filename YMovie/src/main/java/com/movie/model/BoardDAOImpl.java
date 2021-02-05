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
public class BoardDAOImpl implements BoardDAO{
	
	@Autowired
	private JdbcTemplate temp;
	String sql;
	
	@Override
	public List<BoardDTO> getBoardList(Pagination pagination) {
		List<BoardDTO> list = null;
		
		sql = "select * from mov_board order by board_group desc, board_step limit ?, ?";
		
		return list = temp.query(sql, new RowMapper<BoardDTO>() {
			@Override
			public BoardDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				BoardDTO dto = new BoardDTO();
				
				dto.setBoard_num(rs.getInt(1));
				dto.setBoard_category(rs.getString(2));
				dto.setBoard_title(rs.getString(3));
				dto.setBoard_cont(rs.getString(4));
				dto.setBoard_regdate(rs.getString(5));
				dto.setBoard_writer(rs.getString(6));
				dto.setBoard_hit(rs.getInt(7));
				dto.setBoard_comm_cnt(rs.getInt(8));
				dto.setBoard_group(rs.getInt(9));
				dto.setBoard_step(rs.getInt(10));
				dto.setBoard_indent(rs.getInt(11));
				
				return dto;
			}
		},pagination.getStartNo()-1, pagination.getRowsize());
	}
	
	@Override
	public BoardDTO getBoardCont(int bnum) {
		sql = "select b.*, mem.mem_img from mov_board b join mov_member mem on b.board_writer = mem.mem_id where board_num = ?";
		
		return temp.queryForObject(sql, new RowMapper<BoardDTO>() {
			@Override
			public BoardDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				BoardDTO dto = new BoardDTO();
				MemberDTO mem_dto = new MemberDTO();
				
				dto.setBoard_num(rs.getInt(1));
				dto.setBoard_category(rs.getString(2));
				dto.setBoard_title(rs.getString(3));
				dto.setBoard_cont(rs.getString(4));
				dto.setBoard_regdate(rs.getString(5));
				dto.setBoard_writer(rs.getString(6));
				dto.setBoard_hit(rs.getInt(7));
				dto.setBoard_comm_cnt(rs.getInt(8));
				dto.setBoard_group(rs.getInt(9));
				dto.setBoard_step(rs.getInt(10));
				dto.setBoard_indent(rs.getInt(11));
				mem_dto.setMem_img(rs.getString(12));
				dto.setMem(mem_dto);
				
				return dto;
			}
		}, bnum);
	}
	
	@Override
	public void setHit(final int bnum) {
		sql = "update mov_board set board_hit = board_hit+1 where board_num = ?";
		
		temp.update(sql, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, bnum);
			}
		});
	}
	
	@Override
	public void setCommCount(int bnum) {
		sql = "update mov_board set board_comm_cnt = board_comm_cnt+1 where board_num = ?";
		
		temp.update(sql, bnum);
	}
	
	@Override
	public void setdelCommCnt(final int bnum, final int cnt) {
		sql = "update mov_board set board_comm_cnt = board_comm_cnt-? where board_num = ?";
		
		temp.update(sql, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, cnt);
				ps.setInt(2, bnum);
			}
		});
	}
	
	@Override
	public int getBoardCnt() {
		sql = "select count(*) from mov_board";
		
		return temp.queryForInt(sql);
	}
	
	@Override
	public int insertBoard(final BoardDTO dto) {
		
		final int group = temp.queryForInt("select max(board_num) from mov_board");
		
		sql = "insert into mov_board(board_category, board_title, board_cont, board_regdate, board_writer, board_hit, board_comm_cnt, board_group, board_step, board_indent)"
				+ "values(?, ?, ?, now(), ?, default, default, ?, default, default)";
		
		return temp.update(sql, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, dto.getBoard_category());
				ps.setString(2, dto.getBoard_title());
				ps.setString(3, dto.getBoard_cont());
				ps.setString(4, dto.getBoard_writer());
				ps.setInt(5, group+1);
			}
		});
	}
	
	@Override
	public int updateBoard(final BoardDTO dto) {
		sql = "update mov_board set board_category = ?, board_title = ?, board_cont = ? where board_num = ?";
		
		return temp.update(sql, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, dto.getBoard_category());
				ps.setString(2, dto.getBoard_title());
				ps.setString(3, dto.getBoard_cont());
				ps.setInt(4, dto.getBoard_num());
			}
		});
	}
	
	@Override
	public int deleteBoard(int bnum) {
		sql = "delete from mov_board where board_num = ?";
		
		return temp.update(sql, bnum);
	}
	
	@Override
	public int insertBoardReply(final BoardDTO dto) {
		sql = "insert into mov_board(board_category, board_title, board_cont, board_regdate, board_writer, board_hit, board_comm_cnt, board_group, board_step, board_indent)"
				+ "values(?, ?, ?, now(), ?, default, default, ?, ?, ?)";
		
		return temp.update(sql, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, dto.getBoard_category());
				ps.setString(2, dto.getBoard_title());
				ps.setString(3, dto.getBoard_cont());
				ps.setString(4, dto.getBoard_writer());
				ps.setInt(5, dto.getBoard_group());
				ps.setInt(6, dto.getBoard_step()+1);
				ps.setInt(7, dto.getBoard_indent()+1);
			}
		});
	}
	
	@Override
	public void updateReply(final BoardDTO dto) {
		sql = "update mov_board set board_step = board_step+1 where board_step > ? and board_group = ?";
		
		temp.update(sql, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, dto.getBoard_step());
				ps.setInt(2, dto.getBoard_group());
			}
		});
	}
	
	@Override
	public List<BoardDTO> searchBoardList(String label, String keyword, Pagination pagination) {
		List<BoardDTO> list = null;
		
		if(label.equals("category")) {
			sql = "select * from mov_board where board_category like ? order by board_group desc, board_step limit ?,?";
		}else if(label.equals("cont")) {
			sql = "select * from mov_board where board_cont like ? order by board_group desc, board_step limit ?,?";
		}else if(label.equals("writer")) {
			sql = "select * from mov_board where board_writer like ? order by board_group desc, board_step limit ?,?";
		}
		
		return list = temp.query(sql, new RowMapper<BoardDTO>() {
			@Override
			public BoardDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				BoardDTO dto = new BoardDTO();
				
				dto.setBoard_num(rs.getInt(1));
				dto.setBoard_category(rs.getString(2));
				dto.setBoard_title(rs.getString(3));
				dto.setBoard_cont(rs.getString(4));
				dto.setBoard_regdate(rs.getString(5));
				dto.setBoard_writer(rs.getString(6));
				dto.setBoard_hit(rs.getInt(7));
				dto.setBoard_comm_cnt(rs.getInt(8));
				dto.setBoard_group(rs.getInt(9));
				dto.setBoard_step(rs.getInt(10));
				dto.setBoard_indent(rs.getInt(11));
				
				return dto;
			}
		},"%"+keyword+"%", pagination.getStartNo()-1, pagination.getRowsize());
	}
	
	@Override
	public int searchBoardCnt(String label, String keyword) {
		
		keyword = "%"+keyword+"%";
		if(label.equals("category")) {
			sql = "select count(*) from mov_board where board_category like ?";
		}else if(label.equals("cont")) {
			sql = "select count(*) from mov_board where board_cont like ?";
		}else if(label.equals("writer")) {
			sql = "select count(*) from mov_board where board_writer like ?";
		}
		
		return temp.queryForInt(sql, keyword);
	}
	
	@Override
	public int getMyBoardCnt(String mem_id) {
		sql = "select count(*) from mov_board where board_writer = ?";
		
		return temp.queryForInt(sql, mem_id);
	}
	
	@Override
	public List<BoardDTO> getMyBoardList(String mem_id, Pagination pagination) {
		List<BoardDTO> list = null;
		
		sql = "select * from mov_board where board_writer = ? order by board_regdate desc limit ?,?";
		
		return list = temp.query(sql, new RowMapper<BoardDTO>() {
			@Override
			public BoardDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				BoardDTO dto = new BoardDTO();
				
				dto.setBoard_num(rs.getInt(1));
				dto.setBoard_category(rs.getString(2));
				dto.setBoard_title(rs.getString(3));
				dto.setBoard_cont(rs.getString(4));
				dto.setBoard_regdate(rs.getString(5));
				dto.setBoard_writer(rs.getString(6));
				dto.setBoard_hit(rs.getInt(7));
				dto.setBoard_comm_cnt(rs.getInt(8));
				dto.setBoard_group(rs.getInt(9));
				dto.setBoard_step(rs.getInt(10));
				dto.setBoard_indent(rs.getInt(11));
				
				return dto;
			}
		}, mem_id, pagination.getStartNo()-1, pagination.getRowsize());
	}
}
