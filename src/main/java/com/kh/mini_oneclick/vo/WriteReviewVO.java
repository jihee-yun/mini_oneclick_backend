package com.kh.mini_oneclick.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WriteReviewVO {
    private int num;
    private int lectureNum;
    private String memberNum;
    private String title;
    private String content;
    private String img;
}