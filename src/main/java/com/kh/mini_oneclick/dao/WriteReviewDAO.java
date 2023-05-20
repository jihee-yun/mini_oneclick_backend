package com.kh.mini_oneclick.dao;

import com.kh.mini_oneclick.common.Common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class WriteReviewDAO {
    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    private PreparedStatement pStmt = null;

    public boolean WriteReview(int lectureNum, String memberNum, String title, String content, String img) {
        int result = 0;
        String sql = "INSERT INTO T_REVIEW(NUM_, LECTURE_NUM, MEMBER_NUM, TITLE, CONTENT, IMG_, CREATED) " +
                "VALUES(REVIEW_SEQ.NEXTVAL, ?, ?, ?, ?, ?, SYSDATE)"; // NUM_ 에는 NEXT_VAL,
        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1, lectureNum);
            pStmt.setString(2, memberNum);
            pStmt.setString(3, title);
            pStmt.setString(4, content);
            pStmt.setString(5, img);
            result = pStmt.executeUpdate();
            System.out.println("입력한 리뷰 DB값 확인" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(pStmt);
        Common.close(conn);
        if(result == 1) return true;
        else return false;
    }
}