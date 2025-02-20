package com.ict.springboot.basic.annotation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@Controller
public class RequestHeaderController {
	
	@GetMapping("/annotation/requestHeader.do")
	public String exec(Model model,@RequestHeader(name = "user-agent",required = false,defaultValue = "요청 헤더명이 존재하지 않아요") String userAgent) {
		model.addAttribute("userAgent", userAgent);
		//뷰정보 반환
		return "annotation05/annotation";
	}
}
