package com.ict.springboot.repository.bbs;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ict.springboot.repository.users.UsersEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "BBS",uniqueConstraints = @UniqueConstraint(columnNames = "id",name = "PK_BBS"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BbsEntity {
	
	@Id
	@SequenceGenerator(name = "SEQ_BBS",sequenceName="SEQ_BBS",allocationSize = 1,initialValue = 1 )
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "SEQ_BBS")
	private long id;
	
	@Column(nullable = false,length = 100)
	private String title;
	
	@Column(nullable = false,length = 2000)
	private String content;
	
	@ColumnDefault("SYSDATE")
	@CreationTimestamp
	private LocalDateTime postDate;
	
	
	//연관관계 설정
	
	//<<사용자와의 관계>>
	//optional = false(기본값은 true)
	//해당 연관 관계가 선택 사항(optional)이 아니고 필수임을 의미. 
	//즉 자식 엔티티(BbsEntity)는 반드시 1 개의 다른 부모 엔티티(Users)와 관련 되어야 한다
	//데이터베이스 컬럼(users_username)은 NULLABLE NO로 설정 된다
	//기본값은 true
	@ManyToOne(optional = false)
	//@JoinColumn 어노테이션은 외래 키를 매핑 할 때 사용
	// name 속성에는 매핑 할 외래 키 이름을 지정(부모타입필드명_부모타입PK필드명:기본값으로 생략가능)
	// 테이블 자동 생성시 users_username라는 컬럼이 생기고 FK로 설정 된다	
	@JoinColumn(name = "users_username",foreignKey = @ForeignKey(name="FK_USERS_USERNAME_BBS"))	
	private UsersEntity userEntity;
	
	//<<한줄 코멘트와의 관계>>
	@OneToMany(mappedBy = "bbsEntity")//Comments 엔터티와 양방향	
	@OrderBy("id DESC")//Comments의 id컬럼으로 정렬해서 가져오기
	//@JsonIgnore
	private List<CommentsEntity> comments;
	/*
	  -@JsonIgnore를 붙인 이유
	  
	  	GET http://localhost:8080/comments/1 요청시
	  	[org.springframework.http.converter.HttpMessageNotWritableException: Could not write JSON: Infinite recursion (StackOverflowError)에러 발생
	 
	   	원인:
			JPA 연관관계에서 양방향 매핑을 선언한 경우 발생
			Comments엔터티에서 @ManyToOne으로  private BBS bbs; 선언후
			BBS엔터티에서 @OneToMany 로 private List<Comments> comments; 선언시
			Comments를 JSON포맷으로 변환시 발생
	
	   해결:
	   		Comments를 JSON으로 변환시 Comments가 포함되지 않도록
	   		BBS엔터티에서 @JsonIgnore 추가
	 
	 */
}
