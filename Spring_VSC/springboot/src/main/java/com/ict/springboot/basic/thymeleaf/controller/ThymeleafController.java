package com.ict.springboot.basic.thymeleaf.controller;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ict.springboot.basic.thymeleaf.model.AuthUser;

import jakarta.servlet.http.HttpSession;

@Controller
public class ThymeleafController {

	@GetMapping("/variable.do")
	public String thymeleaf(Model model) {
		//템플릿 파일(.html)에 출력할 데이타 저장
		model.addAttribute("variable","변수 표현식 입니다");
		//뷰 정보 반환
		return "thymeleaf01/thymeleaf";
	}////thymeleaf()
	
	@GetMapping("/selections.do")
	public String selections(Model model) {		
		AuthUser user = AuthUser.builder()
				.username("ICT")
				.password("ICT1234")
				.build();
		//데이타 저장
		model.addAttribute("user", user);		
		//뷰 정보 반환
		return "thymeleaf01/thymeleaf";
	}////selections()
	
	@GetMapping("/linkNoParam.do")
	public String linkNoParam(Model model) {
		//데이타 저장
		model.addAttribute("noParameter", "링크 표현식입니다(파라미터 미 전달)");
		//뷰 정보 반환
		return "thymeleaf01/thymeleaf";
	}/////////////linkNoParam
	@GetMapping("/linkParam.do")
	public String linkParam(Model model,@RequestParam String template,@RequestParam String framework) {
		//데이타 저장
		model.addAttribute("parameter", String.format("링크 표현식입니다:템플릿 엔진-%s,웹 프레임워크:%s",template,framework));
		//뷰 정보 반환
		return "thymeleaf01/thymeleaf";
	}/////////////linkParam
	@GetMapping("/pipeline.do")
	public String pipeline(Model model) {
		//데이타 저장
		model.addAttribute("render", "Server Side Rendering");
		//뷰 정보 반환
		return "thymeleaf01/thymeleaf";
	}/////////////pipeline
	
	
	@GetMapping("/ifunless.do")
	public String ifunless(Model model,@RequestParam String username,@RequestParam int age) {
		//데이타 저장
		model.addAttribute("username", username);
		model.addAttribute("age", age);
		
		//뷰 정보 반환
		return "thymeleaf01/thymeleaf";
	}/////////////ifunless
	
	@PostMapping("/switch.do")
	public String switchCase(Model model,@RequestParam Map scores) {		
		//데이타 저장
		model.addAllAttributes(scores);		
		//뷰 정보 반환
		return "thymeleaf01/thymeleaf";
	}/////////////switch
	@GetMapping("/each.do")
	public String each(Model model) {	
		
		AuthUser user1= AuthUser
				.builder()
				.username("KIM")
				.password("KIM1234")
				.name("김길동").regidate(LocalDateTime.now()).build();
		AuthUser user2= AuthUser
				.builder()
				.username("LEE")
				.password("LEE1234")
				.name("이길동").regidate(LocalDateTime.now()).build();
		
		List<AuthUser> users = Arrays.asList(user1,user2);
		
		//데이타 저장
		model.addAttribute("users", users);
		//뷰 정보 반환
		return "thymeleaf01/thymeleaf";
	}/////////////switch
	
	
	@GetMapping("/beanandmap.do")
	public String beanandmap(Model model) {
		AuthUser user= AuthUser
				.builder()
				.username("ICT")
				.password("ICT1234")
				.name("ICT인").regidate(LocalDateTime.now()).build();
		model.addAttribute("user", user);
		
		Map<String,Object> map= new HashMap<>();
		map.put("username", "PARK");
		map.put("password", "PARK1234");
		map.put("name", "박길동");
		model.addAttribute("map", map);
		return "thymeleaf01/thymeleaf";
	}
	
}


