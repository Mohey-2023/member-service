package com.mohey.memberservice.repository;

import com.mohey.memberservice.domain.MemberInfo;
import com.mohey.memberservice.dto.memberFriend.FriendListSearchRespDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendSearchRepository extends JpaRepository<MemberInfo, Long> {
    @Query("SELECT new com.mohey.memberservice.dto.memberFriend.FriendListSearchRespDto("+
            "MI.nickname," +
            "M.gender, "+
            "M.birthDate,"+
            "MPI.profileUrl,"+
            "FR.favoriteStatus)"+
            "FROM Member M " +
            "LEFT JOIN MemberInfo MI ON MI.memberId = M AND MI.createdDatetime = (SELECT MAX(mi.createdDatetime) FROM MemberInfo mi WHERE mi.memberId = M)"+
            "LEFT JOIN MemberProfileImage MPI ON MPI.memberId = M AND MPI.createdDatetime = (SELECT MAX(mpi.createdDatetime) FROM MemberProfileImage mpi WHERE mpi.memberId = M)"+
            "LEFT JOIN FriendRelation FR ON FR.friendStatus = true and FR.favoriteStatus = false "+
            "WHERE M.id = :id"
    )
    List<FriendListSearchRespDto> getNotFavoriteFriendList(@Param("id") Long id);
    @Query("SELECT new com.mohey.memberservice.dto.memberFriend.FriendListSearchRespDto("+
            "MI.nickname," +
            "M.gender, "+
            "M.birthDate,"+
            "MPI.profileUrl,"+
            "FR.favoriteStatus)"+
            "FROM Member M " +
            "LEFT JOIN MemberInfo MI ON MI.memberId = M AND MI.createdDatetime = (SELECT MAX(mi.createdDatetime) FROM MemberInfo mi WHERE mi.memberId = M)"+
            "LEFT JOIN MemberProfileImage MPI ON MPI.memberId = M AND MPI.createdDatetime = (SELECT MAX(mpi.createdDatetime) FROM MemberProfileImage mpi WHERE mpi.memberId = M)"+
            "LEFT JOIN FriendRelation FR ON FR.friendStatus = true and FR.favoriteStatus = true "+
            "WHERE M.id = :id"
    )
    List<FriendListSearchRespDto> getFavoriteFriendList(@Param("id") Long id);


}
