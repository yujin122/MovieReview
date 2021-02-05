package com.movie.model;

import lombok.Data;

@Data
public class ReviewInfoDTO {
	private int review_num;
	private String review_mov;
	private String review_cont;
	private String review_regdate;
	private String review_writer;
	private int review_hit;
	private String review_rating;
	
	private String mem_img;
	
	private String mov_code;
	private String mov_title;
	private String mov_director;
	private String mov_opendt;
	private String mov_actors;
	private String mov_nations;
	private String mov_showtm;
	private String mov_avgrating;
	private String mov_prdtyear;
	private String mov_poster;
}
