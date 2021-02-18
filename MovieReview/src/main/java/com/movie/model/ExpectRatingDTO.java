package com.movie.model;

import lombok.Data;

@Data
public class ExpectRatingDTO {
	private int genre_num_fk;
	private float review_rating_avg;
	private int review_cnt;
}
