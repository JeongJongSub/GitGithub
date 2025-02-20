package com.ict.springboot.basic.database.controller;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DatabaseController {

	
	//[데이타 베이스 연결을 위한 전제]
	
	//1.pom.xml에 아래 의존성 추가
	//<dependency>
    //<groupId>org.springframework.boot</groupId>
    //<artifactId>spring-boot-starter-jdbc</artifactId>		    
	//</dependency>	
	//※위 의존성은 JPA 사용시 주석 처리 하자
	//2.application.yml에 JDBC 및 히카리 설정
	
	//생성자 인젝션으로 데이타 소스 주입받기
	private DataSource dataSource;		
	public DatabaseController(DataSource dataSource) {
		
		this.dataSource = dataSource;
	}


	@GetMapping("/database/hikariCP.do")
	public String hikariCP(Model model) throws SQLException {
		//생성자 인젝션으로 주입받은 데이타 소스로 커넥션 풀에서 커넥션 객체 가져오기
		Connection conn= dataSource.getConnection();
		//데이타 저장
		model.addAttribute("message", conn==null?"데이타 베이스 연결 실패":"데이타 베이스 연결 성공");
		//커넥션 객체를 풀에 반납
		if(conn !=null) conn.close();
		//뷰 반환
		return "database07/database";
	}
}
