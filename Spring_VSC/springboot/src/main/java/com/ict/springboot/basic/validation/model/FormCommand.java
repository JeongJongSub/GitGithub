package com.ict.springboot.basic.validation.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormCommand {
	//※폼의 파라미터명과 속성(필드)명 일치
	
	//이름은 반드시 값을 입력해야 한다(" "(빈 공백)도 유 효성 검증에 실패)
	//messages속성은 유효성 검증 실패시 클라이언트(.html페이지)에서 보여줄 에러 메시지
	@NotBlank(message = "이름을 입력하세요")
	private String name;
	//나이는 반드시 숫자만 입력
	@NotBlank(message = "나이를 입력하세요")
	@Pattern(regexp = "[0-9]{1,3}",message = "나이는 숫자만...")
	private String age;
	@NotNull(message = "성별을 선택하세요")
	private String gender;
	//관심사항은 최소 2개 이상 선택
	@NotNull(message = "관심사항을 선택하세요")
	@Size(min = 2,message = "최소 2개 이상 선택하세요")
	private String[] inter;//※최소(최대) 수 파악을 위해서는 반드시 String[]	
	@NotEmpty(message = "학력을 선택하세요")
	private String grade;
	@NotBlank(message = "파일을 첨부하세요")
	private String file;
}
