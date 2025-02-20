package com.ict.springboot.basic.thymeleaf.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthUser {
	private String username;
	private String password;
	private String name;
	private LocalDateTime regidate;
}
