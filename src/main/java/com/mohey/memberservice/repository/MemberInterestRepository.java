package com.mohey.memberservice.repository;

import com.mohey.memberservice.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mohey.memberservice.domain.MemberInterest;

import java.util.List;

public interface MemberInterestRepository extends JpaRepository<MemberInterest, Long> {
    List<MemberInterest> findTop3ByMemberIdOrderByCreatedDatetimeDesc(Member member);
}
