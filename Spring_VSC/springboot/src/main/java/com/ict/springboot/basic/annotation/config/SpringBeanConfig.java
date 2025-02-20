package com.ict.springboot.basic.annotation.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import com.ict.springboot.basic.annotation.controller.ExternalJavaBean;
import com.ict.springboot.basic.annotation.model.Command;

@Configuration
public class SpringBeanConfig {

	public SpringBeanConfig() {
		System.out.println("com.ict.springboot.basic.annotation.config.SpringBeanConfig 기본 생성자");
	}/////
	/*
	  @Bean어노테이션을 부착할 메소드 작성 규칙
	  접근 지정자 : public(생략 가능)
	  반환타입 : 스프링 컨테이너에 등록하고자 하는 빈의 클래스 타입
	  메소드명 : 보통 소문자로 시작하는 클래스명(단,임의 가능)
	  
	  @Bean의 name속성으로 빈의 아이디(인스턴스 변수 역할) 설정가능
	  해당 빈을 필요로 하는 곳에서 @Autowired나 @Resource로 자동 주입해서 사용
	*/
	//※@Bean어노테이션에 의해 Command객체는 스프링 컨테이너에 등록 된다(즉 싱글 톤이다)
	//[테스트 시나리오 1]
	//빈의 아이디 미 부여(이때는 메소드명이 아이디가 된다)
	//@Bean  --@Autowired(required = false) 테스트시 주석처리
	//Command firstCommand() {
	//	return new Command("김길동", "KIM1234");
	//}
	//[테스트 시나리오 2]
	/*
	@Bean(name = "fCommand")
	Command firstCommand() {
		return new Command("김길동", "KIM1234");
	}
	@Bean("sCommand")
	Command secondCommand() {
		return new Command("이길동", "LEE1234");
	}*/
	
	//[테스트 시나리오 3]
	
	@Bean
	@Qualifier("command1")
	Command firstCommand() {
		return new Command("김길동", "KIM1234");
	}
	@Bean
	@Qualifier("command2")
	Command secondCommand() {
		return new Command("이길동", "LEE1234");
	}
	
	//[외부 라이브러에서 제공하는 클래스를 스프링 컨테이너의 빈으로 등록하기]
	//ConfigurationController에서 주입 받을 빈이다
	@Bean
	//@Lazy를 붙여서 ExternalJavaBean객체를 지연 로딩으로 설정시 
    //지연로딩이 안되고 바로 프로젝트 실행시 생성 된다.
	//왜냐하면 지연 로딩이 아닌 컨트롤러가 프로젝트 실행시 생성 되기때문에 컨트롤러에서 주입받은
	//ExternalJavaBean객체도 지연로딩이 되지 않는다
	//프로젝츠 실행시가 아닌 사용자 최초 요청시 외부 빈을 생성하려면(Lazy 로딩하려면) 
	//주입받은 컨트롤러도 Lazy로딩 이어야 한다
	@Lazy
	ExternalJavaBean externalJavaBean() {
		return new ExternalJavaBean();
	}
	
	
	
	
}
