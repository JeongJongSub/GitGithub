package com.ict.springboot.basic.annotation.controller;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/*
	※프로젝트 최초 실행시 스프링이 관리하는 빈(스프링 컨테이너에 등록된 빈)은 하나가 생성 된다(싱글톤)
	(스프링 프레임웍의 기본 동작)
	즉 해당 빈을 사용할 때가 아닌 프로젝트 최초 실행시에 생성된다
	빈을 사용할때가 아닌 프로젝트 최초 실행시 문제점을 파악할 수 있다는
	장점이 있다.
	
	-프로젝트 최초 실행시 빈을 생성하지 않고 빈을 사용할때 생성한다
	(Lazy loading이라 한다-JSP 모델2 방식의 서블릿처럼)
	
	1. 빈 설정 파일(.xml)에 빈을 등록하여 생성하는 경우
		lazy-init="true" 속성 추가
		lazy-init="default"는 스프링의 관리 사이클(프로젝트 최초 실행시)에 맞게 
		bean 을 생성 한다.(lazy-init="true"와 같다)
		
	
		※단,lazy-init="true" 로 설정해도 해당 빈이 lazy-init="false" 인 
		 빈에서 참조 된다면 의존성 관계(DI)로 인해 
		 프로젝트 최초 실행시에 생성 되게 된다.
	
	2. 자바코드로 빈을 등록하는 경우	
		@Lazy 어노테이션 사용.
		@Lazy 어노테이션 디폴트는 value=true 즉 지연 로딩이다 
*/
@Controller
@Lazy
public class LazyController {

	public LazyController() {
		System.out.println("com.ict.springboot.basic.annotation.controller.LazyController 생성자");
	}
	@GetMapping("/annotation/lazy.do")
	public String exec(Model model) {
		//데이타 저장
		model.addAttribute("message", "최초 요청시 LazyController객체가 딱 한번만 생성 된다");
		//뷰정보 반환
		return "annotation05/annotation";
	}
}
