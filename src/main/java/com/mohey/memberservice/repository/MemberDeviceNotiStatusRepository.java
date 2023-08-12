package com.mohey.memberservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mohey.memberservice.domain.MemberDevice;
import com.mohey.memberservice.domain.MemberDeviceNotiStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberDeviceNotiStatusRepository extends JpaRepository<MemberDeviceNotiStatus, Long> {
	Optional<MemberDeviceNotiStatus> findFirstByMemberDeviceIdOrderByCreatedDatetimeDesc(MemberDevice memberDeviceId);

	@Query("SELECT m.notiStatus FROM MemberDeviceNotiStatus m WHERE m.memberDeviceId.deviceUuid = :deviceUuid AND m.createdDatetime = (SELECT MAX(m2.createdDatetime) FROM MemberDeviceNotiStatus m2 WHERE m2.memberDeviceId.deviceUuid = :deviceUuid)")
	Boolean findNotiStatusByDeviceUuid(@Param("deviceUuid") String deviceUuid);


}
