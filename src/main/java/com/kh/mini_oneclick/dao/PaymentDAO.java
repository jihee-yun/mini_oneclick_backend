package com.kh.mini_oneclick.dao;
import com.kh.mini_oneclick.common.Common;
import com.kh.mini_oneclick.vo.MySubsVo;
import com.kh.mini_oneclick.vo.PaymentVo;
import com.kh.mini_oneclick.vo.SubsVo;

import java.sql.*;


public class PaymentDAO {
    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    private PreparedStatement pStmt = null;
    private PreparedStatement subsStmt = null;
    private PreparedStatement mySubsStmt = null;

    // 구독 결제 정보 저장
    public boolean paymentInsert(PaymentVo vo, SubsVo subsVo, MySubsVo mySubsVo) {
        try {
            conn = Common.getConnection();
            conn.setAutoCommit(false);

            insertPayment(vo);
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

    // 생성된 결제번호 호출
    private int getGeneratedPaymentNum() throws SQLException {
        ResultSet generatedKeys = pStmt.getGeneratedKeys();
        int paymentNum = 0;
        if (generatedKeys.next()) {
            paymentNum = generatedKeys.getInt(1);
        }
        return paymentNum;
    }

    // 구독 정보 저장
    private void insertSubs(int paymentNum, SubsVo subsVo) throws SQLException {
        String subsSql = "INSERT INTO T_SUBS(NUM_, PAYMENT_NUM, TYPE_) VALUES (SUBS_SEQ.NEXTVAL, ?, ?)";
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
    // 회원 구독 상태 여부 변경
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
            case "3개월" :
                mySubsSql = "INSERT INTO T_MY_SUBS(NUM_, SUBS_NUM, MEMBER_NUM, START_DATE, END_DATE) " +
                        "VALUES (MY_SUBS_SEQ.NEXTVAL, ?, ?, SYSDATE, SYSDATE + 90)";
                break;
            case "6개월" :
                mySubsSql = "INSERT INTO T_MY_SUBS(NUM_, SUBS_NUM, MEMBER_NUM, START_DATE, END_DATE) " +
                        "VALUES (MY_SUBS_SEQ.NEXTVAL, ?, ?, SYSDATE, SYSDATE + 180)";
                break;
            case "12개월" :
                mySubsSql = "INSERT INTO T_MY_SUBS(NUM_, SUBS_NUM, MEMBER_NUM, START_DATE, END_DATE) " +
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



    // 구독 환불(수정해야함 객체지향적으로 아직 못했어요..)
    public boolean payBack(int num) {
        int result = 0;

        try {
            conn = Common.getConnection();
            conn.setAutoCommit(false);

            // 결제 취소 정보 업데이트
            String updatePaymentSql = "UPDATE T_PAYMENT SET IS_CANCEL = 'Y' WHERE NUM_ = ?";
            pStmt = conn.prepareStatement(updatePaymentSql);
            pStmt.setInt(1, num);
            result = pStmt.executeUpdate();
            System.out.println("결제 취소 DB 결과 확인: " + result);

            // 구독 정보 삭제
            String deleteSubsSql = "DELETE FROM T_SUBS WHERE PAYMENT_NUM = ?";
            pStmt = conn.prepareStatement(deleteSubsSql);
            pStmt.setInt(1, num);
            result = pStmt.executeUpdate();
            System.out.println("구독 정보 삭제 DB 결과 확인: " + result);

            // 회원 구독 여부 업데이트
            String updateMemberSql = "UPDATE T_MEMBER SET IS_SUB = 'N' WHERE MEMBER_NUM = (SELECT MEMBER_NUM FROM T_PAYMENT WHERE NUM_ = ?)";
            pStmt = conn.prepareStatement(updateMemberSql);
            pStmt.setInt(1, num);
            int updateCount = pStmt.executeUpdate();
            System.out.println("Member 구독 여부 확인: " + updateCount);

            conn.commit(); // 모든 작업 성공 시 커밋
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
}