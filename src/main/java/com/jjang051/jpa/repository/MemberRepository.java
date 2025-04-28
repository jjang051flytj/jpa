package com.jjang051.jpa.repository;

import com.jjang051.jpa.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    //쿼리메서드
    Optional<Member> findByUserID(String userID);
    Optional<Member> findByUserEmail(String userEmail);
    Optional<Member> findByUserIDAndUserEmail(String userID,String userEmail);
    List<Member> findByUserNameLike(String userName);
    Optional<Member> findByUserIDOrUserEmail(String UserID,String userEmail);
    List<Member> findByAgeBetween(int min, int max);
    List<Member> findByAgeBetweenAndUserID(int min, int max, String userID);

    @Query("select m from Member m where m.userName =:userName")
    Optional<Member> findByCustomMember(@Param("userName") String userName);

    @Query(value = "select m from entity_member m where m.userName =:userName",nativeQuery = true)
    List<Member> findByCustomNativeMember(@Param("userName") String userName);
}
