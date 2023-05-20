package com.kh.mini_oneclick.vo;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class CategoryVo {
    private int num;
    private int lectureNum;
    private int likeCount;
    private String name;
    private Date startDate;
    private Date endDate;
    private String thum;
    private String description;
    private String intro;
    private int price;
    private String lecturer;
}