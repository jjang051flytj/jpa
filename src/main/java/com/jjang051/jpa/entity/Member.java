package com.jjang051.jpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jjang051.jpa.dto.MemberDto;
import com.jjang051.jpa.dto.QuestionDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name="entity_member")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String userID;
    private String userPW; //암호화 시켜서 넣을 예정
    private String userName;
    private String userEmail;
    private String tel;
    private String address01;
    private String address02;
    private int zipcode;
    private int age;

    //private String originalProfile; //db에 넣을거
    //private String renameProfile;
    private String roles;

    @OneToMany(mappedBy = "writer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Question> questionList;

    @OneToMany(mappedBy = "writer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comment> commentList;

    public static MemberDto toDto(Member member) {
        MemberDto memberDto = MemberDto.builder()
                .userID(member.getUserID())
                .userPW(member.getUserPW())
                .userName(member.getUserName())
                .roles(member.getRoles())
                .address01(member.getAddress01())
                .address02(member.getAddress02())
                .zipcode(member.getZipcode())
                .tel(member.getTel())
                .userEmail(member.getUserEmail())
                .build();
        return memberDto;
    }
}










