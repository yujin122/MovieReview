package com.movie.model;

import lombok.Data;

@Data
public class ReviewDTO {
	private int review_num;
	private String review_mov;
	private String review_cont;
	private String review_regdate;
	private String review_writer;
	private int review_hit;
	private String review_rating;
	
	private String mov_title;
}
