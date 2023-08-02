package com.mohey.memberservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mohey.memberservice.domain.MemberInfo;
import com.mohey.memberservice.dto.memberInfo.GetInfoRespDto;

public interface MemberInfoRepository extends JpaRepository<MemberInfo, Long> {
	@Query("SELECT new com.mohey.memberservice.dto.memberInfo.GetInfoRespDto(" +
			"MI.nickname, " +
			"M.gender, " +
			"M.birthDate, " +
			"MPI.profileUrl, " +
			"MI.selfIntroduction, " +
			"(SELECT COUNT(FR) FROM FriendRelation FR WHERE FR.memberId = M AND FR.friendStatus = true)) " +
			"FROM Member M " +
			"LEFT JOIN MemberInfo MI ON MI.memberId = M AND MI.createdDatetime = (SELECT MAX(mi.createdDatetime) FROM MemberInfo mi WHERE mi.memberId = M) " +
			"LEFT JOIN MemberProfileImage MPI ON MPI.memberId = M AND MPI.createdDatetime = (SELECT MAX(mpi.createdDatetime) FROM MemberProfileImage mpi WHERE mpi.memberId = M) " +
			"WHERE M.id = :id")
	GetInfoRespDto getUserInfo(@Param("id") Long id);


}
