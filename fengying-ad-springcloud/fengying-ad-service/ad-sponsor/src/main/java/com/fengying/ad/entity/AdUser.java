package com.fengying.ad.entity;

import com.fengying.ad.constant.CommonStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="ad_user")
public class AdUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",nullable = false)
    private Long id;

    @Basic
    @Column(name="username",nullable = false)
    private String username;

    @Basic
    @Column(name="token",nullable = false)
    private String token;

    @Basic
    @Column(name="userStatus",nullable = false)
    private Integer userStatus;

    @Basic
    @Column(name = "create_Time",nullable = false)
    private Date createTime;

    @Basic
    @Column(name="update_Time",nullable = false)
    private Date updateTime;

    public AdUser(String username,String token){

        this.username=username;
        this.token=token;
        this.userStatus= CommonStatus.VAlID.getStatus();
        this.createTime=new Date();
        this.updateTime=this.createTime;
    }


}
