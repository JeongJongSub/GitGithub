package com.ict.springboot.basic.injection.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ict.springboot.basic.injection.model.Person;

@Controller
public class SetterController {

	//[세터 인젝션]
	//  주입받은 값을 가공해서 속성에 넣을때 사용
	//  단,외부에서 변형 가능성이 있다
	
	//STEP1]주입 받을 객체 타입의 필드 선언
	private Person person;
	//STEP2]주입 받을 객체 타입을 인자로 받는 세터 정의
	//세터에 @Autowired 부착.
	//@Autowired 생략 불가 즉 생략시 세터 메소드가 자동으로 호출 안 된다
	//ConstructorController의 인자 생성자 호출로 생성자 인젝션 수행 후
	//->SetterController의 세터 메소드 호출로 세터 인젝션 수행
	
	@Autowired
	public void setPerson(Person person) {
		System.out.println("SetterController의 세터");
		this.person = person;
		//주입받은 Person객체 가공하자
		this.person.setAddr(this.person.getAddr()+" 살아요");
		this.person.setAge(this.person.getAge()+5);
		this.person.setWriter(this.person.getWriter()+"님");
	}
	//[요청 처리]컨트롤러 메소드
	@GetMapping("/injection/setter.do")
	public String execute(Model model) {
		//데이타 저장
		model.addAttribute("personInfo", person);
		//뷰정보 반환
		return "injection04/injection";
	}
	
	
	
}
