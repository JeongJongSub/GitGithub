package com.ict.springboot.basic.annotation.controller;


import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.ict.springboot.basic.annotation.model.Member;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



/*
	@SessionAttributes() 어노테이션]
	
	- 서블릿 API(HttpSession)를 사용하지 않고 세션 처리를 하기 위한 어노테이션
	- 클래스 앞에 붙인다.
	- 세션변수(세션 영역에 저장한 속성명)에 값을 저장하려면 
	  컨트롤러 메소드의 매개변수중 모델계열(Model,Map,ModelMap)
	  을 추가하여 add계열("세변변수명",값) 혹은 put("세변변수명",값)(맵일 경우)으로
	  저장하면 그 이름으로
	  세션영역에도 저장된다.(리퀘스트 영역뿐만 아니라) 즉 별도로 세션 영역에 저장하지 않아도 된다.
	
	예] @SessionAttributes("세션변수명")
	 ※세션변수명이 여러개일때
	 @SessionAttributes({"세션변수명1","세션변수명2",...})
	
	-세션 영역에서 값 읽어올때
	
	 컨트롤러 메소드(@ModelAttribute(value="세션변수명")  String 세션값담을변수)
	 ※만약 세션영역에 세션변수명이 저장되어 있지 않다면 무조건 에러
	
	-세션해제
	
	컨트롤러 메소드(SessionStatus session){
		session.setComplete();
	}
	
	
	
	@SessionAttribute 어노테이션 사용
	
	1.커맨드 객체 사용 안하는 경우 
	@SessionAttribute({"세션변수명1","세션변수명2",...}) 세션변수명은 폼의 파라미터 명과 반드시 일치 시켜라
	 [로그인] - 모델 계열에 사용자가 입력한 아이디와 비번을 저장하면 세션 영역에도 저장된다
	 login(Model model,@RequestParam Map map){
		회원 여부 판단후 회원이라면 model 에 map저장
	    회원이 아니라면 model에 에러메시지 저장
	 }
	 [로그아웃]
	 logout(SessionStatus status){
		status.setComplete();
	 }
	 	
	 [로그인여부 판단]
	 isLogin(@ModelAttribute("세션변수명") String username,Model model){
		메소드 안으로 들어 온다는 얘기는 세션영역에 "세션변수명" 존재한다는 말 고로 로그인이 되었다
	    세션영역에 "세션변수명" 없다면 무조건 500에러 - 
	    @ExceptionHandler 나 설정 파일로 에러 처리
	    model에 로그인 되었다는 정보를 저장
		
	}
	2.커맨드 객체 사용하는 경우
	-※빈 설정 파일(XML)에 <annotation-driven/>태그 추가(스프링 레거시인 경우.스프링 부트는 제외)
	
	@SessionAttribute(types=커맨드객체 클래스명.class) 
	 [로그인] - 아이디와 비번을 커맨드 객체로 받는다 이때 커맨드 객체는 회원이든 아니든 무조건 세션영역에 저장된다
	           세션 영역에 저장될때 소문자로 시작하는 커맨드객체 클래명(authUser)이 키값이 된다
	           value값은 당연히 커맨드 객체가 된다
		
	
	 login(Model model,Member member,SessionStatus status){
		회원이 아닌 경우를 판단해서
	         세션영역에 저장된 커맨드를 객체를 status.setComplete()로 삭제해한다
	         model에는 에러메시지 저장
	 }
	 [로그아웃]
	 logout(SessionStatus status){
		status.setComplete();
	}
	 [로그인여부 판단]
	 isLogin(@ModelAttribute("member") Member auth,Model model){
		//메소드 안으로 들어 온다는 애기는 세션영역에 "loginCommand" 존재한다는 말 고로 로그인이 되었다
	        //세션영역에 "member" 없다면 무조건 500에러 - @ExceptionHandler 나 설정파일로 에러 처리
	        model에 로그인되었다는 정보를 저장
		}

*/

//[1.커맨드 객체 미 사용시]
//※세션영역에 저장할 속성명(세션변수)은 폼의 파라미터명과 일치 시켜라
@SessionAttributes(names = {"username","password"})
//[2.커맨드 객체 사용시]
//@SessionAttributes(types = {Member.class})
@Controller
public class SessionAttributeController {
	
	@ExceptionHandler({IllegalStateException.class})
	public String error(Model model) {
		model.addAttribute("loginError", "로그인 후 이용하세요");
		//뷰정보 반환
		return "annotation05/annotation";
	}
	
	
	//[1.커맨드 객체 미 사용시]
	//<<로그인 처리>>
	@PostMapping("/annotation/login.do")
	public String login(@RequestParam Map map,Model model) {
		//데이타 저장
		//회원여부 판단
		if("KIM".equals(map.get("username")) && "1234".equals(map.get("password"))) {
			//로그인 처리-세션 영역에 필요한 값 저장
			//※@SessionAttribute사용시 세션 및 리퀘스트 두 영역에 동시에 저장됨.
			model.addAllAttributes(map);
		}
		else
			model.addAttribute("loginError", "아이디와 비번 불일치!");
		
		//뷰정보 반환
		return "annotation05/annotation";
	}
	//<<로그아웃 처리>>
	@GetMapping("/annotation/logout.do")
	public String logout(SessionStatus status) {
		//로그아웃 처리-세션영역에 저장된 속성 삭제]
		status.setComplete();
		//뷰정보 반환
		return "annotation05/annotation";
	}
	//<<로그인 여부 판단>>
	//@ModelAttribute(name = "username")는 세션 영역에서 "username"이라는 속성으로 값을 가져오는 역할
	//그래서 세션 영역에 반드시 "username"라는 속성(키가) 존재 해야 한다.
	//그렇지 않으면 에러(Expected session attribute 'username')
	@GetMapping("/annotation/isLogin.do")
	public String isLogin(@ModelAttribute(name = "username") String username,Model model) {
		//데이타 저장
		//로그인 여부 판단-세션영역에 존재 유무로 판단후 데이타 저장]
		model.addAttribute("checkLogin", username+"님 인증 되었습니다");
		//뷰정보 반환
		return "annotation05/annotation";
	}
	
	/*
	//[2.커맨드 객체 사용시]
	//<<로그인 처리>>
	@PostMapping("/annotation/login.do")
	//사용자가 입력한 값는 커맨드 객체로 받는다
	//인증 정보가 일치하지 않더라도  즉 회원이 아니더라도 무조건 사용자가 입력한 값이 세션 영역에 저장된다
	//@SessionAttributes(types = {Member.class})로 설정했기 때문에 세션영역는
	//login 메소드의 인자인 Member객체가 저장된다
	//이때 저장될때 키값(속성명)는 소문자로 시작하는 클래스명이다 
	//회원이 아닌 경우는 세션 영역에 저장된 값을 삭제하기 위해 SessionStatus를 인자로 추가
	
	public String login(Member member,Model model,SessionStatus status) {
		//데이타 저장
		//회원여부 판단
		if(!("KIM".equals(member.getUsername()) && "1234".equals(member.getPassword()))) {
			status.setComplete();
			model.addAttribute("loginError", "Icorrect username or password!");			
		}		
		//뷰정보 반환
		return "annotation05/annotation";
	}
	//<<로그아웃 처리>>
	@GetMapping("/annotation/logout.do")
	public String logout(SessionStatus status) {
		//로그아웃 처리-세션영역에 저장된 속성 삭제]
		status.setComplete();
		//뷰정보 반환
		return "annotation05/annotation";
	}
	//<<로그인 여부 판단>>	
	@GetMapping("/annotation/isLogin.do")	
	public String isLogin(@ModelAttribute(name = "member") Member member,Model model) {
		//데이타 저장
		//로그인 여부 판단-세션영역에 존재 유무로 판단후 데이타 저장]
		model.addAttribute("checkLogin", member.getUsername()+" is authenticated!!");
		//뷰정보 반환
		return "annotation05/annotation";
	}
	*/
	
}
