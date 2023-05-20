package com.kh.mini_oneclick.vo;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class LoadReviewListVO {
    private int reviewNum;
    private int lectureNum;
    private int memberNum;
    private String title;
    private String content;
    private String img;
    private Date date;
}