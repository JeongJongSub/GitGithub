package com.ict.springboot.basic.annotation.controller;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Controller
@RequestMapping("/annotation/")
public class RequestParamController {
	/*
	  * [ 파라미터 받기 ]
	  *  파라미터를 받기 위해 HttpServletRequest 사용 안하고
	  *  @RequestParam어노테이션 사용
	  * 
	  * -파라미터의 자료형으로 받을 수 있다(String이나 int로 즉 형변환 불필요)
	  * -파라미터가 여러개일때는 
	  *  @ModelAttribute어노테이션이나 @RequestParam Map map이 유리
	  *  단,Map으로 받을때 체크 박스류는 여러개 값중 하나만	  
	  * 
	  * -사용자가 전달한 파라미터 값이 매개변수에 저장됨
	  *  즉 매개변수명=리퀘스트객체.getParameter("파라미터명")와 같다
	  *  
	  * -required속성은 디폴트가 true이다 
	  *  만약 파라미터명이 매개변수 명과 다르다면
	  *  
	  *  방법1]    
	  *  @RequestParam(value="파라미터명") 자료형 매개변수명 -
	  *  required가 true
	  *  해당 파라미터명으로 전달이 안되면 에러(400에러)
	  *  
	  *  방법2]
	  *   @RequestParam(value="파라미터명",required=false) 자료형 매개변수명 
	  *   해당 파라미터명으로 전달이 안되도 필수가 아니기때문에
	  *   에러안남  *  
	  *  
	  */ 
	
	//[나이를 숫자가 아닌 값 입력시 오류 처리]
	@ExceptionHandler(exception = {Exception.class} )
	public String error(Model model,Exception e) {
		//데이타 저장
		if(e instanceof MethodArgumentTypeMismatchException)
			model.addAttribute("errorMsg", "나이는 숫자만...");
		else if(e instanceof MissingServletRequestParameterException) 
			model.addAttribute("errorMsg", "파라미터가 전달되지 않았어요");
		else
			model.addAttribute("errorMsg", "관리자에게 문의 하세요");
		
		//뷰정보 반환
		return "annotation05/annotation";
	}/////////////
	
	
	//[파라미터명과 매개변수 일치시]
	@PostMapping("requestParamEqual.do")
	public String equals(@RequestParam String name,@RequestParam int age,Model model) {
		//데이타 저장
		model.addAttribute("name", name);
		model.addAttribute("age", age+10);
		//뷰정보 반환
		return "annotation05/annotation";
	}
	//[파라미터명과 매개변수 불 일치시]
	@PostMapping("requestParamDiff.do")
	public String diff(@RequestParam(defaultValue = "No Name",required = false) String name,@RequestParam("years") int age,Model model) {
		//데이타 저장
		model.addAttribute("name", name);
		model.addAttribute("age", age+10);
		//뷰정보 반환
		return "annotation05/annotation";
	}
	//[Map으로 파라미터 받기]	
	//폼의 파라미터명이 맵의 KEY값이 되고 
	//입력하거나 선택한 값이 맵의 VALUE가 된다
	//단,체크박스류는 여러개 선택해도 무조건 첫번째 선택한 것만 저장된다.
	//이때는 @RequestParam String[] 변수 하나 더 추가
	@PostMapping("requestParamMap.do")
	public String map(@RequestParam Map map,Model model,@RequestParam String[] inter) {
		//데이타 저장
		model.addAllAttributes(map);
		System.out.println("체크박스(Map):"+map.get("inter"));
		System.out.println("체크박스(String[]):"+Arrays.stream(inter).collect(Collectors.joining(",")));
		System.out.println("라디오박스(Map):"+map.get("gender"));
		System.out.println("아이디(Map):"+map.get("username"));
		//"inter"라는 속성으로 다시 저장
		model.addAttribute("inter",Arrays.stream(inter).collect(Collectors.joining(",")));
		//뷰정보 반환
		return "annotation05/annotation";
	}
}///////////
