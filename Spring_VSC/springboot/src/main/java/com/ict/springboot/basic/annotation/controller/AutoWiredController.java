package com.ict.springboot.basic.annotation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ict.springboot.basic.annotation.model.Command;

@Controller
public class AutoWiredController {
		/*
		테스트 시나리오(필드 인젝션시 테스트 한다)
	  	1. Type기반
	  		1-1.SpringBeanConfig파일(내가 만든 클래스)에 Command객체 하나만 등록(단,@Bean의 name속성(id값)/@Qualifier 생략)
	  			현재 컨트롤러 클래스에 아래 필드 추가
	  			@Autowired
				private Command fCommand;
				@Autowired
				private Command sCommand;
			
				fCommand와 sCommand는 같은 객체가 주입된다
	    	1-2.SpringBeanConfig파일에서 자바코드로 등록한 Command객체의 @Bean을 주석처리 한다 
	    		그러면 아래 에러
				APPLICATION FAILED TO START 
				왜냐하면 
				@Autowired는 required = true다 즉 주입 대상 객체가 빈으로 스프링 컨테이너에
				등록(인스턴스화) 되어있어야 한다
	    	1-3.@Autowired(required = false) 
	    	    required = false속성 추가 후 역시 SpringBeanConfig파일의 @Bean을 주석처리
	       		주입이 필수가 아니기때문에 객체가 없으면 주입이 안됨으로 
	       		fCommand와 sCommand는 null     
	   
	       
	 	2. ID(@Bean의 name속성값) 기반
	    	
	 		자바코드로 빈 등록시 ID부여 방법
	 		@Bean(name="id명") 혹은 아이디 미 부여시 메소드명이 id명이 된다
	    	name="id명" 우선하여 메소드명이 아닌 "id명"이 빈의 아이디가 된다
	    	※아이디를 지정하는 경우는 
	    	스프링 컨테이너에 동일한 타입의 객체를 두 개이상 등록시 지정한다
	    	단,SpringBeanConfig파일에서 @Bean의 name속성(id값 즉 인스턴스 변수 역할)은 주입 대상 
	    	클래스의 필드명(변수명)과 일치시켜야 한다
	    	
	 	3. Qualifier기반
	    	3-1. SpringBeanConfig파일에서 @Bean의 name속성 제거 및  @Qualifier("식별자1") 추가 
	    	3-2. 필드에 @Qualifier("식별자1")추가
	  
	  	※ 스프링 프레임워크 컨테이너에 등록된 빈의 주입 순서는 
	     	@Autowired은 먼저 "타입"으로 빈을 찾아서 주입->만약 동일한 타입이 여러개면 "ID"로 찾아서 주입
	     	->찾는 아이디가 없으면 Qualifier 로 찾아서 주입한다
	     	@Resource어노테이션은 ID-> 타입->Qualifier 순이다
	 */
	
	/*
	//필드에 부착 : 필드에 @Autowired 어노테이션 부착
	@Autowired(required = false)
	@Qualifier("command1")
	private Command fCommand;
	@Autowired(required = false)
	@Qualifier("command2")
	private Command sCommand;*/
	
	/*
	//세터에 부착 : 추가적인 로직이 필요할때(세터 인젝션),역시 @Qualifier도 가능
	//※이때는 SpringBeanConfig파일의 id 속성값이 세터의 매개변수명과 일치시켜야 한다	
	private Command fCommand;
	private Command sCommand;
	
	@Autowired	
	@Qualifier("command1")//부착시는 세터의 매개 변수명과 등록한 빈의 id명이 일치하지 않아도 됨
	public void setfCommand(Command firstCommand) {
		
		this.fCommand = firstCommand;
	}
	@Autowired
	@Qualifier("command2")
	public void setsCommand(Command secondCommand) {
		this.sCommand = secondCommand;
	}*/
	
	//생성자에 부착 : 단,@Qualifier부착 불가-생성자 인젝션
    //※이때는 SpringBeanConfig파일의 id 속성 값이 생성자의 매개변수명과 반드시 일치시켜야 한다
	
	private Command fCommand;
	private Command sCommand;
	
	//@Autowired는 생성자가 하나일때 생략 가능
	@Autowired
	public AutoWiredController(Command firstCommand, Command secondCommand) {		
		this.fCommand = firstCommand;
		this.sCommand = secondCommand;
	}

	@GetMapping("/annotation/autowired.do")
	public String exec(Model model) {
		//데이타 저장
		System.out.println("Command객체 빈으로 미 등록시(fCommand):"+fCommand);
		model.addAttribute("message",String.format("fCommand:%s,sCommand:%s", fCommand,sCommand));
		//뷰정보 반환
		return "annotation05/annotation";
	}///////////////////
}
