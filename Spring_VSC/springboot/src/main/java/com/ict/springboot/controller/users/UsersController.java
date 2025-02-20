package com.ict.springboot.controller.users;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ict.springboot.service.PagingUtility;
import com.ict.springboot.service.users.UsersDto;
import com.ict.springboot.service.users.UsersService;

/*

Spring Data JPA 프로그래밍 단계

	1. @Entity를 붙인 엔터티 생성
	2. JpaRepository<엔터티타입,엔터티의 @Id가 붙은 타입> 상속 받은 리포지토리 인터페이스 생성
	3. 1번의 엔터티 정보를 담을 DTO 생성
	4. @Service 어노테이션을 붙인 서비스 클래스 생성
	   (2)에서 생성한 리포지토리 주입 받는다
	5. 사용자 화면의 생성(URL)
	6. 5의 요청 URL을 처리할 @Controller를 붙인 컨트롤러 클래스 생성
	   (4)의 서비스를 주입 받는다


*/
@Controller
@RequestMapping("/users/")
public class UsersController {
	
	//서비스 주입(생성자 인젝션)
	private final UsersService usersService;
	public UsersController(UsersService usersService) {
		this.usersService = usersService;
	}
	
	
	//[모든 회원 목록 요청]
	//<<페이징 미 구현>>
	@GetMapping("all")
	public String getUsersAll(Model model) {
		//1)서비스 호출
		List<UsersDto> users= usersService.usersAll();
		//데이타 저장
		model.addAttribute("users", users);
		//뷰정보 반환
		return "users09/list";
		
	}//////
	//<<페이징 구현>>
	//스프링 프레임워크에서 제공하는 @PageableDefault 어노테이션
	//Pageable, Page 인터페이스를 사용한 페이징 구현
	@Value("${users.pageSize}")
	private int pageSize;
	@Value("${users.blockPage}")
	private int blockPage;
	
	@GetMapping("pagingAll")
	public String getUsersPagingAll(@PageableDefault(page = 1,size = 2,sort = "regiDate",direction = Sort.Direction.DESC) Pageable pageable,Model model) {
		//페이지 사이즈를 변수로 대체
		//Pageable 객체를 새로운 객체로 재생성하여 적용
		pageable= PageRequest.of(pageable.getPageNumber(),pageSize,pageable.getSort());
		System.out.println("<<Pageable객체:페이징 요청 객체>>");
		System.out.println("현재 페이지 번호:"+pageable.getPageNumber());
		System.out.println("페이지 사이즈:"+pageable.getPageSize());
		System.out.println("페이징 정렬:"+pageable.getSort());
		//number는 페이지 번호.Pageable에서는 0번 페이지부터 시작
		//first()는 0번 페이지, next()는 현재 페이지 +1
		//아래 결과는 사용자가 3페이지를 클릭한 경우의 출력 예
		System.out.println("첫 페이지:"+pageable.first());//Page request [number: 0, size 3, sort: regiDate: DESC]
		System.out.println("다음 페이지:"+pageable.next());//[number: 2, size 3, sort: regiDate: DESC]
		
		//1)서비스 호출(반드시 Pageable객체 전달)
		//※List<UsersDto>에서 Page<UsersDto>로 변경
		Page<UsersDto> users= usersService.usersPagingAll(pageable);
		
		System.out.println("<<Page객체:페이징 요청 객체>>");
		System.out.println("현재 페이지 번호:"+users.getNumber());
		System.out.println("페이지 사이즈:"+users.getSize());
		System.out.println("페이징 정렬:"+users.getSort());
		System.out.println("총 페이지 수:"+users.getTotalPages());
		System.out.println("현재 페이지에 뿌려줄 목록(List<UsersDto>):"+users.getContent());
		//데이타 저장
		//users를 users.getContent()로 변경
		model.addAttribute("users", users.getContent());
		model.addAttribute("paging", PagingUtility.pagingStyle(users,blockPage, "/users/pagingAll?paging=y&"));
		//뷰정보 반환
		return "users09/list";
		
	}//////
	
	
	//[회원가입 폼으로 이동]
	@GetMapping("signup")
	public String signup() {		
		//뷰정보 반환
		return "users09/signup";
	}
	//[회원가입 처리]
	@PostMapping("signupProcess")
	public String signupProcess(UsersDto dto,Model model,@RequestParam String paging) {
		
		//서비스 호출
		boolean isSignup=usersService.signup(dto);
		if(isSignup)
			//목록으로 리다이렉트			
			if(!"y".equals(paging))
				//페이징 미 적용
				return "redirect:/users/all";			
			else
				//페이징 적용
				return "redirect:/users/pagingAll?paging=y";
		//데이터 저장
		model.addAttribute("errorMessage", "이미 가입한 아이디 입니다");
		
		//이미 존재하는 회원인 경우 다시 회원가입 페이지로 이동
		//뷰정보 반환
		return "users09/signup";
	}/////
	
	//[회원 검색 처리](검색 버튼 클릭시는 POST,페이징번호 클릭시는 GET 즉 두 방식 모두 받도록)
	//페이징 구현 화면에서만 검색하자
	@RequestMapping(path = "search",method = {RequestMethod.GET,RequestMethod.POST} )
	public String getSearchUsers(@RequestParam Map<String,String> map,@PageableDefault(page=1,size=3,sort = "regiDate",direction = Sort.Direction.DESC) Pageable pageable,Model model) {
		//검색창에 아무것도 입력하지 않고 검색 버튼 클릭시
		if(map.get("searchWord").trim().length()==0) 
			return "redirect:/users/pagingAll?paging=y";
		//Pageable객체 수정
		pageable= PageRequest.of(pageable.getPageNumber(),pageSize,pageable.getSort());
		//서비스 호출
		Page<UsersDto> searchUsers= usersService.searchUsers(map,pageable);
		//데이타 저장
		model.addAttribute("users", searchUsers.getContent());
		model.addAttribute("paging", PagingUtility.pagingStyle(
								searchUsers, 
								blockPage,String.format(
										"/users/search?searchColumn=%s&searchWord=%s&", 
										map.get("searchColumn"),
										map.get("searchWord"))));
	
		//뷰정보 반환
		return "users09/list";
	}/////
	
	//[회원 정보 처리]
	@GetMapping("{username}")
	public String getUserDetail(@PathVariable String username,Model model) {
		//서비스 호출
		UsersDto user= usersService.userDetail(username);
		//데이타 저장
		model.addAttribute("user", user);
		//뷰 정보 반환
		return "users09/view";
	}
	
}
