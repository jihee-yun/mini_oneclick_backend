package com.kh.mini_oneclick.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MySubsVo {
    private int num;
    private int memberNum;
    private int subscriptionNum;
    private Date startDate;
    private Date endDate;
}