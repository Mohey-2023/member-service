package com.mohey.memberservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mohey.memberservice.domain.MemberProfileImage;

public interface MemberProfileRepository extends JpaRepository<MemberProfileImage, Long> {

}
