package com.kh.mini_oneclick.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;

@Getter
@Setter
public class PaymentVo {
    private int num;
    private int lectureNum;
    private int memberNum;
    private Date created;
    private BigDecimal amount;
    private Date cancelDate;
    private char isCancel;

}