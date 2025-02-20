package com.ict.springboot.controller.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.ict.springboot.service.users.UsersDto;
import com.ict.springboot.service.users.UsersService;

import jakarta.servlet.http.HttpSession;

//1.로그인 정보를 받을때 UsersDto객체로 받는다
//2.세션 영역과 리퀘스트 영역에 자동으로 UsersDto객체가 저장된다
//3.저장될때 속성명는 소문자로 시작하는 클래스명
@SessionAttributes(types = UsersDto.class)
@Controller
public class LoginController {

	//서비스 주입
	@Autowired
	private UsersService usersService;
	
	//<<로그인 화면으로 이동>>
	@GetMapping("/users/login")
	public String login() {
		return "/users09/login";
	}///////////////	
	//<<로그인 처리>>
	
	@PostMapping("/users/loginProcess")
	public String loginProcess(UsersDto users,Model model,SessionStatus status) {
		
		//서비스 호출
		boolean isLogin=usersService.isUsers(users);
		System.out.println("회원 여부:"+isLogin);
		if(!isLogin) {
			//회원이 아니어도 세션영에 무조건 저장함으로 이를 삭제
			status.setComplete();
			model.addAttribute("loginError","아이디와 비번 불일치");
			return "/users09/login";
		}
		//기본값이 "forward:",리다이렉트로 이동시는 "redirect:"
		//단,URL패턴은 페이지가 아닌 요청 URL지정
		//페이지 URL지정시 NoResourceFoundException에러
		return "redirect:/";
	}
	
	//<<로그아웃 처리>>
	@GetMapping("/users/logout")
	public String logout(SessionStatus status) {
		//로그아웃 처리-세션영역에 저장된 속성 삭제]
		status.setComplete();
		//뷰정보 반환
		return "redirect:/users/login";
	}
	
	
}
