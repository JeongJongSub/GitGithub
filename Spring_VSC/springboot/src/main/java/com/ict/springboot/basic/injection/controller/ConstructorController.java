package com.ict.springboot.basic.injection.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ict.springboot.basic.injection.model.Person;

@Controller
public class ConstructorController {

	//현재 클래스(ConstructorController)가 Person객체 필요 
	//즉 Person객체에 의존한다	
    //new하지 않고 생성자를 통해서 주입 받는다
	//이를 생성자 인젝션 이라 한다
	
	//[생성자 인잭션]
	//생성자 인젝션을 권장.왜냐하면 외부에서 변형 가능성이 없음
	//하나의 Person객체를 주입 받을시 아래와 같은 순서로 주입 된다
	//인젝션 순서: 생성자 인젝션(서길동)->세터 인젝션(서길동 님)->필드 인젝션(서길동 님)
		
	
	
	//STEP1]주입 받을 객체 타입의 필드 선언
	private Person person;
	
	//STEP2]주입 받을 객체 타입을 인자로 받는 생성자 정의
	//생성자에 @Autowired 부착.
	//생성자가 하나인 경우 @Autowired 생략 가능
	
	//@Autowired
	public ConstructorController(Person person) {	
		System.out.println("ConstructorController의 인자 생성자:"+person);
		this.person = person;
		//주입받은 Person객체 초기화
		this.person.setWriter("서길동");
		this.person.setAddr("서초동");
		this.person.setAge(20);
	}
	//[요청 처리]컨트롤러 메소드
	@GetMapping("/injection/constructor.do")
	public String execute(Model model) {
		//데이타 저장
		model.addAttribute("personInfo", person);
		//뷰정보 반환
		return "injection04/injection";
	}
	
	
	
	
}
