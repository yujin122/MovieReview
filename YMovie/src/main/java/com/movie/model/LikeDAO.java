package com.movie.model;

public interface LikeDAO {
	public int like_check(String mov_num, String mem_id);
	public int like_add(String mov_num, String mem_id);
	public int like_del(String mov_num, String mem_id);
	
}
