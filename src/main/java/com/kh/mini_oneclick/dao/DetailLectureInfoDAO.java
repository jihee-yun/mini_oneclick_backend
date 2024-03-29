package com.kh.mini_oneclick.dao;

import com.kh.mini_oneclick.common.Common;
import com.kh.mini_oneclick.vo.DetailLectureInfoVO;
import com.kh.mini_oneclick.vo.ImgVO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DetailLectureInfoDAO {
    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    private PreparedStatement pStmt = null;

    public List<DetailLectureInfoVO> getAllLectureList(int lectureNum) {
        List<DetailLectureInfoVO> list = new ArrayList<>();
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM T_LECTURE L " +
                    "INNER JOIN T_CATEGORY C ON L.CATEGORY_NUM = C.NUM_ " +
                    "WHERE L.NUM_ =" + "'" + lectureNum + "'" +
                    "AND L.END_DATE >= SYSDATE";
            rs = stmt.executeQuery(sql);
            while(rs.next()) {
                int LectureNum = rs.getInt("L.NUM_");
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
                String Lecture_photo = rs.getString("LECTURER_PHOTO");

                DetailLectureInfoVO vo = new DetailLectureInfoVO();
                vo.setNum(LectureNum);
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
                vo.setLecturer_photo(Lecture_photo);
                list.add(vo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Common.close(rs);
            Common.close(stmt);
            Common.close(conn);
        }
        return list;
    }

    public List<DetailLectureInfoVO> selectLectureList(int lectureNum) {
        List<DetailLectureInfoVO> list = new ArrayList<>();
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM T_LECTURE L " +
                    "INNER JOIN T_CATEGORY C ON L.CATEGORY_NUM = C.NUM_ " +
                    "WHERE L.NUM_ =" + "'" + lectureNum + "'";
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
                String Lecture_photo = rs.getString("LECTURER_PHOTO");

                DetailLectureInfoVO vo = new DetailLectureInfoVO();
                vo.setNum(LectureNum);
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
                vo.setLecturer_photo(Lecture_photo);
                list.add(vo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Common.close(rs);
            Common.close(stmt);
            Common.close(conn);
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
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            Common.close(rs);
            Common.close(stmt);
            Common.close(conn);
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
            if(rs.next()) isReg = true;
            else isReg = false;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Common.close(rs);
            Common.close(stmt);
            Common.close(conn);
        }
        return isReg; // 위시리스트에 있으면 true, 없으면 false 반환
    }

    public boolean acceptWish(int lectureNum, int memberNum) {
        int result = 0;
        String sql = "INSERT INTO T_WISHLIST(NUM_, LECTURE_NUM, MEMBER_NUM) VALUES(WISHLIST_SEQ.NEXTVAL, ?, ?)";
        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1, lectureNum);
            pStmt.setInt(2, memberNum);
            result = pStmt.executeUpdate();
            System.out.println("찜하기 추가 결과 확인" + result);
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            Common.close(pStmt);
            Common.close(conn);
        }

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
        } finally {
            Common.close(pStmt);
            Common.close(conn);
        }

        if(result == 1) return false;
        else return true;
    }

    public boolean checkCartReg(int lectureNum, int memberNum) {
        boolean isReg = false;
        String sql = "SELECT * FROM T_CART " +
                "WHERE LECTURE_NUM = " + "'" + lectureNum + "'" + "AND MEMBER_NUM = " + "'" + memberNum + "'";
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if(rs.next()) isReg = false;
            else isReg = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Common.close(rs);
            Common.close(stmt);
            Common.close(conn);
        }
        return isReg; // 위시리스트에 있으면 false, 없으면 true 반환
    }

    public boolean acceptCart(int lectureNum, int memberNum, int quantity) {
        int result = 0;
        String sql = "INSERT INTO T_CART(NUM_, LECTURE_NUM, MEMBER_NUM, QUANTITY) VALUES(CART_SEQ.NEXTVAL, ?, ?, ?)";
        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1, lectureNum);
            pStmt.setInt(2, memberNum);
            pStmt.setInt(3, quantity);
            result = pStmt.executeUpdate();
            System.out.println("카트에 담기 결과 확인" + result);
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            Common.close(pStmt);
            Common.close(conn);
        }

        if(result == 1) return true;
        else return false;
    }
}