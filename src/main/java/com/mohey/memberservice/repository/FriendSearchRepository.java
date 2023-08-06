package com.mohey.memberservice.repository;

import com.mohey.memberservice.domain.Member;
import com.mohey.memberservice.domain.MemberInfo;
import com.mohey.memberservice.dto.memberFriend.FriendInFriendListSearchRespDto;
import com.mohey.memberservice.dto.memberFriend.FriendListSearchRespDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendSearchRepository extends JpaRepository<MemberInfo, Long> {
    @Query("SELECT new com.mohey.memberservice.dto.memberFriend.FriendListSearchRespDto("+
            "M.memberUuid, "+
            "MI.nickname," +
            "M.gender, "+
            "M.birthDate,"+
            "MPI.profileUrl,"+
            "FR.favoriteStatus)"+
            "FROM Member M " +
            "LEFT JOIN MemberInfo MI ON MI.memberId = M AND MI.createdDatetime = (SELECT MAX(mi.createdDatetime) FROM MemberInfo mi WHERE mi.memberId = M)"+
            "LEFT JOIN MemberProfileImage MPI ON MPI.memberId = M AND MPI.createdDatetime = (SELECT MAX(mpi.createdDatetime) FROM MemberProfileImage mpi WHERE mpi.memberId = M)"+
            "LEFT JOIN FriendRelation FR ON FR.friendStatus = true AND FR.favoriteStatus = false "+
            "WHERE M.id = :id"
    )
    List<FriendListSearchRespDto> findNotFavoriteFriendList(@Param("id") Long id);
    @Query("SELECT new com.mohey.memberservice.dto.memberFriend.FriendListSearchRespDto("+
            "M.memberUuid, "+
            "MI.nickname," +
            "M.gender, "+
            "M.birthDate,"+
            "MPI.profileUrl,"+
            "FR.favoriteStatus)"+
            "FROM Member M " +
            "LEFT JOIN MemberInfo MI ON MI.memberId = M AND MI.createdDatetime = (SELECT MAX(mi.createdDatetime) FROM MemberInfo mi WHERE mi.memberId = M)"+
            "LEFT JOIN MemberProfileImage MPI ON MPI.memberId = M AND MPI.createdDatetime = (SELECT MAX(mpi.createdDatetime) FROM MemberProfileImage mpi WHERE mpi.memberId = M)"+
            "LEFT JOIN FriendRelation FR ON FR.friendStatus = true AND FR.favoriteStatus = true "+
            "WHERE M.id = :id"
    )
    List<FriendListSearchRespDto> findFavoriteFriendList(@Param("id") Long id);

    @Query("SELECT MI.memberId FROM MemberInfo MI " +
            "INNER JOIN FriendRelation FR ON FR.memberId = :memberId AND FR.friendId = MI.memberId AND FR.friendStatus = true "+
            "WHERE MI.nickname = :nickname")
    List<Member> findAllByNickname(@Param("nickname") String nickname, @Param("memberId") Member memberId);

    @Query("SELECT new com.mohey.memberservice.dto.memberFriend.FriendInFriendListSearchRespDto("+
            "M.memberUuid, "+
            "MI.nickname," +
            "M.gender, "+
            "M.birthDate,"+
            "MPI.profileUrl,"+
            //"FR.friendStatus,"+
            "FR.favoriteStatus)"+
            "FROM Member M " +
            "INNER JOIN FriendRelation FR ON FR.memberId = :memberId AND FR.friendStatus = true AND FR.favoriteStatus = true "+
            "LEFT JOIN MemberInfo MI ON MI.memberId = M AND MI.createdDatetime = (SELECT MAX(mi.createdDatetime) FROM MemberInfo mi WHERE mi.memberId = M)"+
            "LEFT JOIN MemberProfileImage MPI ON MPI.memberId = M AND MPI.createdDatetime = (SELECT MAX(mpi.createdDatetime) FROM MemberProfileImage mpi WHERE mpi.memberId = M)"+
            "WHERE M.id = :id"
    )
    List<FriendInFriendListSearchRespDto> findFavoriteFriendById(@Param("id") Long id, @Param("memberId") Member memberId);

    @Query("SELECT new com.mohey.memberservice.dto.memberFriend.FriendInFriendListSearchRespDto("+
            "M.memberUuid, "+
            "MI.nickname," +
            "M.gender, "+
            "M.birthDate,"+
            "MPI.profileUrl,"+
            "FR.favoriteStatus)"+
            "FROM Member M " +
            "LEFT JOIN MemberInfo MI ON MI.memberId = M AND MI.createdDatetime = (SELECT MAX(mi.createdDatetime) FROM MemberInfo mi WHERE mi.memberId = M)"+
            "LEFT JOIN MemberProfileImage MPI ON MPI.memberId = M AND MPI.createdDatetime = (SELECT MAX(mpi.createdDatetime) FROM MemberProfileImage mpi WHERE mpi.memberId = M)"+
            "INNER JOIN FriendRelation FR ON FR.friendStatus = true AND FR.favoriteStatus = false AND FR.memberId = :memberId "+
            "WHERE M.id = :id"
    )
    List<FriendInFriendListSearchRespDto> findNotFavoriteFriendById(@Param("id") Long id, @Param("memberId") Member memberId );
}
