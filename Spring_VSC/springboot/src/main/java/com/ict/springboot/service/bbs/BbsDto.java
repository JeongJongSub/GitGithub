package com.ict.springboot.service.bbs;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

import com.ict.springboot.repository.bbs.BbsEntity;
import com.ict.springboot.repository.bbs.CommentsEntity;
import com.ict.springboot.repository.users.UsersEntity;
import com.ict.springboot.service.users.UsersDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BbsDto {
	//엔터티의 필드와 일치하지 않아도 무방 
	//즉 필요한 필드만으로 구성
	private long id;
	private String title;
	private String content;
	private LocalDateTime postDate;
	private UsersDto usersDto;
	//게시글 상세보기 조회시 댓글 목록용(단,엔터티 연관관계를 양방향 관계로 설정)
	private List<CommentsDto> commentsDtoList;
	
	//간단한 스택 오버 플로우 해결 방법:비 권장
	//private List<CommentsEntity> commentsDtoList;
	//[DTO를 ENTITY로 변환하는 메소드]
	public BbsEntity toEntity() {
		//※목록변환시
		//  .comments(commentsDtoList.stream().map(dto->dto.toEntity()).collect(Collectors.toList()))
		//  코드는 재귀호출로 스택오버플로우 발생
		//  코멘트 정보와 사용자만 세팅하면 된다
		List<CommentsEntity> comments= new Vector<>();
		for(CommentsDto dto: commentsDtoList) {
			CommentsEntity comment=CommentsEntity.builder()
					.id(dto.getId())
					.content(dto.getContent())
					.postDate(dto.getPostDate())
					.usersEntity(dto.getUsersDto().toEntity())
					.build();
			comments.add(comment);
		}
		
		
		
		return BbsEntity.builder()
				.id(id)
				.content(content)
				.title(title)
				.postDate(postDate)
				.userEntity(usersDto.toEntity())
				.comments(comments)
				//.comments(commentsDtoList.stream().map(dto->dto.toEntity()).collect(Collectors.toList()))
				//.comments(commentsDtoList)
				.build();
	}
	//[ENTITY를 DTO로 변환하는 메소드]
	public static BbsDto toDto(BbsEntity bbsEntity) {
		System.out.println("BbsDto-코멘트 엔터티 목록:"+bbsEntity.getComments());
		
		
		//※목록변환시
		//  .commentsDtoList(bbsEntity.getComments().stream().map(entity->CommentsDto.toDto(entity).collect(Collectors.toList())
		//  코드는 재귀호출로 스택오버플로우 발생
		//  코멘트 정보와 사용자만 세팅하면 된다
		
		List<CommentsDto> comments= new Vector<>();
		for(CommentsEntity entity: bbsEntity.getComments()) {
			CommentsDto comment=CommentsDto.builder()
				.id(entity.getId())
				.content(entity.getContent())
				.postDate(entity.getPostDate())
				.usersDto(UsersDto.toDto(entity.getUsersEntity()))
				.build();
			comments.add(comment);
		}
		return BbsDto.builder()
				.id(bbsEntity.getId())
				.content(bbsEntity.getContent())
				.title(bbsEntity.getTitle())
				.postDate(bbsEntity.getPostDate())
				.usersDto(UsersDto.toDto(bbsEntity.getUserEntity()))
				.commentsDtoList(comments)
				//.commentsDtoList(bbsEntity.getComments().stream().map(entity->CommentsDto.toDto(entity).collect(Collectors.toList())//스택 오버 플로우 발생
				//.commentsDtoList(bbsEntity.getComments())
				.build();
	}
	
}
