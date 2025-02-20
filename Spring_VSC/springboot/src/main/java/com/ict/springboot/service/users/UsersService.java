package com.ict.springboot.service.users;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ict.springboot.repository.users.UsersEntity;
import com.ict.springboot.repository.users.UsersRepository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

/*
	@RequiredArgsConstructor  어노테이션
	
	@Autowired나 @Resource어노테이션을 통한 주입이 아닌 
	인자 생성자를 통한 주입(권장)
    주입 받고자 하는 필드는 반드시 final로 선언

*/
@Service
@RequiredArgsConstructor
public class UsersService {
	
	
	//1]리파지토리 주입
	//필드 인젝션
	@Autowired
	private final UsersRepository usersRepository;	
	
	
	//<<페이징 미 구현>>
	public List<UsersDto> usersAll(){	
		
		//1)리파지토리의 CRUD 메소드 호출
		//미 정렬
		//List<UsersEntity> users= usersRepository.findAll();	
		//가입일로 내림차순 정렬:정렬 기준이되는 문자열은 반드시 엔터티 필드명을 사용
		List<UsersEntity> users= usersRepository.findAll(Sort.by(Sort.Direction.DESC,"regiDate"));
		//2)엔터티를 DTO로 변환하여 반환한다		
		List<UsersDto> usersDto= users
				.stream()
				.map(user->UsersDto.toDto(user)).collect(Collectors.toList());
		return usersDto;
	}
	//<<페이징 구현>>
	public Page<UsersDto> usersPagingAll(Pageable pageable) {
		
		
		//findAll()에 Pageable 객체를 전달하여 페이징 처리를 수행하도록 한다
		//이때 인자로 전달받은 Pageable객체의 정보를 리포지토리로 전달한다
		//1)Pageable객체를 리포지토리에 그대로 전달
		//  현재 페이지 번호 1로 설정 후 그대로 리포지토리에 전달시 
		//  리포지토리에서 2페이지 데이타가 반환된다 
		//Page<UsersEntity> users=usersRepository.findAll(pageable);
		
		
		//2)PageRequest.of()메소드로 Pageable객체를 생성해서 리포지토리에 전달		
		//PageRequest.of(int page, int size, Sort sort)
		//page : 반드시 0부터 시작하는 페이지 번호(0부터 시작, 기본값 0) 즉 0 페이지 데이타를 리포지토리로 받기 위함
		//size: 페이지당 표시할 데이터 갯수 0보다 커야 한다 (기본값 10)
		//sort :정렬 정보를 담은 Sort 객체. null이 아니어야 한다 정렬하지 않을 경우 Sort.unsorted()를 지정한다
		Page<UsersEntity> users=usersRepository.findAll(PageRequest.of(pageable.getPageNumber()-1,pageable.getPageSize(), pageable.getSort()));
		
		//Page<UsersEntity>를 Page<UsersDTO>로 변환		
		return users.map(userEntity->UsersDto.toDto(userEntity));
	}

	//회원가입 처리(Insert)
	public boolean signup(UsersDto dto) {
		//[리포 지토리 호출]
		//아이디 중복 여부 판단
		boolean isDuplicated=usersRepository.existsByUsername(dto.getUsername());
		if(isDuplicated) return false;//아이디가 중복된 경우
		
		//아이디가 중복 안된 경우 - 회원 가입 처리
		//※스프링 시큐리티 적용시 비밀번호 암호화 해야 한다
		//DTO를 엔터티로 변환
		UsersEntity usersEntity= dto.toEntity();
		
		//※스프링 시큐리티 적용시 테스트용 ROLE 추가(하드 코딩):권한을 조건에 따라 부여 한다
		usersEntity.setRole("USER");
		/*
			signup()메소드에서는 INSERT만 할 것이다 
			따라서 @Transactional를 붙일 필요 없다
			※UsersRepository인터페이스 즉 Spring Data JPA의 save()메소드는 
			INSERT/UPDATE 쿼리를 실행한다
		 	엔터티 객체가 영속성 컨텍스트에 없는 경우 INSERT
		 	있는 경우 UPDATE 쿼리가 실행 된다.	 
		 	
		 */
		usersRepository.save(usersEntity);
		
		return true;
	}
	//회원 검색용
	public Page<UsersDto> searchUsers(Map<String, String> map, Pageable pageable) {
		Page<UsersEntity> paginUsers=null;
		//[리포지토리 호출]
		/*
		//(방법1)각 컬럼별로 쿼리 생성		
		//findByNameContaining("검색어",Pageable객체) 메소드 이름 규칙에 의해
		//WHERE name LIKE '%' || '검색어' || '%' 쿼리가 생성된다
		if("name".equalsIgnoreCase(map.get("searchColumn")))//이름으로 검색
			paginUsers = usersRepository.findByNameContaining(
											map.get("searchWord"),
											PageRequest.of(pageable.getPageNumber()-1,
											pageable.getPageSize(),
											pageable.getSort()));
		//findByUsernameContaining("검색어",Pageable객체) 메소드 이름 규칙에 의해
		//WHERE username LIKE '%' || '검색어' || '%' 쿼리가 생성된다
		else if("username".equalsIgnoreCase(map.get("searchColumn")))//아이디로 검색
			paginUsers = usersRepository.findByUsernameContaining(
					map.get("searchWord"),
					PageRequest.of(pageable.getPageNumber()-1,
					pageable.getPageSize(),
					pageable.getSort()));
		*/
		//(방법2) OR로 쿼리 생성
		//WHERE username LIKE '%' || '검색어' || '%' OR  name LIKE '%' || '검색어' || '%' 쿼리가 생성된다
		
		paginUsers = usersRepository.findByNameContainingOrUsernameContaining(
										map.get("searchWord"),
										map.get("searchWord"),
										PageRequest.of(pageable.getPageNumber()-1,
										pageable.getPageSize(),
										pageable.getSort()));
		return paginUsers.map(user->UsersDto.toDto(user));
	}
	//회원 상세보기용
	public UsersDto userDetail(String username) {
		//리포지토리 호출:java.util.Optional
		Optional<UsersEntity> userOptional= usersRepository.findByUsername(username);
		//UsersEntity를 UsersDto로 변환해서 컨트롤러에게 전달	
		/*
		if(userOptional.isPresent()) 
			return UsersDto.toDto(userOptional.get());
		else		
			return null;
		*/
		UsersEntity user = userOptional.orElseGet(()->null);	
		return UsersDto.toDto(user);
		
	}
	//회원여부 판단
	public boolean isUsers(UsersDto dto) {
		//리포지토리 호출		
		Optional<UsersEntity> userOptional= usersRepository.findByUsernameAndPassword(dto.getUsername(),dto.getPassword());
		UsersEntity user = userOptional.orElseGet(()->null);
		if(user==null) return false;
		return true;
	}/////

	
	
}
