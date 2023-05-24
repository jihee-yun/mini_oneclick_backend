package com.kh.mini_oneclick.dao;

import com.kh.mini_oneclick.common.Common;
import com.kh.mini_oneclick.vo.ImgVO;
import com.kh.mini_oneclick.vo.LectureVO;
import com.kh.mini_oneclick.vo.DetailLectureInfoVO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LectureDAO {
    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    private PreparedStatement pStmt = null;

    // 검색 강의 정보 조회
    public List<LectureVO> lectureSelect(String getNum) {
        List<LectureVO> list = new ArrayList<>();
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT L.*, C.NAME_ AS CNAME\n" +
                    "    FROM T_LECTURE L\n" +
                    "    INNER JOIN T_CATEGORY C\n" +
                    "    ON L.CATEGORY_NUM = C.NUM_";
            rs = stmt.executeQuery(sql);

            while(rs.next()) {
                int num = rs.getInt("NUM_");
                Integer likecount = rs.getInt("LIKECOUNT");
                String name = rs.getString("NAME_");
                Date start = rs.getDate("START_DATE");
                Date end = rs.getDate("END_DATE");
                String thum = rs.getString("THUM");
                String desc = rs.getString("DESCRIPTION");
                String intro = rs.getString("INTRO");
                Integer price = rs.getInt("PRICE_");
                String addr = rs.getString("ADDR");
                String lecture = rs.getString("LECTURER");
                String category = rs.getString("CNAME");

                LectureVO vo = new LectureVO();
                vo.setNum(num);
                vo.setLikeCount(likecount);
                vo.setName(name);
                vo.setStartDate(start);
                vo.setEndDate(end);
                vo.setThum(thum);
                vo.setDescription(desc);
                vo.setIntro(intro);
                vo.setPrice(price);
                vo.setAddr(addr);
                vo.setLecturer(lecture);
                vo.setCategoryName(category);

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
    // 베이킹 강의 3개 조회
    public List<LectureVO> bakingSelect(String getNum) {
        List<LectureVO> list = new ArrayList<>();
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT L.*, C.NAME_ AS CNAME\n" +
                    "\tFROM T_LECTURE L\n" +
                    "\tINNER JOIN T_CATEGORY C\n" +
                    "\tON L.CATEGORY_NUM = C.NUM_\n" +
                    "\tWHERE L.CATEGORY_NUM = 2 AND ROWNUM <= 3";
            rs = stmt.executeQuery(sql);

            while(rs.next()) {
                int num = rs.getInt("NUM_");
                Integer likecount = rs.getInt("LIKECOUNT");
                String name = rs.getString("NAME_");
                Date start = rs.getDate("START_DATE");
                Date end = rs.getDate("END_DATE");
                String thum = rs.getString("THUM");
                String desc = rs.getString("DESCRIPTION");
                String intro = rs.getString("INTRO");
                Integer price = rs.getInt("PRICE_");
                String addr = rs.getString("ADDR");
                String lecture = rs.getString("LECTURER");
                String category = rs.getString("CNAME");

                LectureVO vo = new LectureVO();
                vo.setNum(num);
                vo.setLikeCount(likecount);
                vo.setName(name);
                vo.setStartDate(start);
                vo.setEndDate(end);
                vo.setThum(thum);
                vo.setDescription(desc);
                vo.setIntro(intro);
                vo.setPrice(price);
                vo.setAddr(addr);
                vo.setLecturer(lecture);
                vo.setCategoryName(category);

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
    // 좋아요 많은 순으로 (사이드바 10개만)
    public List<LectureVO> likeCountSelect(String getNum) {
        List<LectureVO> list = new ArrayList<>();
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT *\n" +
                    "FROM (\n" +
                    "\tSELECT L.*, C.NAME_ AS CNAME\n" +
                    "\t    FROM T_LECTURE L\n" +
                    "\t    INNER JOIN T_CATEGORY C\n" +
                    "\t    ON L.CATEGORY_NUM = C.NUM_\n" +
                    "\t\tORDER BY LIKECOUNT DESC\n" +
                    ") WHERE ROWNUM <= 10";
            rs = stmt.executeQuery(sql);

            while(rs.next()) {
                int num = rs.getInt("NUM_");
                Integer likecount = rs.getInt("LIKECOUNT");
                String name = rs.getString("NAME_");
                Date start = rs.getDate("START_DATE");
                Date end = rs.getDate("END_DATE");
                String thum = rs.getString("THUM");
                String desc = rs.getString("DESCRIPTION");
                String intro = rs.getString("INTRO");
                Integer price = rs.getInt("PRICE_");
                String addr = rs.getString("ADDR");
                String lecture = rs.getString("LECTURER");
                String category = rs.getString("CNAME");

                LectureVO vo = new LectureVO();
                vo.setNum(num);
                vo.setLikeCount(likecount);
                vo.setName(name);
                vo.setStartDate(start);
                vo.setEndDate(end);
                vo.setThum(thum);
                vo.setDescription(desc);
                vo.setIntro(intro);
                vo.setPrice(price);
                vo.setAddr(addr);
                vo.setLecturer(lecture);
                vo.setCategoryName(category);

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
    // 메인화면 추천 클래스 (좋아요 낮은 순 3개)
    public List<LectureVO> recommendLecture (String getNum) {
        List<LectureVO> list = new ArrayList<>();
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT *\n" +
                    "FROM (\n" +
                    "\tSELECT L.*, C.NAME_ AS CNAME\n" +
                    "\t    FROM T_LECTURE L\n" +
                    "\t    INNER JOIN T_CATEGORY C\n" +
                    "\t    ON L.CATEGORY_NUM = C.NUM_\n" +
                    "\t\tORDER BY LIKECOUNT \n" +
                    ") WHERE ROWNUM <= 3";
            rs = stmt.executeQuery(sql);

            while(rs.next()) {
                int num = rs.getInt("NUM_");
                Integer likecount = rs.getInt("LIKECOUNT");
                String name = rs.getString("NAME_");
                Date start = rs.getDate("START_DATE");
                Date end = rs.getDate("END_DATE");
                String thum = rs.getString("THUM");
                String desc = rs.getString("DESCRIPTION");
                String intro = rs.getString("INTRO");
                Integer price = rs.getInt("PRICE_");
                String addr = rs.getString("ADDR");
                String lecture = rs.getString("LECTURER");
                String category = rs.getString("CNAME");

                LectureVO vo = new LectureVO();
                vo.setNum(num);
                vo.setLikeCount(likecount);
                vo.setName(name);
                vo.setStartDate(start);
                vo.setEndDate(end);
                vo.setThum(thum);
                vo.setDescription(desc);
                vo.setIntro(intro);
                vo.setPrice(price);
                vo.setAddr(addr);
                vo.setLecturer(lecture);
                vo.setCategoryName(category);

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
