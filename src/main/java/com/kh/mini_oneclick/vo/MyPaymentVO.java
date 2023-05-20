package com.kh.mini_oneclick.vo;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class MyPaymentVO {
    private int num;
    private int lectureNum;
    private int memberNum;
    private String lectureName;
    private String thum;
    private int price;
    private String addr;
    private Date created;
    private int amount; // 결제 수량
    private Date cancleDate;
    private String isCancle;

}
