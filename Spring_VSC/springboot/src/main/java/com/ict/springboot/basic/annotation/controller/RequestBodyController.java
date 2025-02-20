package com.ict.springboot.basic.annotation.controller;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ict.springboot.basic.annotation.model.Member;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


	/*
	@RestController
		데이타를 클라이언트로 전송(@Controller + @ResponseBody와 같다)
		스프링으로 REST API구축시 사용
	@Controller
		주로 .html 페이지를 클라이언트로 전송
	
	@RequestBody
		※클라이언트로부터 Key=Value쌍이 아닌 JSON형식의 데이타를 받을 때 사용
		
	    자바 객체(자바빈 혹은 컬렉션)를 JSON형식의 문자열로
	    JSON형식의 문자열을 자바 객체(자바빈 혹은 컬렉션)로
	    변환하는 Jackson 라이브러리가 스프링 부트는 내장되어 있다
	    
	    Form태그나 A태그로 데이터 전송시는 무조건 Key=Value쌍으로 전송이 된다
	    	
	[컨트롤러 메소드에서 클라이언트가 보낸 데이타 받기]  
	1.@RequestParam 혹은 커맨드 객체(자바빈)로 데이타 받기
		>>>Form태그 및 A태그로 보내는 데이타
		   -Key=Value형태로 스프링 프레임워크에 전달된다
		   -예로 username=KIM&password=1234
		   -정상적으로 받는다
	    >>>클라이언트에서 자바스크립트로 JSON형식으로 보내는 데이타
	       -JSON.stringify({"id":"KIM","pwd":"1234"}))
	       -데이타를 받지 못한다 즉 null이다
	   
	2.@RequestBody 커맨드타입 변수 혹은  @RequestBody Map 변수로 데이타 받기	  	
	  	>>>Form태그 및 A태그로 보내는 데이타
	  	   -415에러:Content type 'application/x-www-form-urlencoded;charset=UTF-8' not supported)
	  	>>>자바스크립트로 JSON형식으로 보내는 데이타 
	  	   -정상적으로 받는다 
	  	단, @RequestBody String 변수로 받아도 되나 파싱해야 한다 
	  	그래서 String으로 받지 않는다
	※Key=Value쌍이 아닌 JSON 형식 데이타는 무조건 @RequestBody로 받자
	*/
@Controller
public class RequestBodyController {

	@Autowired
	private ObjectMapper mapper;//쉽게 자바 객체를 JSON형식으로 문자열로 변환하기 위해서 선언
	
	//[1.@RequestParam으로 받을때]
	//A태그로 보내는 데이타
	@GetMapping("/annotation/requestBodyLink.do")
	@ResponseBody
	/*
	public String keyValueLink(@RequestParam Map map) throws JsonProcessingException {	
		return mapper.writeValueAsString(map);		
	}*/
	//※@RequestParam Member member 시 MissingServletRequestParameterException: Required request parameter 'member' for method parameter type Member is not present
	public String keyValueLink(Member member) throws JsonProcessingException {		
		return mapper.writeValueAsString(member);
	}
	//FORM태그로 보내는 데이타
	@PostMapping("/annotation/requestBodyForm.do")
	@ResponseBody		
	public String keyValueForm(@RequestParam Map map) throws JsonProcessingException {			
		System.out.println("맵:"+map);
		return mapper.writeValueAsString(map);
	}
	//[2.@RequestBody으로 받을때]
	//JSON형식의 데이타로 보내는 데이타
	/*
	@PostMapping("/annotation/requestBodyJson.do")
	@ResponseBody		
	//public String json(@RequestParam Map map) throws JsonProcessingException {
	public String json(@RequestBody Map map) throws JsonProcessingException {
		//@RequestParam으로 받을때 : {} 즉 데이타를 받지 못한다
		//@RequestBody로 받을때 : {username=hwanyhee, password=1234}
		System.out.println(map);
		return mapper.writeValueAsString(map);
	}
	*/
	//[3.@RequestBody로 받고 커맨드 객체나 맵 컬렉션 반환]
	//※String으로 반환할 필요 없이 바로 커맨드 객체나 맵 컬렉션을 반환 하면 된다
	// JACKSON 라이브러리가 자동으로 자바 객체를 JSON형식의 문자열 데이타로
	// JSON형식의 문자열 데이타를 자바 객체로 변환 한다
	// 아래와 같은 잇점이 있다
	// 1)ObjectMapper 주입 불 필요
	// 2)자바 객체를 JSON형식의 문자열로 변환하는 메소드(writeValueAsString())호출 불 필요
	// 3)예외도 던질 필요 없다
	@PostMapping("/annotation/requestBodyJson.do")
	@ResponseBody	
	//Map으로 Map반환:커맨드 객체 불 필요
	/*
	public Map json(@RequestBody Map map)  {		
		return map;
	}*/
	public Member json(@RequestBody Member member)  {	
		
		return member;
	}
	//물론 맵으로 받고 커맨드 객체로 반환하거나 
	//커맨드 객체로 받고 맵으로 반환해도 된다
	
}
