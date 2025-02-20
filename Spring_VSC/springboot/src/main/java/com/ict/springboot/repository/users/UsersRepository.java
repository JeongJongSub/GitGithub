package com.ict.springboot.repository.users;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


/*
	JpaRepository<T, ID>
 	T는 엔터티 타입
 	ID는 엔터티의 @Id를 붙인 필드의 타입
*/

//※@Repository 붙이지 않아도 자동으로 bean으로 등록 된다
// DAO와 같다
@Repository
public interface UsersRepository extends JpaRepository<UsersEntity, String> {
	//[메소드 네임 규칙에 따른 추상 메소드 추가]
	//Spring Data JPA가 메소드 이름 규칙에 따라 쿼리를 자동으로 생성한다
	
	//1.아이디 존재 여부용(WHERE username=?): 서비스단의 중복 아이디 검증용
	boolean existsByUsername(String username);
	
	//※다중 레코드 조회시 반환타입은
	// 페이징 구현시는 Page<UsersEntity>,페이징 미 구현시는 List<UsersEntity>
	// 단일 레코드 조회시 반환타입은 Optional<UsersEntity>이다
	
	//회원 검색용
	//(방법1)각 컬럼별로 조회 쿼리 생성
	Page<UsersEntity> findByNameContaining(String name, PageRequest of);
	Page<UsersEntity> findByUsernameContaining(String username, PageRequest of);
	//(방법2)OR로 조회 쿼리 생성
	Page<UsersEntity> findByNameContainingOrUsernameContaining(String name, String username, PageRequest of);
	//회원정보 상세보기용
	Optional<UsersEntity> findByUsername(String username);
	//회원 여부 판단용
	Optional<UsersEntity> findByUsernameAndPassword(String username, String password);
	

	

}
