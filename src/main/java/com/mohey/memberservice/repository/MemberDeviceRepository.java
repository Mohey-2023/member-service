package com.mohey.memberservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mohey.memberservice.domain.Member;
import com.mohey.memberservice.domain.MemberDevice;

public interface MemberDeviceRepository extends JpaRepository<MemberDevice, Long> {
	List<MemberDevice> findMemberDevicesByMemberId(Member member);

	MemberDevice findMemberDeviceByDeviceUuid(String DeviceUuid);
}
