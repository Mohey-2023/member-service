package com.mohey.memberservice.repository;

import com.mohey.memberservice.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mohey.memberservice.domain.MemberDevice;

import java.util.List;

public interface MemberDeviceRepository extends JpaRepository<MemberDevice, Long> {
    List<MemberDevice> findMemberDevicesByMemberId(Member member);
}
