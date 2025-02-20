package com.ict.springboot.basic.injection.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ict.springboot.basic.injection.model.Person;

@Controller
public class FieldController {

	//[필드 인젝션]
	//접근 지정자가 private임으로 외부에서 변형이 불가능하다
	//Person이 싱글톤으로 생성됨으로  동일한 Person객체가 필드로 주입 된다
	
	//STEP1]주입 받을 객체 타입의 필드 선언
	//필드에 @Autowired 부착.
	//@Autowired 생략 불가 즉 생략시 주입이 안된다
	@Autowired
	private Person person;
	
	//[요청 처리]컨트롤러 메소드
	@GetMapping("/injection/field.do")
	public String execute(Model model) {
		//데이타 저장
		model.addAttribute("personInfo", person);
		//뷰정보 반환
		return "injection04/injection";
	}
	
}
