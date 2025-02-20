package com.ict.springboot.basic.annotation.controller;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ict.springboot.basic.annotation.model.Command;

@Controller
public class ModelAttributeController {
	
	//(방법1)맵으로 받기
	//:커맨드 객체(DTO계열 클래스) 생성 불필요
	//단,체크박스 처럼 하나의 파라미터명(키값이 됨)으로 여러 값이 넘어올때는 
	//Map으로 받기 때문에 하나만 받을 수 있다.
	/*
	@PostMapping("/annotation/modelAttribute.do")
	public String map(@RequestParam Map map,@RequestParam String[] inter,Model model) {
		//데이타 저장
		model.addAllAttributes(map);		
		model.addAttribute("inter",Arrays.stream(inter).collect(Collectors.joining(",")));
		//뷰정보 반환
		return "annotation05/modelAttribute";
	}///////
	*/
	/*
	 (방법2)커맨드 객체로 받기 
	  @ModelAttribute("속성명")  사용
	   
	  파라미터가 많은 경우 서블릿 API(HttpServletRequest)보다는
	  커맨드 객체 혹은 맵으로 받는게 유리
	  -단, 커맨드 객체(DTO계열)의 속성명과 폼의 파라미터명을 일치시켜야 한다.
	  -속성명은 영역에 저장된 객체의 식별자 이다.
	   @ModelAttribute("속성명") 생략시에는 Model의 add계열 메소드로 객체를 저장해야 한다 
	  예]
	  @ModelAttribute("속성명")  커맨드객체타입(Command) 매개변수명
	  
	  ※@SessionAttribute와 함께 세션 처리(인증)에서 주로 사용
	  
	  ※아래 요청이 있을 때마다 Command객체가 새롭게 계속 생성된다
	  이 Command객체의 스프링 컨테이너가 생명주기를 관리 하지 않는다
	  (즉 싱글톤이 아니다)
	 */
	@PostMapping("/annotation/modelAttribute.do")
	//[@ModelAttribute어노테이션 사용]
	//@ModelAttribute(name="cmd") Command cmd는 model.addAttribute("cmd",cmd)와 동일하다
	//public String command(@ModelAttribute(name="cmd") Command cmd) {
	
	//[@ModelAttribute 생략]
	//단,생략시는 Model로 영역에 커맨드 객체(Command타입)를 저장해야 한다
	public String command(Command cmd,Model model) {
		//커맨드 객체로 폼에 입력한값 받는지 확인 코드
		System.out.println("체크 박스:"+cmd.getInter());	
		//@ModelAttribute 생략시 데이타 저장
		model.addAttribute("cmd",cmd);
		//뷰정보 반환
		return "annotation05/modelAttribute";
	}
}//////////////
