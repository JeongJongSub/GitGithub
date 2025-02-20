package com.ict.springboot.repository.users;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.ict.springboot.repository.bbs.BbsEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//Spring Data JPA관련 어노테이션
@Entity
@Table(name = "Users")
//Lombok어노 테이션
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsersEntity {

	
	/*
	@SequenceGenerator 속성
		name : @GeneratedValue에서 지정할 수 있는 시퀀스 제너레이터 식별자명(필수)
		sequenceName : 데이터베이스 테이블의  시퀀스명.(기본값은 hibernate_sequence)
		initialValue : DDL 자동 생성시 start 와 같은 속성(기본값 1)
		allocationSize : JPA에서 가상으로 관리할 시퀀스 할당 범위(기본값 50이며 1로 설정시 매번 insert시마다 DB의 시퀀스 호출)
	
	@Id
	@SequenceGenerator(name = "seq_users",sequenceName = "seq_users",allocationSize = 1,initialValue = 1)
	@GeneratedValue(generator = "seq_users",strategy = GenerationType.SEQUENCE)
	private long id;
	*/
	
	@Id
	@Column(length = 20)
	private String username;
	@Column(length = 255,nullable = false)
	private String password;
	@Column(length = 20,nullable = false)
	private String name;
	
	
	//인가용 필드(여러 역할 저장시는 String[])
	@Column(length = 20,nullable = false)
	private String role;
	
	//[입력 날짜로 자동 설정하기]
	//(방법1)
	//@ColumnDefault("SYSDATE")
	@CreationTimestamp
	private LocalDateTime regiDate;
	
	
	
	/*
	//(방법2)
	@CreationTimestamp
	@Column(nullable = false,updatable = false)	
	private java.sql.Date regiDate;*/
	
	/*
	//(방법3)
	@Column(nullable = false,updatable = false)	
	private java.sql.Date regiDate;
	@PrePersist //영속화 되기전 발생하는 이벤트
    public void setCreateDate() {
        this.regiDate = new java.sql.Date(new Date().getTime()); 
    }*/
	
	//[연관 관계 맺기]
	//※회원 정보에는 게시글 및 댓글을 표시하지 않을 것임으로 단방향으로 하자
	
	//생략시 단방향 연관관계	
	//@OneToMany(mappedBy = "userEntity")
	//private List<BbsEntity> bbses;
	
	
}
