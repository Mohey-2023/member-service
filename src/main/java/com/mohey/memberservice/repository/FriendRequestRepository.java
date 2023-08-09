package com.mohey.memberservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mohey.memberservice.domain.FriendRequest;
import com.mohey.memberservice.dto.memberalarm.FriendRespAlarmRespDto;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
	FriendRequest findFriendRequestByAlarmUuid(String alarmUuid);

	@Query("SELECT new com.mohey.memberservice.dto.memberalarm.FriendRespAlarmRespDto(" +
		"FR.alarmUuid, " +
		"M.memberUuid, " +
		"MPI.profileUrl, " +
		"MI.nickname, " +
		"M.birthDate, " +
		"M.gender) " +
		"FROM FriendRequest FR " +
		"JOIN FR.memberId M " +
		"LEFT JOIN MemberProfileImage MPI ON MPI.memberId = M AND MPI.createdDatetime = (SELECT MAX(mpi.createdDatetime) FROM MemberProfileImage mpi WHERE mpi.memberId = M.id) "
		+
		"LEFT JOIN MemberInfo MI ON MI.memberId = M AND MI.createdDatetime = (SELECT MAX(mi.createdDatetime) FROM MemberInfo mi WHERE mi.memberId = M.id) "
		+
		"INNER JOIN FriendRequestStatus FRS ON FRS.friendRequest.id = FR.id AND FRS.status = 'WAIT' " +
		"WHERE FR.responseId.id = :id")
	List<FriendRespAlarmRespDto> getFriendReqList(@Param("id") Long id);

}
