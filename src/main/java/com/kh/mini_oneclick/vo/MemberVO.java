package com.kh.mini_oneclick.vo;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class MemberVO {
    private int num;
    private String id;
    private String pwd;
    private String name;
    private String tel;
    private String isTeacher;
    private String mail;
    private Date created;
    private String isDeleted;
    private Date mySubStartDate; // 내 구독권
    private Date mySubEndDate; // 내 구독권
    private String subsType; // 내 구독권
    private String isSub; // 내 구독권
    private int paySubNum;


}
