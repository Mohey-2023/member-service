package com.mohey.memberservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mohey.memberservice.domain.Member;
import com.mohey.memberservice.domain.MemberInfo;
import com.mohey.memberservice.dto.memberInfo.GetInfoRespDto;
import com.mohey.memberservice.dto.memberInfo.GetYourInfoRespDto;

public interface MemberInfoRepository extends JpaRepository<MemberInfo, Long> {
	@Query("SELECT new com.mohey.memberservice.dto.memberInfo.GetInfoRespDto(" +
		"MI.nickname, " +
		"M.gender, " +
		"M.birthDate, " +
		"MPI.profileUrl, " +
		"MI.selfIntroduction, " +
		"(SELECT COUNT(FR) FROM FriendRelation FR WHERE FR.memberId = M AND FR.friendStatus = true)) " +
		"FROM Member M " +
		"LEFT JOIN MemberInfo MI ON MI.memberId = M AND MI.createdDatetime = (SELECT MAX(mi.createdDatetime) FROM MemberInfo mi WHERE mi.memberId = M) "
		+
		"LEFT JOIN MemberProfileImage MPI ON MPI.memberId = M AND MPI.createdDatetime = (SELECT MAX(mpi.createdDatetime) FROM MemberProfileImage mpi WHERE mpi.memberId = M) "
		+
		"WHERE M.id = :id")
	GetInfoRespDto getUserInfo(@Param("id") Long id);

	@Query("SELECT new com.mohey.memberservice.dto.memberInfo.GetYourInfoRespDto(" +
		"MI.nickname, " +
		"M.gender, " +
		"M.birthDate, " +
		"MPI.profileUrl, " +
		"MI.selfIntroduction, " +
		"(SELECT COUNT(FR) FROM FriendRelation FR WHERE FR.memberId = M AND FR.friendStatus = true), " +
		"FRR.friendStatus, " +
		"FRR.favoriteStatus, " +
		"FRS.status) " +
		"FROM Member M " +
		"LEFT JOIN MemberInfo MI ON MI.memberId = M AND MI.createdDatetime = (SELECT MAX(mi.createdDatetime) FROM MemberInfo mi WHERE mi.memberId = M) "
		+
		"LEFT JOIN MemberProfileImage MPI ON MPI.memberId = M AND MPI.createdDatetime = (SELECT MAX(mpi.createdDatetime) FROM MemberProfileImage mpi WHERE mpi.memberId = M) "
		+
		"LEFT JOIN FriendRelation FRR ON FRR.memberId = :member AND FRR.friendId = :friend "
		+
		"LEFT JOIN FriendRequest FRQ ON FRQ.memberId = :member AND FRQ.responseId = :friend AND FRQ.createdDatetime = (SELECT MAX(frq.createdDatetime) FROM FriendRequest frq WHERE frq.memberId = :member AND frq.responseId = :friend)"
		+
		"LEFT JOIN FriendRequestStatus FRS ON FRS.id = FRQ.id "
		+
		"WHERE M = :friend")
	GetYourInfoRespDto getYourInfo(@Param("member") Member member, @Param("friend") Member friend);

	MemberInfo findMemberInfoByMemberId(Member member);

	Boolean existsByNickname(String name);

}
