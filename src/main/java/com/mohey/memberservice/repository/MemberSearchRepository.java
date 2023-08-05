package com.mohey.memberservice.repository;

import com.mohey.memberservice.domain.Member;

import com.mohey.memberservice.dto.memberSearch.MemberSearchRespDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberSearchRepository extends JpaRepository<Member, Long> {

    @Query("SELECT MI.memberId FROM MemberInfo MI WHERE MI.nickname = :nickname")
    List<Member> findAllByNickname(@Param("nickname") String nickname);
    @Query("SELECT new com.mohey.memberservice.dto.memberSearch.MemberSearchRespDto("+
            "MI.nickname," +
            "M.gender, "+
            "M.birthDate,"+
            "MPI.profileUrl,"+
            "FR.friendStatus)"+
            "FROM Member M " +
            "LEFT JOIN MemberInfo MI ON MI.memberId = M AND MI.createdDatetime = (SELECT MAX(mi.createdDatetime) FROM MemberInfo mi WHERE mi.memberId = M)"+
            "LEFT JOIN MemberProfileImage MPI ON MPI.memberId = M AND MPI.createdDatetime = (SELECT MAX(mpi.createdDatetime) FROM MemberProfileImage mpi WHERE mpi.memberId = M)"+
            "LEFT JOIN FriendRelation FR ON FR.memberId = M AND FR.friendId = :friendId "+
            "WHERE M.id = :id"
    )
    List<MemberSearchRespDto> getMemberList(@Param("id") Long id, @Param("friendId") Member friendId);



}
