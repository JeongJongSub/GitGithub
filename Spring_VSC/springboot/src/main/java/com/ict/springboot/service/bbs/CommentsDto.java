package com.ict.springboot.service.bbs;

import java.time.LocalDateTime;

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
public class CommentsDto {
	
	private Long id;
	private String content;
	private LocalDateTime postDate;
	private BbsDto bbsDto;
	private UsersDto usersDto;
	
	//DTO를 ENTITY로 변환하는 메소드
	public CommentsEntity toEntity() {
		return CommentsEntity.builder()
				.id(id)
				.content(content)
				.postDate(postDate)
				.bbsEntity(bbsDto.toEntity())
				.usersEntity(usersDto.toEntity())
				.build();
	}//
	//ENTITY를 DTO로 변환하는 메소드
	public static CommentsDto toDto(CommentsEntity entity) {
		return CommentsDto.builder()
				.id(entity.getId())
				.content(entity.getContent())
				.postDate(entity.getPostDate())
				.bbsDto(BbsDto.toDto(entity.getBbsEntity()))
				.usersDto(UsersDto.toDto(entity.getUsersEntity()))
				.build();
	}
	
}
