package com.ict.springboot.basic.annotation.model;

import lombok.Getter;
import lombok.Setter;

//[스프링에서는 DTO계열 클래스를 커맨드 객체라 한다.]

@Getter
@Setter
public class Command {
	/*※
	폼의 파라미터명과 속성(필드,멤버변수)을 일치시켜야 한다.
    체크박스 값을 저장하는 필드는 타입을 
    String[] 혹은 String으로 한다
    String으로 지정시 ,(콤마)구분해서 선택된 모든 값들이 저장 된다.
	*/
	//속성들]
	private String name;
	private String password;
	private String inter;//혹은 private String[] inter;
	private String gender;
	private String grade;
	private String file;
	private String self;
	public Command() {
		System.out.println("com.ict.springboot.basic.annotation.model.Command 기본 생성자");
	}
	//자바 코드(@Configuration 및 @Bean사용)로 현재 빈(Command)을 컨테이너에 등록하기 위한 테스트용 생성자
	public Command(String name, String password) {
		
		this.name = name;
		this.password = password;
		System.out.println("com.ict.springboot.basic.annotation.model.Command 인자 생성자(name,password)");
	}
	
	
	
}
