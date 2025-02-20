package com.ict.springboot.basic.annotation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/*
[스프링 컨테이너에  빈 등록 방법]

	방법1. @Controller,@Service,@Repository,@Component,@Configuration가 붙은 클래스를 스캔 해서 
		  스프링 컨테이너에 빈으로 등록(컴포넌트 스캔). 즉 어노테이션으로 빈 등록	
		  빈의 아이디(인스턴스 변수)는 카멜 케이스 형태의 클래스명이 된다		
		  빈의 아이디를 직접 지정시에는 value속성 추가(단,속성이 하나일때는 생략 가능).
		  @Controller(value="빈 아이디"),@Component(value="빈 아이디")등 value속성으로 빈의 아이디 지정

	방법2.@Congiguration어노테이션과 @Bean어노테이션 사용해서 자바 코드로 
	     new로 생성해서 빈 등록.     
	
		@Configuration	
		public  class  SpringBeanConfig{
		
			@Bean		
		    public  OneMemoService  oneMemoService(){		    	
		   		//return  new  OneMemoService();
		   		 OneMemoService oneMemoService= new  OneMemoService();
		   		 return oneMemoService;
		    }
		    @Bean	
		    public  OneMemoRepository  oneMemoRepository(){
		    	return  new  OneMemoRepository();
		    }
		
		}
		
	
	이때 빈의 아이디는 카멜 케이스 형태의 메소드명이 된다	
	혹은 @Bean(name="빈 아이디")으로 지정한다
	
	단,@Controller는  요청 처리를 하기 위한 빈이니까
	@Configuration으로 등록하는 빈에서 제외 한다	
	서비스 및 리포지토리 역할을 하는 빈(클래스)는 @Configuration으로 등록할 수 있다
	단,서비스와 리포지토리 역할을 하는 클래스에서  @Service,@Repository를 제거한다
	
	
	※방법1이나 방법2로 생성된 빈은 @Autowired나 @Resource로 주입해서 사용
	
	
	※방법2의 주된 목적은 빈을 설정 파일(.xml)이 아닌 자바 코드로 등록해서 
	  빈을 주입 받고자 할때	주로 사용한다
	  즉 외부 라이브러리에서 제공하는 빈 이나 빈을 Config별로 구분해서 등록 후
	  주입 받을때 사용한다
	
	방법3. 빈 설정파일에 빈으로 등록
	<beans:bean name="/HandlerMapping/BeanNameUrl.do" class="com.kosmo.springapp.basic.handlermapping.BeanNameUrlController"/>
*/
@Controller
//외부 빈인 ExternalJavaBean를 지연 로딩하기 위해서는 주입 받는 
//컨트롤러도 지연 로딩 이어야 한다
@Lazy
public class ConfigurationController {
	
	@Autowired
	private ExternalJavaBean eBean;
	
	@GetMapping("/annotation/configuration.do")
	public String exec(Model model) {
		//데이타 저장
		model.addAttribute("message", eBean);
		//뷰정보 반환
		return "annotation05/annotation";
	}
}
