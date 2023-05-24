package com.kh.mini_oneclick.vo;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class DetailLectureInfoVO {
    private int lectureNum;
    private int category_Num;
    private int likeCount;
    private String name;
    private Date startDate;
    private Date endDate;
    private String thum;
    private String description;
    private String intro;
    private int price;
    private String addr;
    private String lecturer;
    private String lecturer_desc;
}
