package com.mohey.memberservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mohey.memberservice.domain.MemberInterest;

public interface MemberInterestRepository extends JpaRepository<MemberInterest, Long> {

}
