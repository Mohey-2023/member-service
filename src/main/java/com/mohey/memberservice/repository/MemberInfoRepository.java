package com.mohey.memberservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mohey.memberservice.domain.MemberInfo;

public interface MemberInfoRepository extends JpaRepository<MemberInfo, Long> {
	
}
