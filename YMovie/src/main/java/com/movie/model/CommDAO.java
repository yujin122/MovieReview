package com.movie.model;

import java.util.List;

public interface CommDAO {
	
	/* public JsonArray getCommList(int bnum, Pagination pagination); */		// 댓글 리스트
	public CommDTO getCommCont(int comm_num);							// 댓글 상세정보
	public int insertComm(CommDTO dto);									// 댓글 등록
	public int deleteComm(int comm_num);								// 댓글 삭제
	public int deleteBoardComm(int bnum);								// 게시글 삭제 시 댓글 삭제
	public int updateComm(int comm_num, String comm_cont);				// 댓글 수정
	public int insertCommReply(CommDTO dto);							// 대댓글
	public int getMaxStep(int comm_group);								// 최고 step 값
	public int getCommCnt(int bnum);									// 댓글수
	public int getMyCommCnt(String mem_id);								// 내가 쓴 댓글 수
	public List<CommDTO> getMyCommList(String mem_id, Pagination pagination); 	// 내가 쓴 댓글 리스트
}
