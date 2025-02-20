package com.ict.springboot.basic.annotation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/*
	@ResponseBody : 뷰 리졸버를 거치지 않고 직접 브라우저로 출력시 사용.
	                .html 페이지가 아닌  클라이언트에 데이타 전송시에 사용 한다
	                반환 타입은 String으로 한다.
	                데이타 전송 예는 ReturnTypeController.java 참조
	                ※반환되는 문자열이 응답 바디에 쓰인다(그래서 반환타입은 String 이다) 
	@Controller + @ResponseBody는 @RestController와 같다
*/
@Controller
public class ResponseBodyController {

	@GetMapping("/annotation/responsebody.do")
	@ResponseBody
	public  String exec() {
		//보통 JSON형태의 문자열을 응답
		//클라이언트는 비동기로 AJAX요청 즉 자바스크립트로 요청 한다
		return "<h1>@Controller+@ResponseBody로 브라우저에 출력합니다</h1>";
	}
	
}
