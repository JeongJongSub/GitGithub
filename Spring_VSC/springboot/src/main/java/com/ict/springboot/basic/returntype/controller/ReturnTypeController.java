package com.ict.springboot.basic.returntype.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ict.springboot.basic.thymeleaf.model.AuthUser;

import jakarta.servlet.http.HttpServletResponse;

//@Controller는 .html 템플릿 페이지 응답
@Controller
public class ReturnTypeController {
	
	//※ 주로 뷰 정보(.html 템플릿 페이지) 반환시는 반환 타입을 String	 
	//   HTML페이지가 아닌 데이타 반환시는 반환 타입을 객체(자바빈 혹은 컬렉션)로 한다	
	//반환타입-ModelAndView : ModelAndView에 뷰 정보와 데이타 정보 저장해서 반환
	@GetMapping("/returntype/modelandview.do")
	public ModelAndView modelAndView(@RequestParam Map<String, String> map ,Model model) {
		//방법1)ModelAndView객체에 뷰 정보만 저장,데이타는 Model에 저장
		//Model에 데이타 저장:?returntype=ModelAndView일때 returntype이 키,ModelAndView 값
		//model.addAllAttributes(map);
		//model.addAttribute("message", map.get("returntype"));
		//ModelAndView에 뷰 저장
		//return new ModelAndView("returnType03/returnType");//포워드
		
		//방법2)ModelAndView객체에 뷰 정보 와 데이타 저장해서 반환-Model 인자는 필요없다
		ModelAndView mav = new ModelAndView();
		//데이타 저장
		mav.addAllObjects(map);
		mav.addObject("message", map.get("returntype"));
		//뷰정보 저장
		mav.setViewName("returnType03/returnType");
		return mav;
	}///
	//반환타입-String : 뷰 정보만 반환.즉 HTML페이지를 브라우저로 전송
	@GetMapping("/returntype/string.do")	
	public String string(@RequestParam String returntype,Map map) {
		//데이타 저장
		map.put("message", returntype);
		map.put("returntype", returntype);
		//뷰 정보 반환
		return "returnType03/returnType";
	}////////////
	
	
	//[JSON 데이타 응답]	
	//방법1)반환 타입을 void 
	//     void의 의미는 디스패처 서블릿에게 뷰 및 데이타 정보 반환하지 않는다(즉 뷰리졸버가 동작하지 않는다)
	//     컨트롤러에서 바로 브라우저로 JSON 데이타 전송시
	
	@Autowired
	private ObjectMapper mapper;
	
	@GetMapping("/returntype/void.do")
	public void noreturn(@RequestParam String returntype,HttpServletResponse resp) throws IOException {
		resp.setContentType("application/json;charset=UTF-8");
		//브라우저와 연결된 출력 스트림
		PrintWriter out = resp.getWriter();
		Map<String, String> map = new HashMap<>();
		map.put("username", "ICT");
		map.put("password", "ICT1234");
		map.put("parameter", returntype);
		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map));
		//브라우저 출력
		out.print(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map));
		//스트림 닫기
		out.close();
	}/////////////
	
	//방법2)반환 타입은 String,@ResponseBody 사용
	//@ResponseBody의 의미는 디스패처 서블릿에게 뷰 및 데이타 정보 반환하지 않는다(즉 뷰리졸버가 동작하지 않는다)
	//컨트롤러에서 바로 브라우저로 JSON 데이타 전송시
	//뷰 리졸버 대신 HttpMessageConverter가 작동한다
	//방법2인 String반환시는  StringHttpMessageConverter가 작동해서 JSON으로 변환 처리
	//방법3인 자바빈 혹은 컬렉션 반환시는 JSON으로 변환하는 라이브러리가 작동해서 JSON으로 변환 처리
	//(단,스프링 부트는 JACKSON이 포함되 있음)
	//예를들면 MappingJackson2HttpMessageConverter
	@GetMapping("/returntype/stringjson.do")
	@ResponseBody
	public String stringjson(@RequestParam String returntype) throws JsonProcessingException {
		//자바 객체(자바빈 혹은 컬렉션)를 JSON으로 변환해주는 
		//라이브러리(GSON 혹은 JACKSON)를 사용시
		//아래처럼 불편하게 문자열로 JSON형식을 만들 필요가 없다	
		
		//JSON형식의 문자열 생성 첫번째
		//return String.format("{\"username\":\"ICT\",\"password\":\"ICT1234\",\"parameter\":\"%s\"}",returntype);
		//JSON형식의 문자열 생성 두번째
		Map<String, String> map = new HashMap<>();
		map.put("username", "JACK");
		map.put("password", "JACK1234");
		map.put("parameter", returntype);
		return mapper.writeValueAsString(map);
	}/////////////////
	
	//방법3)반환 타입은 자바 빈 혹은 컬렉션,@ResponseBody 사용
	@GetMapping("/returntype/object.do")
	@ResponseBody
	/*
	//1)자바빈 타입 반환시-단,DTO계열 클래스를 만들어야 한다
	public AuthUser object(@RequestParam String returntype) {
		//자바빈 객체가 자동으로 메시지 컨버터에 의해 JSON형식의 문자열로 변환되서 전송
		return AuthUser.builder()
				.username("DTO")
				.password("DTO1234")
				.name(returntype)
				.build();
	}*/
	/*
	//2)맵 컬렉션 타입 반환시-단,DTO계열 클래스를 만들 필요가 없다
	public Map<String,String> object(@RequestParam String returntype) {
		Map<String,String> map = new HashMap<>();
		map.put("username", "MAP");
		map.put("password", "MAP1234");
		map.put("name", returntype);
		return map;
	}*/
	//3)리스트 컬렉션 타입 반환시-List<Map<>>시는 DTO계열 클래스를 만들 필요가 없다
	
	public List<AuthUser> object(@RequestParam String returntype) {
		List<AuthUser> users = Arrays.asList(AuthUser.builder()
				.username("USER1")
				.password("USER1234")
				.name(returntype)
				.build(),
				AuthUser.builder()
				.username("USER2")
				.password("USER1234")
				.name(returntype)
				.build()
				);				
				
		return users;
	}
	
	
	
}
