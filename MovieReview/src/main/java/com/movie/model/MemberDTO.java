package com.movie.model;

import lombok.Data;

@Data
public class MemberDTO {
	private String mem_name;
	private String mem_id;
	private String mem_pwd;
	private String mem_birth;
	private String mem_gender;
	private String mem_addr;
	private String mem_phone;
	private String mem_email;
	private String mem_img;
	private String authkey;
	private String authstatus;
}
