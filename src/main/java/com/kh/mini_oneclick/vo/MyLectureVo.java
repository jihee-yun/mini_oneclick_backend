package com.kh.mini_oneclick.vo;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class MyLectureVo {
    private int num;
    private int memberNum;
    private int lectureNum;
    private Date created; // 등록일
    private String isDeleted; // Y, N 강의취소 유무 default N
    private String written; // 강의 리뷰 작성되었는지 default N

}