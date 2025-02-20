package com.ict.springboot.basic.annotation.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class RequestMappingController {
	//※스프링 프레임워크 4.3부터 HTTP메소드에 대응하는 어노테이션 등장(@GetMapping/@PostMapping등)
	
	/*
	//	1.@RequestMapping("/요청URL")
	//	  -주로 요청을 처리하는 메소드 앞에 단다.
	//	  -컨텍스트 루트를 제외한 /로 시작하는 요청URL
	//	  -HTTP 메소드 모든 방식 처리가능
	@RequestMapping("/annotation/requestMappingAll.do")
	public String all(HttpServletRequest req) {
		//데이타 저장]
		//리퀘스트 영역에 직접 데이타 저장
		String authInfo=String.format("[아이디:%s,비번:%s,요청방식:%s]",
				req.getParameter("username"),
				req.getParameter("password"),
				req.getMethod());
		req.setAttribute("authInfo", authInfo);
		//뷰정보 반환
		return "annotation05/annotation";
	}////////////
	
	//	2.@RequestMapping(value="/요청URL",mehtod=전송방식지정)
	// 	  -하나만 처리 가능
	@RequestMapping(path = "/annotation/requestMappingOne.do",method = RequestMethod.GET)
	public String one(HttpServletRequest req) {
		//뷰정보 반환
		return all(req);		
	}////////////
	*/
	//	3.URL패턴이 다른 여러 요청을 하나의 컨트롤러 메소드로 처리하기
	//	방법1) path={"요청URL1","요청URL2",...}
	//	방법2) @PathVariable 과 요청URL의 바뀌는 부분을 변수 처리
	/*
	//방법1)
	//@RequestMapping({"/annotation/requestMappingAll.do","/annotation/requestMappingOne.do"})
	@RequestMapping(path={"/annotation/requestMappingAll.do","/annotation/requestMappingOne.do"},method = {RequestMethod.GET,RequestMethod.POST})
	public String multi(HttpServletRequest req) {
		//데이타 저장]
		//리퀘스트 영역에 직접 데이타 저장
		String authInfo=String.format("[아이디:%s,비번:%s,요청방식:%s]",
				req.getParameter("username"),
				req.getParameter("password"),
				req.getMethod());
		req.setAttribute("authInfo", authInfo);
		//뷰정보 반환
		return "annotation05/annotation";
	}////////////
	*/
	
	//방법2)
	@RequestMapping("/annotation/{path}")
	public String multi(@PathVariable String path,@RequestParam Map map,Model model) {
		//데이타 저장]
		//리퀘스트 영역에 직접 데이타 저장
		String authInfo=String.format("[아이디:%s,비번:%s,URL의 PathVariable:%s]",
				map.get("username"),
				map.get("password"),
				path);
		
		model.addAttribute("authInfo", authInfo);
		//뷰정보 반환
		return "annotation05/annotation";
	}////////////
	
	
}
