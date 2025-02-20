package com.ict.springboot.basic.validation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.ict.springboot.basic.validation.model.FormCommand;

import jakarta.validation.Valid;

	/*
	※유효성 검증을 위해 커맨드 객체 작성
	  맵으로 폼의 값을 받아도 되지만 
	  스프링 부트의 유효성 라이브러리인 spring-boot-starter-validation를
      사용할수 없다(어노테이션 사용할수 없다) 
      즉 직접 자바 코드로 유효성 검증을 작성해야 한다
      
    ※전제 조건은 pom.xml에 spring-boot-starter-validation 라이브러리 추가 
	 @NotBlank와 같은 유효성 검증용 어노테이션을 사용 가능   
      
	 */
@Controller
public class ValidationController {
	
	@PostMapping("/validation/validationForm.do")
	//※반드시 @ModelAttribute("속성명")을 커맨드 객체에 붙여 주어야 한다
	//  뷰 템플릿(타임 리프)에서는 "속성명" 으로 커맨드 객체의 값에 접근한다
	
	public String exec(@Valid @ModelAttribute(name = "form") FormCommand form,Errors errors,Model model) {
		//FormCommand의 필드 중 어느 하나라도 유효성에 실패한다면 
		//Errors객체의 hasErrors()메소드는 true를 반환 한다
		if(errors.hasErrors()) {			
			//유효성 검증을 통과 못하면 다시 입력 페이지로 포워드
			return "validation08/validation";
		}
		
		//유효성 검증 통과시
		return "validation08/complete";
	}////

}
