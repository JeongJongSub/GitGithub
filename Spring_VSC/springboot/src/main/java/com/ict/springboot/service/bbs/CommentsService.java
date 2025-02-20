package com.ict.springboot.service.bbs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ict.springboot.repository.bbs.CommentsEntity;
import com.ict.springboot.repository.bbs.CommentsRepository;

@Service
public class CommentsService {

	//필드 인젝션
	@Autowired
	private CommentsRepository cRepository;

	public CommentsDto write(CommentsDto commentsDto) {
		//리포지토리 호출
		CommentsEntity entity= cRepository.save(commentsDto.toEntity());
		return CommentsDto.toDto(entity);
	}
	
}
