package com.movie.model;

import java.util.List;

import com.movie.service.Pagination;

public interface LikeDAO {
	public int like_check(String mov_num, String mem_id);
	public int like_add(String mov_num, String mem_id);
	public int like_del(String mov_num, String mem_id);
	public List<MovieDTO> like_list(String mem_id);	
	public int getLikeCnt(String mem_id);
	public List<MovieDTO> likeAllList(String mem_id, Pagination pagination);
}
