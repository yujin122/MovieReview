package com.movie.model;

import lombok.Data;

@Data
public class BoardDTO {
	private int board_num;
	private String board_category;
	private String board_title;
	private String board_cont;
	private String board_regdate;
	private String board_writer;
	private int board_hit;
	private int board_comm_cnt;
	private int board_group;
	private int board_step;
	private int board_indent;
	private MemberDTO mem;
}
