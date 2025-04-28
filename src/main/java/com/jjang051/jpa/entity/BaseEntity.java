package com.jjang051.jpa.entity;


import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class BaseEntity {
    @CreatedDate
    @Column(updatable = false)
    public LocalDateTime regDate;

    @LastModifiedDate     //마지막 수정한 날짜 들어간다.
    public LocalDateTime modifyDate;
}
