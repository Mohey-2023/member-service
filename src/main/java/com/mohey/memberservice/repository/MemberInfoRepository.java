package com.mohey.memberservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mohey.memberservice.domain.MemberInfo;
import com.mohey.memberservice.dto.memberInfo.getInfoRespDto;

public interface MemberInfoRepository extends JpaRepository<MemberInfo, Long> {
	@Query(value = "SELECT M.gender AS gender, " +
		"MI.nickname AS nickname, " +
		"MI.self_introduction AS selfIntroduction, " +
		"MPI.profile_url AS profileUrl, " +
		"(SELECT COUNT(*) FROM friend_relation_tb FR WHERE FR.member_tb_id = M.id AND FR.friend_status = 1) AS friendCount "
		+
		"FROM member_tb M " +
		"LEFT JOIN member_info_tb MI ON MI.id = ( " +
		"    SELECT id FROM member_info_tb WHERE member_tb_id = M.id " +
		"    ORDER BY created_datetime DESC LIMIT 1) " +
		"LEFT JOIN member_profile_image_tb MPI ON MPI.id = ( " +
		"    SELECT id FROM member_profile_image_tb WHERE member_tb_id = M.id " +
		"    ORDER BY created_datetime DESC LIMIT 1) " +
		"WHERE M.id = :id", nativeQuery = true)
	getInfoRespDto getUserInfo(@Param("id") Long id);
}
