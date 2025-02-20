package com.ict.springboot.service.bbs;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ict.springboot.repository.bbs.BbsEntity;
import com.ict.springboot.repository.bbs.BbsRepository;
import com.ict.springboot.repository.bbs.CommentsEntity;
import com.ict.springboot.repository.bbs.CommentsRepository;
import com.ict.springboot.repository.users.UsersEntity;

@Service
public class BbsService {
	
	//[리포지토리 주입]
	@Autowired
	private BbsRepository bbsRepository;
	
	@Autowired
	private CommentsRepository cRepository;
	
	//전체 목록
	public Page<BbsDto> bbsAll(Pageable pageable){
		//리포 지토리 호출
		Page<BbsEntity> bbsEntities= bbsRepository.findAll(PageRequest.of(
				pageable.getPageNumber()-1, 
				pageable.getPageSize(), 
				pageable.getSort()));
		//Page<BbsEntity>를 Page<BbsDto>로 변환		
		return bbsEntities.map(bbsEntity->BbsDto.toDto(bbsEntity));
	}
	//입력용
	//글 번호가 설정된 DTO 반환
	//※인자로 전달된 BbsDto에 @Id가 붙은 필드인 id값이 없음으로 save() 호출시 
	//insert문 자동 생성
	/*
	 1)먼저 시퀀스 값 조회
	 	select
        	SEQ_BBS.nextval 
     	from
        	dual
	 2)INSERT쿼리 생성
	 	insert 
    	into
        BBS
        (content, postDate, title, users_username, id) 
    	values
        (?, ?, ?, ?, ?)
	 */
	public BbsDto saveBbs(BbsDto dto) {
		//리포지토리 호출		
		//BbsDto의 UsersEntity userEntity필드 미 설정시 
		//NULL을 ("SPRING"."BBS"."USERS_USERNAME") 안에 삽입할 수 없습니다  에러
		
		BbsEntity bbsEntity= bbsRepository.save(dto.toEntity());
		//BbsEntity를 BbsDto로 변환해서 반환
		return BbsDto.toDto(bbsEntity);
	}
	//게시글 상세보기용
	public BbsDto bbsOneById(long id) {
		//리포지토리 호출	
		BbsEntity bbsEntity= bbsRepository.findById(id).orElseThrow(()->new IllegalArgumentException(id +"는 존재하지 않아요"));
		//List<CommentsEntity> comments= cRepository.findAllById(List.of(id));
		//bbsEntity.setComments(comments);
		System.out.println("bbsOneById-코멘트 목록:"+bbsEntity.getComments());
		return BbsDto.toDto(bbsEntity);
	}
	//수정용
	//수정된 DTO 반환
	/*
	인자로 전달된 BbsDto에 id값이 있음으로 UPDATE문 생성(방법1:save()호출시)
	update
        BBS 
    set
        postDate=?,
        title=?,
        users_username=?,
        content=? 
    where
        id=?
	  
	 */
	//수정 방법
	//(방법1)save() 호출
	//1.findById(아이디) 호출 : 영속성 컨텍스트에 Bbs객체가 영속화 된다
	//  이때 스냅샵이 생성된다
	//2.영속화된 Bbs객체를 변경할 사항만 setter로 수정 
	//3.save()호출
	//  이때 아이디 값이 DTO에 있음으로 UPFATE쿼리가 
	//  쓰기 지연 SQL저장소에 저장되고 이 SQL이 실제 DB에 전송 된다	
	//(방법2)save() 미 호출
	//방법1의 2번까지 동일
	//3.메소드에 @Transactional 부착
	//  메소드 시작시 트랜잭션이 시작되고
	//  메소드 종료시 트랜잭션이 종료된다
	//  이때 Flush가 발생해 쓰기 지연 SQL저장소에 저장된 UPDATE쿼리가 실제 DB에 전송된다
	//  ※더티 체킹은 영속성 컨텍스트에 저장된 엔터티 객체와 스냅샵(영속화 될때의 복사본)의 
	//    차이를 체킹하는 거를 더티체킹 이라 한다 	
	/*
	//방법1)
	public BbsDto update(BbsDto dto) {
		//영속화(이때 영속화된 스냅샵이 생성 된다)
		BbsEntity bbsEntity= bbsRepository.findById(dto.getId()).orElseThrow(()->new IllegalArgumentException("해당하는 게시글 아이디가 없습니다"));
		//변경할 사항만 Setter로 수정
		bbsEntity.setTitle(dto.getTitle());
		bbsEntity.setContent(dto.getContent());
		//save() 호출
		bbsRepository.save(bbsEntity);
		
		return BbsDto.toDto(bbsEntity);
	}*/
	//방법2)
	@Transactional
	public BbsDto update(BbsDto dto) {
		//영속화(이때 영속화된 스냅샵이 생성 된다)
		BbsEntity bbsEntity= bbsRepository.findById(dto.getId()).orElseThrow(()->new IllegalArgumentException("해당하는 게시글 아이디가 없습니다"));
		//변경할 사항만 Setter로 수정
		bbsEntity.setTitle(dto.getTitle());
		bbsEntity.setContent(dto.getContent());		
		return BbsDto.toDto(bbsEntity);
	}/////
	
}
