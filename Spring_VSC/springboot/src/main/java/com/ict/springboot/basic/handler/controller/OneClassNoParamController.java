package com.ict.springboot.basic.handler.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/*
 [컨트롤러 클래스]
 -클래스 위에 @Controller 부착
  사용자 요청을 받을수 있는 핸들러(컨트롤러)가 된다
 -.html페이지를 서비스
 */
@Controller
public class OneClassNoParamController {
	/*
	[컨트롤러 메소드 작성 규칙]	
		접근지정자 : public
	 	반환타입 : 주로 String(뷰 정보를 문자열(html페이지명만)로 반환)
	 	메소드명: 임의
	 	메소드 인자 : 원하는 타입을 사용할 수 있다(단, 사용할 수 있는 타입이 정해져 있다)
	                어노테이션도 가능
	 	예외를 throws할 수 있다(선택) 
	
	※패스 파라미터란 URL 패스에 파라미터를 포함시켜 값을 전달하는 방식이다
		설정: URL패턴의 변하는 부분을 {변수명} 로 변수처리:{변수명}
		읽기: 패스 파라미터로 값을 받을때는 @PathVariable String 변수명으로 받는다
	*/
	@GetMapping("/controller/oneclass/{path}")
	public String noparam(@PathVariable String path,Map map) {
		System.out.println("path:"+path);
		//모델 계열(Map)에 데이타 저장
		switch(path.toUpperCase()) {
			case "LIST.DO":map.put("message", "목록 요청 입니다");break;
			case "EDIT.DO":map.put("message", "수정 요청 입니다");break;
			case "VIEW.DO":map.put("message", "상세보기 요청 입니다");break;
			default:map.put("message", "삭제 요청 입니다");
		}
		//뷰 정보 반환(html 페이지 응답)
		return "controller02/controller";
	}////////////
}
