package com.ict.springboot.repository.bbs;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository //생략 가능
public interface BbsRepository extends JpaRepository<BbsEntity, Long> {
	
}
