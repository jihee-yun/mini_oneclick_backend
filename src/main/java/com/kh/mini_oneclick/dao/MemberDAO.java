package com.kh.mini_oneclick.dao;

import com.kh.mini_oneclick.common.Common;
import com.kh.mini_oneclick.vo.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {
    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    private PreparedStatement pStmt = null;

    // 로그인 체크
    public boolean loginCheck(String id, String pwd) {
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement(); // Statement 객체 얻기
            String sql = "SELECT * FROM T_MEMBER WHERE ID_ = " + "'" + id + "'";
            rs = stmt.executeQuery(sql);

            while(rs.next()) { // 읽은 데이타가 있으면 true
                String sqlId = rs.getString("ID_"); // 쿼리문 수행 결과에서 ID값을 가져 옴
                String sqlPwd = rs.getString("PWD");
                System.out.println("ID : " + sqlId);
                System.out.println("PWD : " + sqlPwd);
                if(id.equals(sqlId) && pwd.equals(sqlPwd)) {
                    Common.close(rs);
                    Common.close(stmt);
                    Common.close(conn);
                    return true;
                }
            }
            Common.close(rs);
            Common.close(stmt);
            Common.close(conn);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 회원 정보 조회(마이페이지 조회)
    public List<MemberVO> myInfoSelect(String id) {
        List<MemberVO> list = new ArrayList<>();
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM T_MEMBER WHERE ID_ =" + "'" + id + "'";
            rs = stmt.executeQuery(sql);

            while(rs.next()) {
                int num = rs.getInt("NUM_");
                String id1 = rs.getString("ID_");
                String pwd = rs.getString("PWD");
                String name = rs.getString("NAME_");
                String tel = rs.getString("TEL");
                String email = rs.getString("MAIL");
                Date join = rs.getDate("CREATED");
                String isTeacher = rs.getString("IS_TEACHER");
                String isSub = rs.getString("IS_SUB");

                MemberVO vo = new MemberVO();
                vo.setNum(num);
                vo.setId(id1);
                vo.setPwd(pwd);
                vo.setName(name);
                vo.setMail(email);
                vo.setCreated(join);
                vo.setTel(tel);
                vo.setIsTeacher(isTeacher);
                vo.setIsSub(isSub);
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

    // 아이디만 조회 (아이디 찾기)
    public String memberId(String name, String mail) {
        int result = 0;
        String getId = null;
        String sql = "SELECT ID_ FROM T_MEMBER WHERE NAME_ = " + "'" + name + "'" + " AND " + "MAIL = " + "'" + mail + "'";
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if(rs.next()) {
                getId = rs.getString("ID_");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(stmt);
        Common.close(conn);
        return getId;

    }
    // 비밀번호만 조회 (비밀번호 찾기)
    public String memberPw(String name, String id, String mail) {
        int result = 0;
        String getPw = null;
        String sql = "SELECT PWD FROM T_MEMBER WHERE NAME_ = " + "'" + name + "'" + " AND " + "ID_ = " + "'" + id + "'" + " AND " + "MAIL = " + "'" + mail + "'";
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if(rs.next()) {
                getPw = rs.getString("PWD");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(stmt);
        Common.close(conn);
        return getPw;
    }

    // 회원 정보 조회
    public List<MemberVO> memberSelect(String getId) {
        List<MemberVO> list = new ArrayList<>();
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM T_MEMBER WHERE ID_ =" + "'" + getId + "'";
            rs = stmt.executeQuery(sql);

            while(rs.next()) {
                int num = rs.getInt("NUM_");
                String id = rs.getString("ID_");
                String pwd = rs.getString("PWD");
                String name = rs.getString("NAME_");
                String tel = rs.getString("TEL");
                String isTeacher = rs.getString("IS_TEACHER");
                String mail = rs.getString("MAIL");
                Date created = rs.getDate("CREATED");
                String isDeleted = rs.getString("IS_DELETED");
                String isSub = rs.getString("IS_SUB");

                MemberVO vo = new MemberVO();
                vo.setNum(num);
                vo.setId(id);
                vo.setPwd(pwd);
                vo.setName(name);
                vo.setTel(tel);
                vo.setIsTeacher(isTeacher);
                vo.setMail(mail);
                vo.setCreated(created);
                vo.setIsDeleted(isDeleted);
                vo.setIsSub(isSub);
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
    // 회원 가입 여부 확인
    public boolean regMemberCheck(String id) {
        boolean isNotReg = false;
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM T_MEMBER WHERE ID_ = " + "'" + id +"'";
            rs = stmt.executeQuery(sql);
            if(rs.next()) isNotReg = false;
            else isNotReg = true;
        } catch(Exception e) {
            e.printStackTrace();
        }
        Common.close(rs);
        Common.close(stmt);
        Common.close(conn);
        return isNotReg; // 가입 되어 있으면 false, 가입이 안되어 있으면 true
    }

    // 예빈 : 회원 가입
    public boolean memRegister(String name, String mail, String tel, String id, String pwd, String isTeacher) {
        int result = 0;
        String sql = "INSERT INTO T_MEMBER(NUM_, ID_, PWD, NAME_, TEL, MAIL, IS_TEACHER, CREATED, IS_DELETED, IS_SUB) VALUES (MEMBER_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, SYSDATE, 'N', 'N')";
        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, id);
            pStmt.setString(2, pwd);
            pStmt.setString(3, name);
            pStmt.setString(4, tel);
            pStmt.setString(5, mail);
            pStmt.setString(6, isTeacher);
            result = pStmt.executeUpdate();
            System.out.println("회원 가입 DB 결과 확인 : " + result);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(pStmt);
        Common.close(conn);

        if(result == 1) return true;
        else return false;
    }

    // 회원 탈퇴
    public boolean memberDelete(String id) {
        int result = 0;
        String sql = "DELETE FROM T_MEMBER WHERE ID = ?";

        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, id);
            result = pStmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(pStmt);
        Common.close(conn);
        if(result == 1) return true;
        else return false;
    }


    // 전화번호 수정
    public boolean updateTel(String tel, String id) {
        int result = 0;
        String sql = "UPDATE T_MEMBER SET TEL  = ? WHERE ID_ = ?";

        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, tel);
            pStmt.setString(2, id);
            result = pStmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(pStmt);
        Common.close(conn);
        if(result == 1) return true;
        else return false;
    }

    // 전화번호 중복값 체크
    public boolean regTelCheck(String tel) {
        boolean isNotReg = false;
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM T_MEMBER WHERE TEL = " + "'" + tel +"'";
            rs = stmt.executeQuery(sql);
            if(rs.next()) isNotReg = false;
            else isNotReg = true;
        } catch(Exception e) {
            e.printStackTrace();
        }
        Common.close(rs);
        Common.close(stmt);
        Common.close(conn);
        return isNotReg; // 가입 되어 있으면 false, 가입이 안되어 있으면 true
    }

    // 이메일 수정
    public boolean updateMail(String mail, String id) {
        int result = 0;
        String sql = "UPDATE T_MEMBER SET MAIL = ? WHERE ID_ = ?";

        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, mail);
            pStmt.setString(2, id);
            result = pStmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(pStmt);
        Common.close(conn);
        if(result == 1) return true;
        else return false;
    }

    // 이메일 중복값 체크
    public boolean regMailCheck(String mail) {
        boolean isNotReg = false;
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM T_MEMBER WHERE MAIL = " + "'" + mail +"'";
            rs = stmt.executeQuery(sql);
            if(rs.next()) isNotReg = false;
            else isNotReg = true;
        } catch(Exception e) {
            e.printStackTrace();
        }
        Common.close(rs);
        Common.close(stmt);
        Common.close(conn);
        return isNotReg; // 가입 되어 있으면 false, 가입이 안되어 있으면 true
    }

    // 내 구독권 조회
    public List<MemberVO> mySubsSelect(String id) {
        List<MemberVO> list = new ArrayList<>();
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT SUBSCRIPTION_NUM, START_DATE, END_DATE, ID_, IS_SUB, TYPE_, P.NUM_ AS PNUM\n" +
                    "    FROM T_MEMBER M JOIN T_MY_SUBS S\n" +
                    "    ON M.NUM_ = S.MEMBER_NUM\n" +
                    "    JOIN T_SUBS T\n" +
                    "    ON T.NUM_ = S.SUBSCRIPTION_NUM\n" +
                    "    JOIN T_PAYSUB P\n" +
                    "    ON T.PAYMENT_NUM = P.NUM_\n" +
                    "    WHERE ID_ = " + "'" + id + "'";
            rs = stmt.executeQuery(sql);

            while(rs.next()) {
                int num = rs.getInt("PNUM");
                String isSub = rs.getString("IS_SUB");
                String type = rs.getString("TYPE_");
                Date startDate = rs.getDate("START_DATE");
                Date endDate = rs.getDate("END_DATE");

                MemberVO vo = new MemberVO();
                vo.setIsSub(isSub);
                vo.setSubsType(type);
                vo.setMySubStartDate(startDate);
                vo.setMySubEndDate(endDate);
                vo.setPaySubNum(num);
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
    // 수강 중인 강의 조회
    public List<LectureVO> myClassSelect(String id) {
        List<LectureVO> list = new ArrayList<>();
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT C.NUM_, M.ID_, G.NUM_ AS CNUM, G.NAME_ AS CNAME, C.LECTURER, C.NAME_, C.THUM, C.INTRO, C.PRICE_, C.LIKECOUNT, L.CREATED, L.IS_DELETED\n" +
                    "    FROM T_MY_LECTURE L JOIN T_MEMBER M\n" +
                    "    ON M.NUM_ = L.MEMBER_NUM\n" +
                    "    JOIN T_LECTURE C \n" +
                    "    ON C.NUM_ = L.LECTURE_NUM\n" +
                    "    JOIN T_CATEGORY G\n" +
                    "    ON G.NUM_ = C.CATEGORY_NUM" +
                    "    WHERE ID_ = " + "'" + id + "'";
            rs = stmt.executeQuery(sql);

            while(rs.next()) {
                int num = rs.getInt("NUM_");
                String name = rs.getString("NAME_");
                int cateNum = rs.getInt("CNUM");
                String category = rs.getString("CNAME");
                String thumnail = rs.getString("THUM");
                String intro = rs.getString("INTRO");
                String lecturer = rs.getString("LECTURER");
                int price = rs.getInt("PRICE_");
                int likeCount = rs.getInt("LIKECOUNT");
                Date created = rs.getDate("CREATED");
                String isDeleted = rs.getString("IS_DELETED");


                LectureVO vo = new LectureVO();
                vo.setNum(num);
                vo.setName(name);
                vo.setCategoryNum(cateNum);
                vo.setCategoryName(category);
                vo.setThum(thumnail);
                vo.setIntro(intro);
                vo.setLecturer(lecturer);
                vo.setPrice(price);
                vo.setLikeCount(likeCount);
                vo.setCreated(created);
                vo.setIsDeleted(isDeleted);
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
    // 위시리스트 조회
    public List<LectureVO> myWishSelect(String id) {
        List<LectureVO> list = new ArrayList<>();
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT W.NUM_, L.NUM_ AS LNUM, M.ID_, G.NUM_ AS CNUM, G.NAME_ AS CNAME, L.NAME_, L.THUM, L.INTRO, L.LECTURER, L.PRICE_, L.LIKECOUNT\n" +
                    "    FROM T_MEMBER M JOIN  T_WISHLIST W\n" +
                    "    ON M.NUM_ = W.MEMBER_NUM\n" +
                    "    JOIN T_LECTURE L\n" +
                    "    ON L.NUM_ = W.LECTURE_NUM\n" +
                    "    JOIN T_CATEGORY G\n" +
                    "    ON G.NUM_ = L.CATEGORY_NUM" +
                    "    WHERE ID_ = " + "'" + id + "'";
            rs = stmt.executeQuery(sql);

            while(rs.next()) {
                int num = rs.getInt("NUM_");
                int num2 = rs.getInt("LNUM");
                int cateNum = rs.getInt("CNUM");
                String name = rs.getString("NAME_");
                String category = rs.getString("CNAME");
                String thumnail = rs.getString("THUM");
                String intro = rs.getString("INTRO");
                String lecturer = rs.getString("LECTURER");
                int price = rs.getInt("PRICE_");
                int likeCount = rs.getInt("LIKECOUNT");

                LectureVO vo = new LectureVO();
                vo.setWishNum(num);
                vo.setNum(num2);
                vo.setName(name);
                vo.setCategoryNum(cateNum);
                vo.setCategoryName(category);
                vo.setThum(thumnail);
                vo.setIntro(intro);
                vo.setLecturer(lecturer);
                vo.setPrice(price);
                vo.setLikeCount(likeCount);
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

    // 위시리스트 삭제 및 좋아요 카운트 수정
    public boolean wishDeleteLikeUpdate(String num, String wishNum) {
        Connection conn = null;
        PreparedStatement pStmt1 = null;
        PreparedStatement pStmt2 = null;
        boolean isSuccess = false;

        try {
            conn = Common.getConnection();
            conn.setAutoCommit(false);

            String deleteWishSql = "DELETE FROM T_WISHLIST WHERE NUM_ = ?";
            pStmt1 = conn.prepareStatement(deleteWishSql);
            pStmt1.setString(1, wishNum);
            int deleteResult = pStmt1.executeUpdate();

            String updateLikeCountSql = "UPDATE T_LECTURE SET LIKECOUNT = LIKECOUNT - 1 WHERE NUM_ = ?";
            pStmt2 = conn.prepareStatement(updateLikeCountSql);
            pStmt2.setString(1, num);
            int updateResult = pStmt2.executeUpdate();

            if (deleteResult == 1 && updateResult == 1) {
                conn.commit();
                isSuccess = true;
            } else {
                conn.rollback();
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            Common.close(pStmt1);
            Common.close(pStmt2);
            Common.close(conn);
        }

        return isSuccess;
    }

    // 작성 후기 조회
    public List<MyReviewVO> myReviewSelect(String id) {
        List<MyReviewVO> list = new ArrayList<>();
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT R.NUM_, L.NUM_ AS LNUM, R.CONTENT, R.IMG_, M.ID_, L.NAME_, L.THUM, C.WRITTEN, R.CREATED\n" +
                    "    FROM T_REVIEW R JOIN T_MEMBER M\n" +
                    "    ON M.NUM_ = R.MEMBER_NUM\n" +
                    "    JOIN T_LECTURE L \n" +
                    "    ON L.NUM_ = R.LECTURE_NUM\n" +
                    "    JOIN T_MY_LECTURE C\n" +
                    "    ON M.NUM_ = C.MEMBER_NUM AND L.NUM_ = C.LECTURE_NUM" +
                    "    WHERE ID_ = " + "'" + id + "'";

            rs = stmt.executeQuery(sql);

            while(rs.next()) {
                int num = rs.getInt("NUM_");
                int num2 = rs.getInt("LNUM");
                String name = rs.getString("NAME_");
                String thumnail = rs.getString("THUM");
                String img = rs.getString("IMG_");
                String content = rs.getString("CONTENT");
                Date created = rs.getDate("CREATED");

                MyReviewVO vo = new MyReviewVO();
                vo.setNum(num);
                vo.setLNum(num2);
                vo.setTitle(name);
                vo.setContent(content);
                vo.setThum(thumnail);
                vo.setImg(img);
                vo.setCreated(created);
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

    // 작성 가능한 후기 조회
    public List<MyReviewVO> myWritableSelect(String id) {
        List<MyReviewVO> list = new ArrayList<>();
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT C.NUM_, L.NUM_ AS LNUM, L.NAME_, L.THUM, C.CREATED, C.WRITTEN\n" +
                    "    FROM T_MY_LECTURE C JOIN T_LECTURE L \n" +
                    "    ON C.LECTURE_NUM = L.NUM_\n" +
                    "    JOIN T_MEMBER M\n" +
                    "    ON C.MEMBER_NUM = M.NUM_" +
                    "    WHERE ID_ = " + "'" + id + "'" + "and WRITTEN = 'N'";

            rs = stmt.executeQuery(sql);

            while(rs.next()) {
                int num = rs.getInt("NUM_");
                int num2 = rs.getInt("LNUM");
                String name = rs.getString("NAME_");
                String thumnail = rs.getString("THUM");
                Date created = rs.getDate("CREATED");
                String written = rs.getString("WRITTEN");

                MyReviewVO vo = new MyReviewVO();
                vo.setMyLectureNum(num);
                vo.setLNum(num2);
                vo.setTitle(name);
                vo.setThum(thumnail);
                vo.setClassDay(created);
                vo.setWritten(written);
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
    // 강의 세부 정보 조회
    public List<LectureVO> classDetail(int id) {
        List<LectureVO> list = new ArrayList<>();
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT L.NUM_, C.NAME_ AS CATEGORY, L.NAME_ AS TITLE, L.THUM\n" +
                    "    FROM T_LECTURE L JOIN T_CATEGORY C \n" +
                    "    ON C.NUM_ = L.CATEGORY_NUM\n" +
                    "    WHERE L.NUM_ = " + "'" + id + "'";
            rs = stmt.executeQuery(sql);

            while(rs.next()) {
                int num = rs.getInt("NUM_");
                String name = rs.getString("TITLE");
                String category = rs.getString("CATEGORY");
                String thumnail = rs.getString("THUM");

                LectureVO vo = new LectureVO();
                vo.setWishNum(num);
                vo.setName(name);
                vo.setCategoryName(category);
                vo.setThum(thumnail);
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

    // 새로운 후기 작성 및 작성 여부 변경
    public boolean writeReviewUpdateWritten(String memNum, String lecNum, String content, String url) {
        Connection conn = null;
        PreparedStatement pStmt1 = null;
        PreparedStatement pStmt2 = null;
        boolean isSuccess = false;

        try {
            conn = Common.getConnection();
            conn.setAutoCommit(false); // 트랜잭션 시작

            String writeReviewSql = "INSERT INTO T_REVIEW(NUM_, LECTURE_NUM, MEMBER_NUM, TITLE, CONTENT, IMG_, CREATED) " +
                    "VALUES(REVIEW_SEQ.NEXTVAL, ?, ?, '제목', ?, ?, SYSDATE)";
            pStmt1 = conn.prepareStatement(writeReviewSql);
            pStmt1.setString(1, lecNum);
            pStmt1.setString(2, memNum);
            pStmt1.setString(3, content);
            pStmt1.setString(4, url);
            int writeReviewResult = pStmt1.executeUpdate();

            String updateWrittenSql = "UPDATE T_MY_LECTURE SET WRITTEN = 'Y' WHERE LECTURE_NUM = ?";
            pStmt2 = conn.prepareStatement(updateWrittenSql);
            pStmt2.setString(1, lecNum);
            int updateWrittenResult = pStmt2.executeUpdate();

            if (writeReviewResult == 1 && updateWrittenResult == 1) {
                conn.commit(); // 커밋
                isSuccess = true;
            } else {
                conn.rollback(); // 롤백
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (conn != null) {
                    conn.rollback(); // 롤백
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            Common.close(pStmt1);
            Common.close(pStmt2);
            Common.close(conn);
        }

        return isSuccess;
    }
    // 후기 수정(전체)
    public boolean updateReview(String num, String content, String url) {
        int result = 0;
        String sql = "UPDATE T_REVIEW SET CONTENT  = ?, IMG_ = ?, CREATED = SYSDATE WHERE NUM_ = ?";

        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, content);
            pStmt.setString(2, url);
            pStmt.setString(3, num);
            result = pStmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(pStmt);
        Common.close(conn);
        if(result == 1) return true;
        else return false;
    }
    // 후기 수정(이미지 삭제만)
    public boolean deleteReviewImg(String num, String url) {
        int result = 0;
        String sql = "UPDATE T_REVIEW SET IMG_ = ?, CREATED = SYSDATE WHERE NUM_ = ?";

        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, url);
            pStmt.setString(2, num);
            result = pStmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(pStmt);
        Common.close(conn);
        if(result == 1) return true;
        else return false;
    }
    // 후기 수정(내용만 수정)
    public boolean updateReviewContent(String num, String content) {
        int result = 0;
        String sql = "UPDATE T_REVIEW SET CONTENT  = ?, CREATED = SYSDATE WHERE NUM_ = ?";

        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, content);
            pStmt.setString(2, num);
            result = pStmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(pStmt);
        Common.close(conn);
        if(result == 1) return true;
        else return false;
    }

    // 강의 썸네일 이미지 업데이트
    public boolean updateImg(String url) {
        int result = 0;
        String sql = "UPDATE T_LECTURE SET THUM  = ? WHERE NUM_ = 6";

        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, url);
            result = pStmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(pStmt);
        Common.close(conn);
        if(result == 1) return true;
        else return false;
    }

    // 결제 내역 조회
    public List<MyPaymentVO> myPayment(String id) {
        List<MyPaymentVO> list = new ArrayList<>();
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT P.NUM_ AS PNUM, L.NUM_, P.MEMBER_NUM, L.NAME_, L.THUM, L.PRICE_, L.ADDR, P.CREATED, P.AMOUNT, P.CANCEL_DATE, P.IS_CANCEL\n" +
                    "    FROM T_LECTURE L JOIN T_PAYMENT P\n" +
                    "    ON L.NUM_ = P.LECTURE_NUM\n" +
                    "    JOIN T_MEMBER M\n" +
                    "    ON P.MEMBER_NUM = M.NUM_\n" +
                    "    WHERE IS_CANCEL = 'N' AND M.ID_ = " + "'" + id + "'";
            rs = stmt.executeQuery(sql);

            while(rs.next()) {
                int num = rs.getInt("PNUM");
                String lectureName = rs.getString("NAME_");
                String thumnail = rs.getString("THUM");
                int price = rs.getInt("PRICE_");
                int amount = rs.getInt("AMOUNT");
                Date created = rs.getDate("CREATED");
                String isCancle = rs.getString("IS_CANCEL");

                MyPaymentVO vo = new MyPaymentVO();
                vo.setNum(num);
                vo.setLectureName(lectureName);
                vo.setThum(thumnail);
                vo.setPrice(price);
                vo.setAmount(amount);
                vo.setCreated(created);
                vo.setIsCancle(isCancle);
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

    // 결제 내역 조회
    public List<MyPaymentVO> myStudent(String name) {
        List<MyPaymentVO> list = new ArrayList<>();
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT P.*, L.NAME_, L.THUM, M.NAME_ AS STUDENT, M.TEL\n" +
                    " FROM T_PAYMENT P JOIN T_LECTURE L\n" +
                    " ON L.NUM_ = P.LECTURE_NUM\n" +
                    " JOIN T_MEMBER M\n" +
                    " ON P.MEMBER_NUM = M.NUM_\n" +
                    " WHERE L.LECTURER = " + "'" + name + "'";
            rs = stmt.executeQuery(sql);

            while(rs.next()) {
                int num = rs.getInt("NUM_");
                String lectureName = rs.getString("NAME_");
                String thumnail = rs.getString("THUM");
                Date created = rs.getDate("CREATED");
                String student = rs.getString("STUDENT");
                String tel = rs.getString("TEL");

                MyPaymentVO vo = new MyPaymentVO();
                vo.setNum(num);
                vo.setLectureName(lectureName);
                vo.setThum(thumnail);
                vo.setCreated(created);
                vo.setStudent(student);
                vo.setTel(tel);
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

    // 장바구니 내역 조회
    public List<MyCartVO> myCart(String id) {
        List<MyCartVO> list = new ArrayList<>();
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT L.NUM_ AS LNUM, L.NAME_, L.THUM, L.PRICE_, L.START_DATE, M.NUM_ AS MNUM, M.ID_, C.NUM_ AS CNUM, C.QUANTITY\n" +
                    "    FROM T_CART C JOIN T_MEMBER M\n" +
                    "    ON C.MEMBER_NUM = M.NUM_\n" +
                    "    JOIN T_LECTURE L\n" +
                    "    ON C.LECTURE_NUM = L.NUM_\n" +
                    "    WHERE M.ID_ = " + "'" + id + "'";
            rs = stmt.executeQuery(sql);

            while(rs.next()) {
                int num = rs.getInt("CNUM");
                int num2 = rs.getInt("LNUM");
                String lectureName = rs.getString("NAME_");
                String thumnail = rs.getString("THUM");
                int price = rs.getInt("PRICE_");
                int quantity = rs.getInt("QUANTITY");
                Date startDate = rs.getDate("START_DATE");
                int totalPrice = price * quantity;

                MyCartVO vo = new MyCartVO();
                vo.setLecNum(num2);
                vo.setCartNum(num);
                vo.setLecName(lectureName);
                vo.setThum(thumnail);
                vo.setPrice(price);
                vo.setQuantity(quantity);
                vo.setStartDate(startDate);
                vo.setTotalPrice(totalPrice);
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

    // 장바구니 삭제
    public boolean deleteCart(List<Integer> cartIds) {
        int result = 0;
        String sql = "DELETE FROM T_CART WHERE NUM_ = ?";

        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            for (int i = 0; i < cartIds.size(); i++) {
                pStmt.setLong(1, cartIds.get(i));
                pStmt.addBatch();
            }

            int[] rowsAffected = pStmt.executeBatch();
            result = rowsAffected.length;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(pStmt);
        Common.close(conn);
        if(result == cartIds.size()) return true;
        else return false;
    }
}
