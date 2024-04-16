package com.demo.angular.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @CreatedBy
    @Column(name = "created_by", length = 50)
    private String createdBy;


    @LastModifiedDate
    @Column(name = "update_on")
    private LocalDateTime updatedOn;

    @LastModifiedBy
    @Column(name = "update_by", length = 50)
    private String updatedBy;
}
