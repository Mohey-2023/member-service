package com.mohey.memberservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mohey.memberservice.domain.FriendRelation;
import com.mohey.memberservice.domain.Member;
import com.mohey.memberservice.dto.memberFriend.FriendInFriendListSearchRespDto;
import com.mohey.memberservice.dto.memberFriend.FriendListSearchRespDto;

public interface FriendSearchRepository extends JpaRepository<FriendRelation, Long> {

	//FriendRelation에 friendId와 memberId는 둘다 member 타입이다 !!!!!!!제발주의해
	//내가 친구요청을 보낸 사용자면 친구를 가져오는데 친구요청을 받은 사용자면 가져오지 못한다

	//친구상태 false 추가
	@Query("SELECT FR.friendId FROM FriendRelation FR WHERE FR.memberId.id = :memberId AND FR.friendStatus != false ")
	List<Member> findAllByMemberId(@Param("memberId") Long memberId);

	@Query("SELECT new com.mohey.memberservice.dto.memberFriend.FriendListSearchRespDto(m.memberUuid, mi.nickname,  m.gender, m.birthDate, mpi.profileUrl, fr.favoriteStatus) " +
			"FROM FriendRelation fr " +
			"JOIN fr.friendId m " +
			"LEFT JOIN MemberInfo mi on m.id = mi.memberId.id " +
			"LEFT JOIN MemberProfileImage mpi on m.id = mpi.memberId.id " +
			"WHERE fr.memberId.id = :memberId " +
			"AND fr.friendStatus = TRUE")
	List<FriendListSearchRespDto> findFriendDetailsByMemberId(Long memberId);

	@Query("SELECT new com.mohey.memberservice.dto.memberFriend.FriendListSearchRespDto(" +
		"M.memberUuid, " +
		"MI.nickname," +
		"M.gender, " +
		"M.birthDate," +
		"MPI.profileUrl," +
		"FR.favoriteStatus)" +
		"FROM Member M " +
		"LEFT JOIN MemberInfo MI ON MI.memberId = M AND MI.createdDatetime = (SELECT MAX(mi.createdDatetime) FROM MemberInfo mi WHERE mi.memberId = M)"
		+
		"LEFT JOIN MemberProfileImage MPI ON MPI.memberId = M AND MPI.createdDatetime = (SELECT MAX(mpi.createdDatetime) FROM MemberProfileImage mpi WHERE mpi.memberId = M)"
		+
		"LEFT JOIN FriendRelation FR ON FR.friendStatus = true AND FR.favoriteStatus = false AND FR.friendId = M AND FR.memberId = :friendId " +
		"WHERE M.id = :id"
	)
	List<FriendListSearchRespDto> findNotFavoriteFriendList(@Param("id") Long id,@Param("friendId") Member friendId);

	//내..정보만 가져옴 심지어 두 개임 디채ㅔ 왜?
	//내 멤버아이디로 가져오라고 했으니까 그러지....

	@Query("SELECT new com.mohey.memberservice.dto.memberFriend.FriendListSearchRespDto(" +
		"M.memberUuid, " +
		"MI.nickname," +
		"M.gender, " +
		"M.birthDate," +
		"MPI.profileUrl," +
		"FR.favoriteStatus)" +
		"FROM Member M " +
		"LEFT JOIN MemberInfo MI ON MI.memberId = M AND MI.createdDatetime = (SELECT MAX(mi.createdDatetime) FROM MemberInfo mi WHERE mi.memberId = M)"
		+
		"LEFT JOIN MemberProfileImage MPI ON MPI.memberId = M AND MPI.createdDatetime = (SELECT MAX(mpi.createdDatetime) FROM MemberProfileImage mpi WHERE mpi.memberId = M)"
		+
		"INNER JOIN FriendRelation FR ON FR.friendStatus = true AND FR.favoriteStatus = true AND FR.friendId = M AND FR.memberId =:friendId " +
		"WHERE M.id = :id"
	)
	List<FriendListSearchRespDto> findFavoriteFriendList(@Param("id") Long id,@Param("friendId") Member friendId);

	@Query("SELECT new com.mohey.memberservice.dto.memberFriend.FriendInFriendListSearchRespDto(m.memberUuid, mi.nickname,  m.gender, m.birthDate, mpi.profileUrl, fr.favoriteStatus) " +
			"FROM FriendRelation fr " +
			"JOIN fr.friendId m " +
			"INNER JOIN MemberInfo mi on m.id = mi.memberId.id and mi.nickname LIKE :nickname " +
			"LEFT JOIN MemberProfileImage mpi on m.id = mpi.memberId.id " +
			"WHERE fr.memberId.id = :memberId " +
			"AND fr.friendStatus = TRUE")
	List<FriendInFriendListSearchRespDto> findFriendInFriendDetailsByNickname(String nickname, Long memberId);

	@Query("SELECT MI.memberId FROM MemberInfo MI " +
			"INNER JOIN FriendRelation FR ON FR.memberId = :memberId AND FR.friendId = MI.memberId AND FR.friendStatus = true " +
			"WHERE MI.nickname LIKE :nickname")
	List<Member> findAllByNickname(@Param("nickname") String nickname, @Param("memberId") Member memberId);

	@Query("SELECT new com.mohey.memberservice.dto.memberFriend.FriendInFriendListSearchRespDto(" +
		"M.memberUuid, " +
		"MI.nickname," +
		"M.gender, " +
		"M.birthDate," +
		"MPI.profileUrl," +
		//"FR.friendStatus,"+
		"FR.favoriteStatus)" +
		"FROM Member M " +
		"INNER JOIN FriendRelation FR ON FR.memberId = :memberId AND FR.friendStatus = true AND FR.favoriteStatus = true "
		+
		"LEFT JOIN MemberInfo MI ON MI.memberId = M AND MI.createdDatetime = (SELECT MAX(mi.createdDatetime) FROM MemberInfo mi WHERE mi.memberId = M)"
		+
		"LEFT JOIN MemberProfileImage MPI ON MPI.memberId = M AND MPI.createdDatetime = (SELECT MAX(mpi.createdDatetime) FROM MemberProfileImage mpi WHERE mpi.memberId = M)"
		+
		"WHERE M.id = :id"
	)
	List<FriendInFriendListSearchRespDto> findFavoriteFriendById(@Param("id") Long id,
		@Param("memberId") Member memberId);

	@Query("SELECT new com.mohey.memberservice.dto.memberFriend.FriendInFriendListSearchRespDto(" +
		"M.memberUuid, " +
		"MI.nickname," +
		"M.gender, " +
		"M.birthDate," +
		"MPI.profileUrl," +
		"FR.favoriteStatus)" +
		"FROM Member M " +
		"LEFT JOIN MemberInfo MI ON MI.memberId = M AND MI.createdDatetime = (SELECT MAX(mi.createdDatetime) FROM MemberInfo mi WHERE mi.memberId = M)"
		+
		"LEFT JOIN MemberProfileImage MPI ON MPI.memberId = M AND MPI.createdDatetime = (SELECT MAX(mpi.createdDatetime) FROM MemberProfileImage mpi WHERE mpi.memberId = M)"
		+
		"INNER JOIN FriendRelation FR ON FR.friendStatus = true AND FR.favoriteStatus = false AND FR.memberId = :memberId "
		+
		"WHERE M.id = :id"
	)
	List<FriendInFriendListSearchRespDto> findNotFavoriteFriendById(@Param("id") Long id,
		@Param("memberId") Member memberId);

}
