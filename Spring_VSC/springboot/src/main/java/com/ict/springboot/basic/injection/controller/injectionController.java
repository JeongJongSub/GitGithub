package com.ict.springboot.basic.injection.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ict.springboot.basic.injection.model.Person;

@Controller
public class injectionController {
	
	//생성자 인젝션
	private Person person;

	public injectionController(Person person) {	
		System.out.println("injectionController의 인자 생성자:"+person);
		//생성자를 통해서 주입받은 객체로 필드 초기화
		this.person = person;
	}
	@PostMapping("/injection/injection.do")
	//폼의 파라미터명이 맵의 키,입력값이 맵의 값이 된다
	public String execute(@RequestParam Map map,Model model) {
		
		//사용자 입력값으로 person를 다시 설정
		person.setAddr(map.get("addr").toString());
		person.setAge(Integer.parseInt(map.get("age").toString()));
		person.setWriter(map.get("writer").toString());
		//데이타 저장
		model.addAttribute("personInfo", person);
		//뷰정보 반환
		return "injection04/injection";
	}
	
	
}
