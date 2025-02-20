package com.ict.springboot.repository.bbs;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import com.ict.springboot.repository.users.UsersEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="COMMENTS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@DynamicUpdate//Update시 변경된 필드만으로 동적 쿼리 생성(디폴트는 모든 컬럼 사용)
/*
 * Hibernate: 
    update
        Comments 
    set
        content=? 
    where
        id=?
    @DynamicUpdate 위 주석 처리시
    update
        Comments 
    set
        bbs_id=?,
        content=?,
        postDate=?,
        users_username=? 
    where
        id=?
*/
public class CommentsEntity {
	
	@Id
	@SequenceGenerator(name="seq_comments",sequenceName = "seq_comments",initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq_comments")
	private Long id;
	
	@Column(nullable = false,length = 100)
	private String content;
	
	@ColumnDefault("SYSDATE")
	@CreationTimestamp
	private LocalDateTime postDate;
	
	//Comments 테이블은 FK가 두 개가 된다
	//연관관계 설정
	@ManyToOne(optional = false)
	@JoinColumn(name="bbs_id")//부모타입필드명_부모타입PK필드명 이 기본값 즉 bbsEntity_id 컬럼이 생기고 FK로 설정된다
	private BbsEntity bbsEntity;
	
	@ManyToOne(optional = false)
	@JoinColumn(name="users_username")//users_username라는 컬럼이 생기고 FK로 설정된다
	private UsersEntity usersEntity;
	
	
}
