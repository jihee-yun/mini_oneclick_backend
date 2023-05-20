package com.kh.mini_oneclick.dao;

import com.kh.mini_oneclick.common.Common;
import com.kh.mini_oneclick.vo.CategoryVo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private Statement stmt = null;
    private ResultSet rs = null;

    // 카테고리 리스트 조회
    public List<CategoryVo> getAllCategories() {
        List<CategoryVo> categoryList = new ArrayList<>();

        try {
            conn = Common.getConnection();
            pstmt = conn.prepareStatement("SELECT * FROM T_CATEGORY");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int categoryNum = rs.getInt("NUM_");
                String categoryName = rs.getString("NAME_");

                CategoryVo categoryVo = new CategoryVo();
                categoryVo.setNum(categoryNum);
                categoryVo.setName(categoryName);

                categoryList.add(categoryVo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Common.close(rs);
            Common.close(pstmt);
            Common.close(conn);
        }

        return categoryList;
    }

    // 카테고리별 강의 조회
    public List<CategoryVo> getLecturesByCategory(int categoryNum) {
        List<CategoryVo> list = new ArrayList<>();

        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();

            String sql = "SELECT L.NUM_ AS LECTURE_NUM, L.NAME_, L.START_DATE, L.END_DATE, L.THUM, L.INTRO, L.PRICE_, L.LECTURER, C.NAME_ AS CATEGORY_NAME "
                    + "FROM T_LECTURE L "
                    + "JOIN T_CATEGORY C ON L.CATEGORY_NUM = C.NUM_ "
                    + "WHERE L.CATEGORY_NUM = '" + categoryNum + "'";

            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int lectureNum = rs.getInt("LECTURE_NUM");
                String name = rs.getString("NAME_");
                Date startDate = rs.getDate("START_DATE");
                Date endDate = rs.getDate("END_DATE");
                String thumbnail = rs.getString("THUM");
                String intro = rs.getString("INTRO");
                int price = rs.getInt("PRICE_");
                String lecturer = rs.getString("LECTURER");

                CategoryVo vo = new CategoryVo();
                vo.setLectureNum(lectureNum);
                vo.setName(name);
                vo.setStartDate(startDate);
                vo.setEndDate(endDate);
                vo.setThum(thumbnail);
                vo.setIntro(intro);
                vo.setPrice(price);
                vo.setLecturer(lecturer);
                list.add(vo);
            }
            Common.close(rs);
            Common.close(stmt);
            Common.close(conn);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}