package com.kh.mini_oneclick.vo;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class MyReviewVO {
    private int num; // 후기 번호(pk)
    private int lNum; // 강의 번호
    private int mNum; // 회원 번호
    private int myLectureNum; // 수강 중인 강의 번호
    private String title; // 강의 이름
    private String thum; // 강의 썸네일
    private String content; // 댓글 내용
    private String img; // 후기 첨부 이미지
    private Date created; // 작성 날짜
    private Date classDay; // 수강 날짜
    private String written; // 후기 작성 여부
}
