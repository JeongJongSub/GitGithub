package com.ict.springboot.basic.properties.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller(value = "myController")
public class PropertiesController {
	
	//1.디폴트 속성 파일:application.properties(.yml)에서 읽기(application.yml로 변경해도 무방)
	//.properties 속성파일에서 키로 값을 읽어오는 어노테이션
	@Value("${mykey.yourkey.wekey}")
	private String wekey;
	@Value("${server.servlet.session.timeout}")
	private String timeout;
	
	//2.디폴트 속성 파일이 아닌 src/main/resources/config/paging.properties에서 읽기
	//[전제 조건]
	//1)PropertySourcesPlaceholderConfigurer 빈을 스프링 컨테이너에 등록
	//  PropertySourcesPlaceholderConfigurer는 .properties파일만 지원한다(.yml은 지원하지 않는다)
	//2)@PropertySource()에 속성 파일 위치 설정
	@Value("${onememo.pageSize}")
	private int pageSize;
	@Value("${onememo.blockPage}")
	private int blockPage;
	
	
	@GetMapping("/properties/valueDefault")
	public String valueDefault(Model model) {
		//데이타 저장
		model.addAttribute("message", String.format("wekey:%s,timeout:%s", wekey,timeout));
		//뷰 반환
		return "properties06/properties";
	}
	
	@GetMapping("/properties/valueCustom")
	public String valueCustom(Model model) {
		//데이타 저장
		model.addAttribute("message", String.format("pageSize:%s,blockPage:%s", pageSize,blockPage));
		//뷰 반환
		return "properties06/properties";
	}
	
	
}
