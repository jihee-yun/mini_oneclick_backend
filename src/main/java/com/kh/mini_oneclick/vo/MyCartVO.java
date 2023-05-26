package com.kh.mini_oneclick.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;

@Getter
@Setter
public class MyCartVO {
    private int cartNum;
    private int memNum;
    private int lecNum;
    private String lecName;
    private String thum;
    private BigDecimal price;
    private int quantity;
    private Date startDate; // 일단 수강일자.......
    private BigDecimal totalPrice; // 총금액
}
