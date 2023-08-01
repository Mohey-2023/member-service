package com.mohey.memberservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mohey.memberservice.domain.MemberDevice;

public interface MemberDeviceRepository extends JpaRepository<MemberDevice, Long> {

}
