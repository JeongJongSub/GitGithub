package com.ict.springboot.service.users;

import java.time.LocalDateTime;

import com.ict.springboot.repository.users.UsersEntity;

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
public class UsersDto {
	//※Users 엔터티의 필드중 데이타 전달에 필요한 필드만 지정
	// ROLE 필드은 제외
	private String username;
	private String password;
	private String name;
	private LocalDateTime regiDate;
	
	//[DTO를 ENTITY로 변환하는 메소드]
	public UsersEntity toEntity() {
		return UsersEntity
				.builder()
				.name(name)
				.username(username)
				.password(password)
				.regiDate(regiDate)
				.build();
	}
	//[ENTITY를 DTO로 변환하는 메소드]
	public static UsersDto toDto(UsersEntity usersEntity) {
		return UsersDto.builder()
				.name(usersEntity.getName())
				.username(usersEntity.getUsername())
				.password(usersEntity.getPassword())
				.regiDate(usersEntity.getRegiDate())
				.build();
	}
}
