package com.ict.springboot.basic.handler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class OneMethodParamController {

	@GetMapping("/controller/oneclassparam/crud.do")
	public String param(Model model,@RequestParam String crud) {
		//모델 계열(Model)에 데이타 저장
		switch(crud.toUpperCase()) {
			case "LIST":model.addAttribute("message", "READ ALL REQUEST");break;
			case "EDIT":model.addAttribute("message", "UPDATE REQUEST");break;
			case "VIEW":model.addAttribute("message", "READ ONE REQUEST");break;
			default:model.addAttribute("message", "DELETE REQUEST");break;
		}
		//뷰 정보 반환(html 페이지 응답)
		return "controller02/controller";
	}
}
