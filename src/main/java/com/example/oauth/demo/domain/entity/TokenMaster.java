package com.example.oauth.demo.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "token_master")
@Getter
@Setter
@ToString
public class TokenMaster implements Serializable {

    @Id
    @Column(name = "tkn_id", nullable = false)
    private String tknId;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "pub_time", length = 14)
    private String pubTime;

    @Column(name = "exp_time", length = 14)
    private String expTime;

    @Column(name = "tkn_type", length = 2)
    private String tknType;

    public TokenMaster() {
    }

    @Builder
    public TokenMaster(String tknId, Integer userId, String pubTime, String expTime, String tknType) {
        this.tknId = tknId;
        this.userId = userId;
        this.pubTime = pubTime;
        this.expTime = expTime;
        this.tknType = tknType;
    }
}