package com.ict.springboot.basic.injection.model;

import org.springframework.stereotype.Component;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter


@Component
public class Person {
	//[속성(필드)-멤버변수들]
	private String writer;
	private String addr;
	private int age;
	
	public Person() {
		System.out.println("com.ict.springboot.basic.injection.model.Person의 기본 생성자");
	}//////////
	public Person(String writer, String addr, int age) {		
		this.writer = writer;
		this.addr = addr;
		this.age = age;
		System.out.println("com.ict.springboot.basic.injection.model.Person의 인자 생성자");
	}/////////
	@Override
	public String toString() {		
		return String.format("[이름:%s,주소:%s,나이:%s]", writer,addr,age);
	}///////////
	
	
}
