package com.movie.model;

import lombok.Data;

@Data
public class CommDTO {
	private int comm_num;
	private int comm_board;
	private String comm_cont;
	private String comm_writer;
	private String comm_regdate;
	private int comm_group;
	private int comm_step;
	private int comm_indent;
	private String mem_img;
	private String board_title;
	private String board_category;
}
