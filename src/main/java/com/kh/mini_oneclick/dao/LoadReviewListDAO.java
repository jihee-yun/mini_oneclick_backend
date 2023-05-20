package com.kh.mini_oneclick.dao;

import com.kh.mini_oneclick.common.Common;
import com.kh.mini_oneclick.vo.LoadReviewListVO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoadReviewListDAO {
    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    private PreparedStatement pStmt = null;

    public List<LoadReviewListVO> loadReviewList(String getId) {
        List<LoadReviewListVO> list = new ArrayList<>();
        String sql = "SELECT * FROM T_REVIEW WHERE LECTURE_NUM = " + getId +
                " ORDER BY CREATED DESC";
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next()) {
                int reviewNum = rs.getInt("NUM_");
                int lectureNum = rs.getInt("LECTURE_NUM");
                int memberNum = rs.getInt("MEMBER_NUM");
                String title = rs.getString("TITLE");
                String content = rs.getString("CONTENT");
                String img = rs.getString("IMG_");
                Date date = rs.getDate("CREATED");

                LoadReviewListVO vo = new LoadReviewListVO();
                vo.setReviewNum(reviewNum);
                vo.setLectureNum(lectureNum);
                vo.setMemberNum(memberNum);
                vo.setTitle(title);
                vo.setContent(content);
                vo.setImg(img);
                vo.setDate(date);
                list.add(vo);
            }
            Common.close(rs);
            Common.close(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}