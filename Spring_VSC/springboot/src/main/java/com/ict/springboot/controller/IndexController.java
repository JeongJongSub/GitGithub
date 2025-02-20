package com.ict.springboot.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

//[일반 자바 클래스 형태 즉 POJO(Plain Old Java Object)]

@Controller//컴파일러에게 "아래 클래스는 사용자 요청을 처리하는 클래스야" 라고 알려주는 역할
public class IndexController {//컨트롤러 클래스

	
	//GET방식의 루트 요청(즉 시작 페이지 요청)을 처리하는 메소드	
	@GetMapping("/")
	public String index(Model model) {//컨트롤러 메소드
		model.addAttribute("message", "Hello Spring Boot!!!");	
		
		//기본적으로 src/main/resources의 templates에서 index.html을 찾는다
        //반드시 /는 생략 한다. 
		model.addAttribute("checked",true);			
		
		return "index";//.html이 생략된 파일명
	}
	
	@GetMapping("/template")
	public String template() {
		return "template";//templates디렉토리의 template.html 서비스
	}
	
	//타임리프 기초 UI로 이동
	@GetMapping("/thymeleaf.do")
	public String thymeleaf(HttpSession session,Model model) {
		//타임리프 템플릿에서 출력할 데이타들 저장
		session.setAttribute("username", "ICT");
		model.addAttribute("dates",new Date());
		model.addAttribute("temporals",LocalDateTime.now());
		model.addAttribute("escapeXml","<h1>스프링부트 & 타임리프</h1>");
		//뷰 반환
		//템플릿 파일은 src/main/resources/templates 아래에 위치 시킨다
		//thymeleaf01 디렉토리 생성후 thymeleaf.html 생성 
		return "thymeleaf01/thymeleaf";
	}
	//컨트롤러 기초 UI로 이동
	@GetMapping("/controller.do")
	public String controller() {
		return "controller02/controller";
	}
	//컨트롤러 메소드의 반환 타입 UI로 이동
	@GetMapping("/returnType.do")
	public String returnType() {
		return "returnType03/returnType";
	}
	
	@GetMapping("/injection.do")
	public String injection() {
		return "injection04/injection";
	}
	
	@GetMapping("/annotation.do")
	public String annotation() {
		return "annotation05/annotation";
	}
	
	@GetMapping("/properties.do")
	public String properties() {
		return "properties06/properties";
	}
	
	@GetMapping("/database.do")
	public String database() {
		return "database07/database";
	}
	
	@GetMapping("/validation.do")
	public String validation() {
		return "validation08/validation";
	}
	
	
	
}
