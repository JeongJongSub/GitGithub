package com.ict.springboot.controller.bbs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.ict.springboot.service.bbs.BbsService;
import com.ict.springboot.service.bbs.CommentsDto;
import com.ict.springboot.service.bbs.CommentsService;
import com.ict.springboot.service.users.UsersDto;
import com.ict.springboot.service.users.UsersService;

import lombok.RequiredArgsConstructor;

//<<.html페이지를 서비스 하지 않고 JSON형식의 데이타 서비스>>
@RestController
@SessionAttributes(types = UsersDto.class)

@RequiredArgsConstructor
public class CommentsController {

	//[생성자 인젝션] 
	//※생성자 인젝션시는 반드시 필드에 final 키워드 부착
	//서비스들 주입
	private final CommentsService cService;
	private final BbsService bService;	
	private final UsersService uService;
	
	//한줄 댓글 입력처리
	@PostMapping("/comments")
	public CommentsDto writeComments(@ModelAttribute UsersDto usersDto,CommentsDto commentsDto,@RequestParam long bbs_id) {
		System.out.println("로그인 사용자:"+usersDto.getUsername());
		//인자인 CommentsDto의 content필드만 설정 된다 즉 commentsDto.getBbsDto()는 null
		//System.out.println("게시글 번호:"+commentsDto.getBbsDto().getId());
		System.out.println("한줄 코멘트:"+commentsDto.getContent());
		
		//CommentsDto의 id와 postDate필드를 제외한 bbsDto와 usersDto를 설정하기
		commentsDto.setBbsDto(bService.bbsOneById(bbs_id));
		commentsDto.setUsersDto(uService.userDetail(usersDto.getUsername()));
		
		//서비스 호출
		return cService.write(commentsDto);
		
	}///////////////////
	
}
