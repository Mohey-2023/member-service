package com.mohey.memberservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mohey.memberservice.domain.Member;
import com.mohey.memberservice.domain.MemberDevice;
import com.mohey.memberservice.dto.memberalarm.NotiTokenDto;

public interface MemberDeviceRepository extends JpaRepository<MemberDevice, Long> {
	List<MemberDevice> findMemberDevicesByMemberId(Member member);


	MemberDevice findMemberDeviceByDeviceUuid(String DeviceUuid);

//	@Query("SELECT md.deviceToken, nd.notiStatus, ad.aliveStatus "
//		+ "FROM MemberDevice md "
//		+ "JOIN md.memberDeviceNotiStatus nd "
//		+ "JOIN md.memberDeviceAliveStatus ad "
//		+ "WHERE md.memberId = :member")
//	List<NotiTokenDto> getDeviceTokenAndStatus(@Param("member") Member member);

	@Query("SELECT md.deviceToken "
			+ "FROM MemberDevice md "
			+ "WHERE md.memberId = :member "
			+ "AND EXISTS ("
			+ "    SELECT 1 FROM MemberDeviceNotiStatus nd "
			+ "    WHERE nd.memberDeviceId = md "
			+ "    AND nd.notiStatus = true "
			+ "    AND nd.createdDatetime = ("
			+ "        SELECT MAX(subNd.createdDatetime) "
			+ "        FROM MemberDeviceNotiStatus subNd "
			+ "        WHERE subNd.memberDeviceId = md "
			+ "    )"
			+ ")"
			+ "AND EXISTS ("
			+ "    SELECT 1 FROM MemberDeviceAliveStatus ad "
			+ "    WHERE ad.memberDeviceId = md "
			+ "    AND ad.aliveStatus = true "
			+ "    AND ad.createdDatetime = ("
			+ "        SELECT MAX(subAd.createdDatetime) "
			+ "        FROM MemberDeviceAliveStatus subAd "
			+ "        WHERE subAd.memberDeviceId = md "
			+ "    )"
			+ ")")
	List<String> getDeviceToken(@Param("member") Member member);


}
