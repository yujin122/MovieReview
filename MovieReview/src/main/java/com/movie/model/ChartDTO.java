package com.movie.model;

import lombok.Data;

@Data
public class ChartDTO {
	private int genre_num_fk;
	private String mov_genre_name;
	private float rating;
	private int cnt;
}
