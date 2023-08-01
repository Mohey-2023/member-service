package com.mohey.memberservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mohey.memberservice.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
