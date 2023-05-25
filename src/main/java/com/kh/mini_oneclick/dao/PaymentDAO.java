package com.kh.mini_oneclick.dao;
import com.kh.mini_oneclick.common.Common;
import com.kh.mini_oneclick.vo.*;

import java.sql.*;


public class PaymentDAO {
    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    private PreparedStatement pStmt = null;
    private PreparedStatement subsStmt = null;
    private PreparedStatement mySubsStmt = null;

    // 구독 결제 정보 저장(트랜잭션)
    public boolean paySubInsert(PaymentVo vo, SubsVo subsVo, MySubsVo mySubsVo) {
        try {
            conn = Common.getConnection();
            conn.setAutoCommit(false);

            insertPaySub(vo);
            int paymentNum = getGeneratedPaymentNum();
            insertSubs(paymentNum, subsVo);
            int subsNum = getGeneratedSubsNum();
            insertMySubs(mySubsVo, subsNum, subsVo.getType());
            updateMemberSubStatus(vo.getMemberNum());

            conn.commit(); // 모든 작업 성공 시 커밋
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (conn != null) {
                    conn.rollback(); // 예외 발생 시 롤백
                }
            } catch (Exception r) {
                r.printStackTrace();
            }
            return false;
        } finally {
            Common.close(subsStmt);
            Common.close(pStmt);
            Common.close(conn);
        }
    }
    // 구독결제 테이블 정보 저장
    public void insertPaySub(PaymentVo paymentVo) throws SQLException {
        String paySubSql = "INSERT INTO T_PAYSUB(NUM_, MEMBER_NUM, CREATED, AMOUNT, IS_CANCEL) VALUES (PAYSUB_SEQ.NEXTVAL, ?, SYSDATE, ?, 'N')";
        pStmt = conn.prepareStatement(paySubSql, new String[]{"NUM_"});
        pStmt.setInt(1, paymentVo.getMemberNum());
        pStmt.setBigDecimal(2, paymentVo.getAmount());
        int count = pStmt.executeUpdate();
        System.out.println("구독 입금 DB 결과 확인 : " + count);
    }

    // 결제정보 저장
    private void insertPayment(PaymentVo vo) throws SQLException {
        String paymentSql = "INSERT INTO T_PAYMENT(NUM_, LECTURE_NUM, MEMBER_NUM, CREATED, AMOUNT, IS_CANCEL) VALUES (PAYMENT_SEQ.NEXTVAL, ?, ?, SYSDATE, ?, 'N')";
        pStmt = conn.prepareStatement(paymentSql, new String[]{"NUM_"});
        pStmt.setInt(1, vo.getLectureNum());
        pStmt.setInt(2, vo.getMemberNum());
        pStmt.setBigDecimal(3, vo.getAmount());
        int count = pStmt.executeUpdate();
        System.out.println("입금 DB 결과 확인 : " + count);
    }
    // 해당 생성된 LectureNum 으로 MyLecture 테이블에도 데이터 삽입


    // 구독 결제 시 생성된 결제번호 호출
    private int getGeneratedPaymentNum() throws SQLException {
        ResultSet generatedKeys = pStmt.getGeneratedKeys();
        int paymentNum = 0;
        if (generatedKeys.next()) {
            paymentNum = generatedKeys.getInt(1);
            System.out.println(paymentNum);
        }
        return paymentNum;

    }

    // 구독 정보 저장
    private void insertSubs(int paymentNum, SubsVo subsVo) throws SQLException {
        String subsSql = "INSERT INTO T_SUBS(NUM_, PAYSUB_NUM, TYPE_) VALUES (SUBS_SEQ.NEXTVAL, ?, ?)";
        subsStmt = conn.prepareStatement(subsSql, new String[]{"NUM_"});
        subsStmt.setInt(1, paymentNum);
        subsStmt.setString(2, subsVo.getType());
        int result = subsStmt.executeUpdate();
        System.out.println("구독 정보 DB 결과 확인: " + result);
    }

    // 생성된 구독번호 호출
    private int getGeneratedSubsNum() throws SQLException {
        ResultSet generatedKeys = subsStmt.getGeneratedKeys();
        int subsNum = 0;
        if (generatedKeys.next()) {
            subsNum = generatedKeys.getInt(1);
        }
        return subsNum;
    }

    // 회원 구독 여부 Y 업데이트
    private void updateMemberSubStatus(int memberNum) throws SQLException {
        String updateMemberSql = "UPDATE T_MEMBER SET IS_SUB = 'Y' WHERE NUM_ = ?";
        pStmt = conn.prepareStatement(updateMemberSql);
        pStmt.setInt(1, memberNum);
        int updateCount = pStmt.executeUpdate();
        System.out.println("Member 구독여부 확인: " + updateCount);
    }

    // my 구독 저장 결제되는시점부터 START DATE 시작 END DATE는 TYPE마다 다름
    private void insertMySubs(MySubsVo mySubsVo, int subsNum, String type) throws SQLException {
        String mySubsSql = "";

        switch (type) {
            case "3개월":
                mySubsSql = "INSERT INTO T_MY_SUBS(NUM_, SUBSCRIPTION_NUM, MEMBER_NUM, START_DATE, END_DATE) " +
                        "VALUES (MY_SUBS_SEQ.NEXTVAL, ?, ?, SYSDATE, SYSDATE + 90)";
                break;
            case "6개월":
                mySubsSql = "INSERT INTO T_MY_SUBS(NUM_, SUBSCRIPTION_NUM, MEMBER_NUM, START_DATE, END_DATE) " +
                        "VALUES (MY_SUBS_SEQ.NEXTVAL, ?, ?, SYSDATE, SYSDATE + 180)";
                break;
            case "12개월":
                mySubsSql = "INSERT INTO T_MY_SUBS(NUM_, SUBSCRIPTION_NUM, MEMBER_NUM, START_DATE, END_DATE) " +
                        "VALUES (MY_SUBS_SEQ.NEXTVAL, ?, ?, SYSDATE, SYSDATE + 365)";
                break;
        }
        mySubsStmt = conn.prepareStatement(mySubsSql);
        mySubsStmt.setInt(1, subsNum);
        mySubsStmt.setInt(2, mySubsVo.getMemberNum());
        int result = mySubsStmt.executeUpdate();
        System.out.println("MY 구독 저장 여부 확인 : " + result);
        System.out.println(type);
    }

    // 카트 결제
    public boolean paymentCartClass(PaymentVo paymentVo, MyLectureVo myLectureVo, MyCartVO myCartVO) {
        try {
            conn = Common.getConnection();
            conn.setAutoCommit(false);
            // 결제 메소드 사용
            insertPayment(paymentVo);
            // 내 강의에 저장
            insertMyLecture(paymentVo.getMemberNum(), myLectureVo, paymentVo.getLectureNum());
            // 결제하면 my cart 에서 삭제
            deleteCartDB(paymentVo.getMemberNum());

            conn.commit(); // 모든 작업 성공 시 커밋
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (conn != null) {
                    conn.rollback(); // 예외 발생 시 롤백
                }
            } catch (Exception r) {
                r.printStackTrace();
            }
            return false;
        } finally {
            Common.close(pStmt);
            Common.close(conn);
        }
    }


    // 클래스 결제(트랜잭션)
    public boolean paymentClass(PaymentVo vo, MyLectureVo myLectureVo) {
        try {
            conn = Common.getConnection();
            conn.setAutoCommit(false);

            insertPayment(vo); // 결제 메소드 재사용
            // 내 수강페이지 저장
            insertMyLecture(vo.getMemberNum(), myLectureVo, vo.getLectureNum());

            conn.commit(); // 모든 작업 성공 시 커밋
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (conn != null) {
                    conn.rollback(); // 예외 발생 시 롤백
                }
            } catch (Exception r) {
                r.printStackTrace();
            }
            return false;
        } finally {
            Common.close(pStmt);
            Common.close(conn);
        }
    }
    // 수강 강의 정보 저장
    private void insertMyLecture(int memberNum, MyLectureVo myLectureVo, int lectureNum) throws SQLException {
        String myLectureSql = "INSERT INTO T_MY_LECTURE(NUM_, MEMBER_NUM, LECTURE_NUM, CREATED, IS_DELETED, WRITTEN) VALUES (MY_LECTURE_SEQ.NEXTVAL, ?, ?, SYSDATE, 'N', 'N')";
        pStmt = conn.prepareStatement(myLectureSql);
        pStmt.setInt(1, memberNum);
        pStmt.setInt(2, lectureNum);
        int count = pStmt.executeUpdate();
        System.out.println("수강 강의 DB 결과 확인: " + count);
    }
    // 카트 삭제
    private void deleteCartDB(int memberNum) throws SQLException {
        String delCartSql = "DELETE FROM T_CART WHERE MEMBER_NUM = ?";
        pStmt = conn.prepareStatement(delCartSql);
        pStmt.setInt(1, memberNum);
        int cartCount = pStmt.executeUpdate();
        System.out.println("장바구니 삭제 DB 결과 확인: " + cartCount);
    }
    // 구독 환불(트랜잭션)
    public boolean payBack(int num) {
        int result = 0;

        try {
            conn = Common.getConnection();
            conn.setAutoCommit(false);
            // 결제 취소 정보 업뎃
            payBackSub(num);
            // 회원 구독 여부 업데이트
            int memberNum = getMemberNum(num);
            downMemberSub(memberNum);
            // 마이구독 삭제
            deleteMySubs(memberNum);
            // 구독 정보 삭제
            deleteSubs(num);

            conn.commit(); // 모든 작업 성공 시 커밋
            result = 1;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (conn != null) {
                    conn.rollback(); // 예외 발생 시 롤백
                }
            } catch (Exception r) {
                r.printStackTrace();
            }
            result = -1;
        } finally {
            Common.close(pStmt);
            Common.close(conn);
        }
        return result > 0;
    }
    // 결제 취소 정보 업데이트
    private void payBackSub(int paymentNum) throws SQLException {
        String payBackSql = "UPDATE T_PAYSUB SET IS_CANCEL = 'Y', CANCEL_DATE = SYSDATE WHERE NUM_ = ?";
        pStmt = conn.prepareStatement(payBackSql);
        pStmt.setInt(1, paymentNum);
        int result = pStmt.executeUpdate();
        System.out.println("결제 취소 업데이트 결과 확인 : " + result);
    }

    // 구독 삭제
    private void deleteSubs(int paymentNum) throws SQLException {
        String deleteSubsSql = "DELETE FROM T_SUBS WHERE PAYSUB_NUM = ?";
        pStmt = conn.prepareStatement(deleteSubsSql);
        pStmt.setInt(1, paymentNum);
        int result = pStmt.executeUpdate();
        System.out.println("구독 삭제 DB 확인 : " + result);
    }

    // My 구독 삭제
    private void deleteMySubs(int memberNum) throws SQLException {
        String deleteMySubsSql = "DELETE FROM T_MY_SUBS WHERE MEMBER_NUM = ?";
        pStmt = conn.prepareStatement(deleteMySubsSql);
        pStmt.setInt(1, memberNum);
        int result = pStmt.executeUpdate();
        System.out.println("My 구독 삭제 DB 확인 : " + result);
    }

    // 회원 구독 여부 업데이트
    private void downMemberSub(int memberNum) throws SQLException {
        String updateMemberSql = "UPDATE T_MEMBER SET IS_SUB = 'N' WHERE NUM_ = ?";
        pStmt = conn.prepareStatement(updateMemberSql);
        pStmt.setInt(1, memberNum);
        int updateCount = pStmt.executeUpdate();
        System.out.println("Member 구독 여부 확인 : " + updateCount);
    }

    // 업뎃된 PaymentNum 에 대한 SubsNum 가져오기
    private int getSubsNum(int paymentNum) throws SQLException {
        String getSubsNumSql = "SELECT SUBS_NUM FROM T_SUBS WHERE PAYMENT_NUM = ?";
        pStmt = conn.prepareStatement(getSubsNumSql);
        pStmt.setInt(1, paymentNum);
        ResultSet rs = pStmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("SUBS_NUM");
        } else {
            //기본값 반환
            return 0;
        }
    }
    // 업뎃된 PaymentNum 에 대한 MemberNum 가져오기
    private int getMemberNum(int paymentNum) throws SQLException {
        String getMemberNumSql = "SELECT MEMBER_NUM FROM T_PAYMSUB WHERE NUM_ = ?";
        pStmt = conn.prepareStatement(getMemberNumSql);
        pStmt.setInt(1, paymentNum);
        ResultSet rs = pStmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("MEMBER_NUM");
        } else {
            //기본값 반환
            return 0;
        }
    }
    // 업뎃된 PaymentNum 에 대한 LectureNum 가져오기
    private int getLectureNum(int paymentNum) throws SQLException {
        String getLectureNumSql = "SELECT LECTURE_NUM FROM T_PAYMENT WHERE NUM_ = ?";
        pStmt = conn.prepareStatement(getLectureNumSql);
        pStmt.setInt(1, paymentNum);
        ResultSet rs = pStmt.executeQuery();
        if(rs.next()) {
            return rs.getInt("LECUTRE_NUM");
        } else {
            return 0;
        }
    }
    // 클래스 결제 환불
    public boolean payBackClass(int num) {
        int result = 0;

        try {
            conn = Common.getConnection();
            conn.setAutoCommit(false);

            // 결제 취소 정보 업데이트
            classPayBack(num);
            // 수강 강의 정보 삭제
            int memberNum = getPayBackMemberNum(num);
            delMyLecture(memberNum);

            conn.commit(); // 모든 작업 성공 시 커밋
            result = 1; // 성공 시 결과값을 1로 설정
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (conn != null) {
                    conn.rollback(); // 예외 발생 시 롤백
                }
            } catch (Exception r) {
                r.printStackTrace();
            }
        } finally {
            Common.close(pStmt);
            Common.close(conn);
        }
        return result > 0;
    }

    // 결제 취소 정보 업데이트
    private void classPayBack(int paymentNum) throws SQLException {
        String payBackSql = "UPDATE T_PAYMENT SET IS_CANCEL = 'Y', CANCEL_DATE = SYSDATE WHERE NUM_ = ?";
        pStmt = conn.prepareStatement(payBackSql);
        pStmt.setInt(1, paymentNum);
//        pStmt.setDate(2, paymentVo.getCancelDate());
        int result = pStmt.executeUpdate();
        System.out.println("결제 취소 업데이트 결과 확인 : " + result);
    }

    // 수강 강의 정보 삭제
    private void delMyLecture(int memberNum) throws SQLException {
        String myLectureSql = "DELETE FROM T_MY_LECTURE WHERE MEMBER_NUM = ?";
        pStmt = conn.prepareStatement(myLectureSql);
        pStmt.setInt(1, memberNum);
        int count = pStmt.executeUpdate();
        System.out.println("수강 강의 DB 결과 확인: " + count);
    }

    // 업뎃된 환불 PaymentNum 에 대한 MemberNum 가져오기
    private int getPayBackMemberNum(int paymentNum) throws SQLException {
        String getMemberNumSql = "SELECT MEMBER_NUM FROM T_PAYMENT WHERE NUM_ = ?";
        pStmt = conn.prepareStatement(getMemberNumSql);
        pStmt.setInt(1, paymentNum);
        ResultSet rs = pStmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("MEMBER_NUM");
        } else {
            //기본값 반환
            return 0;
        }
    }

    // 구독권 있는사람이 결제할 경우(앞단에서 memberNum 에 isSub 이 Y 일 경우)
//    private int useSubPay(int memberNum) throws SQLException {
//
//    }




    // 다음주에 해야할것
    // 합친 코드에서 id 로그인할때 들어온 데이터 배열을  context api 에 데이터 쪼개 받아서 useState 에 각각 데이터가 잘 들어가는지 확인하기
    // lectureNum 값 context api 로 받아와서 최종 테스트해보기
    // 일단 코드 다 합치고, 내구독권 결제해보고 잘 들어간다면 ~ 내 결제내역가서 context api 로 paymentNum 값만 뽑아와서 환불 테스트해보기
    // 그쪽까지 성공하면 이제 내 강의 결제해보고 잘 들어가면 ~ 내 결제내역가서 context api 로 paymentNum 값 뽑아와서 클래스 환불 테스트해보기
    // 여기까지 성공하면 이번 플젝 구현 끝! deadLine 5/26.. 열심히 화이팅..
}
