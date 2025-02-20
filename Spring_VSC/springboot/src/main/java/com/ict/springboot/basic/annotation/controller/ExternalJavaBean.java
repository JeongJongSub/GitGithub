package com.ict.springboot.basic.annotation.controller;

/*
	※스프링 철학에 맞게 DI를 통해서 주입 받자
	  주입 받으려면 주입 받으려는 빈은 반드시 스프링 컨테이너에 등록되야 한다
	※스프링 컨테이너에 빈 등록 방법 3가지 
	1.@Component어노이션을 붙이거나(컴포넌트 스캔) 아니면
	2.@Configuration어노테이션이 붙은 클래스에서 @Bean으로 등록 한다(자바 코드로 등록)
	3.빈 설정파일(XML파일)에 등록해주거나
	
	즉 내가 만든 빈은 @Component를 붙이면 된다
	외부 라이브러리에서 제공하는 클래스는 스프링 컨테이너에 등록하려면
	2번이나 3번을 사용한다
	스프링 부트는 2번을 사용한다 
*/
//아래는 ConfigurationControler에서 주입 받는 외부 라이브러리라고 가정하자
public class ExternalJavaBean {
	
	public ExternalJavaBean() {
		System.out.println("com.ict.springboot.basic.annotation.controller.ExternalJavaBean의 생성자");
	}
	@Override
	public String toString() {
		
		return "ExternalJavaBean객체 입니다";
	}
}
