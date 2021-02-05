package com.movie.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.movie.service.CommService;

@Controller
public class CommController {
	
	@Autowired private CommService service;
	
	
}
