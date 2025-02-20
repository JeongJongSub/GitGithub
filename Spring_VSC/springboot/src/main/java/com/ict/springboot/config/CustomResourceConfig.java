package com.ict.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CustomResourceConfig implements WebMvcConfigurer {
	
	//정적 리소스 설정 추가
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {		
		//URL 경로 패턴(/**) 및 정적 리소스의 실제 파일 위치(classpath:/static/) 디폴트 설정을
		//spring.web.resources.add-mappings=false 설정으로 비활성화시
		//1.리소스 핸들러 추가로 기존  URL패턴 및 실제 위치 활성화
		//spring.web.resources.add-mappings=true 설정시는 아래 코드는 불필요
		//registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
		//2.리소스 핸들러 추가로 새로운 URL패턴 및 실제 위치를 추가
		registry.addResourceHandler("/myresource/**").addResourceLocations("classpath:/mystatic/");
	}
	
	
	
}
