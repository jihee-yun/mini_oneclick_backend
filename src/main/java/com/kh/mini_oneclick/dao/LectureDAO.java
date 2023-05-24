package com.kh.mini_oneclick.dao;

import com.kh.mini_oneclick.common.Common;
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

    public List<DetailLectureInfoVO> LectureList(int category, int lectureNum) {
        List<DetailLectureInfoVO> list = new ArrayList<>();
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            System.out.println("LectureDAO 실행 : " + category + "," + lectureNum);
            String sql = "SELECT * FROM T_LECTURE L " +
                    "INNER JOIN T_CATEGORY C ON L.CATEGORY_NUM = C.NUM_ " +
                    "WHERE C.NUM_ = " + "'" + category + "' AND " + "L.NUM_ =" + "'" + lectureNum + "'" ;
//                    "AND L.END_DATE >= SYSDATE";
            rs = stmt.executeQuery(sql);
            while(rs.next()) {
                int LectureNum = rs.getInt("NUM_");
                int CategoryNum = rs.getInt("CATEGORY_NUM");
                int LikeCount = rs.getInt("LIKECOUNT");
                String Name = rs.getString("NAME_");
                Date StartDate = rs.getDate("START_DATE");
                Date EndDate = rs.getDate("END_DATE");
                String Thum = rs.getString("THUM");
                String Desc = rs.getString("DESCRIPTION");
                String Intro = rs.getString("INTRO");
                int Price = rs.getInt("PRICE_");
                String Addr = rs.getString("ADDR");
                String Lecturer = rs.getString("LECTURER");
                String Lecturer_desc = rs.getString("LECTURER_DESC");

                DetailLectureInfoVO vo = new DetailLectureInfoVO();
                vo.setLectureNum(LectureNum);
                vo.setCategory_Num(CategoryNum);
                vo.setLikeCount(LikeCount);
                vo.setName(Name);
                vo.setStartDate(StartDate);
                vo.setEndDate(EndDate);
                vo.setThum(Thum);
                vo.setDescription(Desc);
                vo.setIntro(Intro);
                vo.setPrice(Price);
                vo.setAddr(Addr);
                vo.setLecturer(Lecturer);
                vo.setLecturer_desc(Lecturer_desc);
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

    public List<ImgVO> getLectureImg(int lectureNum) {
        List<ImgVO> list = new ArrayList<>();
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            System.out.println(lectureNum);
            String sql = "SELECT * FROM T_LECTURE WHERE NUM_ = " + "'" + lectureNum + "'";
            rs = stmt.executeQuery(sql);

            while(rs.next()) {
                ImgVO vo = new ImgVO();
                vo.setMainImg1(rs.getString("MAINIMG1"));
                vo.setMainImg2(rs.getString("MAINIMG2"));
                vo.setMainImg3(rs.getString("MAINIMG3"));
                vo.setMainImg4(rs.getString("MAINIMG4"));
                list.add(vo);
            }
            Common.close(rs);
            Common.close(stmt);
            Common.close(conn);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean checkWishReg(int lectureNum, int memberNum) {
        boolean isReg = false;
        String sql = "SELECT * FROM T_WISHLIST " +
                "WHERE LECTURE_NUM = " + "'" + lectureNum + "'" + "AND MEMBER_NUM = " + "'" + memberNum + "'";
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if(rs.next()) isReg = false;
            else isReg = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(rs);
        Common.close(stmt);
        Common.close(conn);
        return isReg; // 작성한 이력이 있으면 false, 없으면 true 반환
    }

    public boolean acceptWish(int lectureNum, int memberNum) {
        int result = 0;
        String sql = "INSERT INTO T_WISHLIST VALUES(WISHLIST_SEQ.NEXT_VAL, ?, ?)";
        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1, lectureNum);
            pStmt.setInt(2, memberNum);
            result = pStmt.executeUpdate();
            System.out.println("찜하기 추가 결과 확인" + result);
        } catch(Exception e) {
            e.printStackTrace();
        }
        Common.close(pStmt);
        Common.close(conn);

        if(result == 1) return true;
        else return false;
    }

    public boolean deleteWish(int lectureNum, int memberNum) {
        int result = 0;
        String sql = "DELETE FROM T_WISHLIST WHERE LECTURE_NUM = ? AND MEMBER_NUM = ?";
        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1, lectureNum);
            pStmt.setInt(2, memberNum);
            result = pStmt.executeUpdate();
            System.out.println("찜하기 삭제 결과 확인" + result);
        } catch(Exception e) {
            e.printStackTrace();
        }
        Common.close(pStmt);
        Common.close(conn);

        if(result == 1) return true;
        else return false;
    }

}
