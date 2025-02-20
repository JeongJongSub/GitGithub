package com.ict.springboot.basic.annotation.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Member {
	//※반드시 로그인 폼의 파라미터명과 일치 시키자
	private String username;
	private String password;
}
