package com.ict.springboot.controller.bbs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.ict.springboot.repository.users.UsersEntity;
import com.ict.springboot.service.PagingUtility;
import com.ict.springboot.service.bbs.BbsDto;
import com.ict.springboot.service.bbs.BbsService;
import com.ict.springboot.service.users.UsersDto;
import com.ict.springboot.service.users.UsersService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequestMapping("/bbs")
@RequiredArgsConstructor
@SessionAttributes(types = UsersDto.class)
public class BbsController {
	//[서비스 주입]
	private final BbsService bService;
	private final UsersService uService;
	
	//페이징 관련 상수 주입받기
	@Value("${bbs.pageSize}")
	private int pageSize;
	@Value("${bbs.blockPage}")
	private int blockPage;
	
	@ExceptionHandler({Exception.class})
	public String error(Model model) {
		//e.printStackTrace();
		model.addAttribute("loginError", "로그인 후 이용하세요");
		//뷰정보 반환
		return "forward:/users/login";
	}
	
	//게시글 목록
	//@ModelAttribute는 @SessionAttributes와 함께 사용하면
	//인증 허용여부 판단용이다
	//즉 세션 영역에 소문자로 시작하는 커멘드 객체를 무조건 찾는다
	//만약 세션 영역에 없으면 Expected Session attribute "속성명" 에러
	//※인증이 필요한 모든 컨트롤러 메소드에 인자로 @ModelAttribute를 사용한다
	@GetMapping("/all")
	public String getBbsAll(@ModelAttribute UsersDto authUser,@PageableDefault(page = 1,sort = "id",direction = Sort.Direction.DESC) Pageable pageable,Model model) {
		//서비스 호출
		//Pageable 객체를 새로운 객체로 재생성하여 적용
		pageable= PageRequest.of(pageable.getPageNumber(),pageSize,pageable.getSort());
		Page<BbsDto> bbses= bService.bbsAll(pageable);
		//데이타 저장
		model.addAttribute("bbses", bbses.getContent());
		model.addAttribute("paging",PagingUtility.pagingStyle(bbses, blockPage, "/bbs/all?"));
		//뷰 정보 반환
		return "/bbs10/list";
	}////////
	//게시글 작성 폼으로 이동
	@GetMapping("/write")
	//@ModelAttribute UsersDto authUser는 로그인한 상태만 작성 폼 허용
	public String moveWriteForm(@ModelAttribute UsersDto authUser) {
		
		return "/bbs10/write";
	}//////
	//글 입력 처리	
	@PostMapping("/write")
	public String writeBbs(BbsDto dto,@ModelAttribute UsersDto authUser) {
		//서비스 호출
		//※글 입력 서비스 호출 전 로그인한 사용자 정보로 BbsDto의 usersDto필드 설정
		//UsersDto loginUser= uService.userDetail(authUser.getUsername());
		dto.setUsersDto(authUser);
		
		BbsDto insertedDto=bService.saveBbs(dto);
		
		
		System.out.println("방금 입력한 글의 글번호(PK):"+insertedDto.getId());
		//뷰 반환
		return "redirect:/bbs/all";
	}
	//글 번호(id)로 글 상세 보기 조회
	@GetMapping("/{id}")
	public String getBbsOne(@PathVariable long id,Model model,@ModelAttribute UsersDto authUser) {
		//서비스 호출
		BbsDto bbs= bService.bbsOneById(id);
		
		System.out.println("글번호 "+id+"의 댓글 갯수:"+bbs.getCommentsDtoList().size());
		//내용 줄바꿈 하기
		bbs.setContent(bbs.getContent().replace("\r\n", "<br/>"));
		//데이타저장
		model.addAttribute("bbs", bbs);
		//뷰 반환
		return "/bbs10/view";
	}///////////////
	
	//글 수정 폼으로 이동
	@GetMapping("/update/{id}")
	public String moveEditForm(@PathVariable long id,Model model,@ModelAttribute UsersDto authUser) {
		BbsDto bbs= bService.bbsOneById(id);
		model.addAttribute("bbs", bbs);
		return "/bbs10/edit";
	}/////
	//글 수정 처리
	@PostMapping("/update")
	public String updateBbs(BbsDto dto,@ModelAttribute UsersDto authUser) {
		BbsDto updatedDto= bService.update(dto);
		//수정후 확인차 상세보기로 이동
		return "redirect:/bbs/"+dto.getId();
	}
	
	
	
	
	
	
}
