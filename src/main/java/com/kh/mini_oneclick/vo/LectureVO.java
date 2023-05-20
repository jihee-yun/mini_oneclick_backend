package com.kh.mini_oneclick.vo;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class LectureVO {
    private int num; // 강의 번호(pk)
    private int wishNum; // 위시리스트 번호
    private int categoryNum;
    private String categoryName;
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
    private Date created;
    private String isDeleted;

}
