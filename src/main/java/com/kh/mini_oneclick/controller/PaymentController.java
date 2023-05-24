package com.kh.mini_oneclick.controller;

import com.kh.mini_oneclick.dao.PaymentDAO;
import com.kh.mini_oneclick.vo.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class PaymentController {
    // 구독 결제
    @PostMapping("/payment")
    public ResponseEntity<Boolean> paymentInsert(@RequestBody Map<String, String> payData) {
        int lectureNum = Integer.parseInt(payData.get("lectureNum"));
        int memberNum = Integer.parseInt(payData.get("memberNum"));
        BigDecimal amount = new BigDecimal(payData.get("amount"));

        PaymentVo payment = new PaymentVo();
        payment.setLectureNum(lectureNum);
        payment.setMemberNum(memberNum);
        payment.setAmount(amount);

        SubsVo subs = new SubsVo();
        subs.setType(payData.get("type"));

        MySubsVo mySubs = new MySubsVo();
        mySubs.setMemberNum(memberNum);

        PaymentDAO dao = new PaymentDAO();
        boolean isTrue = dao.paymentInsert(payment, subs, mySubs);
        return new ResponseEntity<>(isTrue, HttpStatus.OK);
    }
    // 클래스 결제
    @PostMapping("/payClass")
    public ResponseEntity<Boolean> paymentClass (@RequestBody Map<String, String> payClassData) {
        int lectureNum = Integer.parseInt(payClassData.get("lectureNum"));
        int memberNum = Integer.parseInt(payClassData.get("memberNum"));
        BigDecimal amount = new BigDecimal(payClassData.get("amount"));

        PaymentVo payment = new PaymentVo();
        payment.setLectureNum(lectureNum);
        payment.setMemberNum(memberNum);
        payment.setAmount(amount);

        MyLectureVo myLecture = new MyLectureVo();
        myLecture.setMemberNum(memberNum);

        PaymentDAO dao = new PaymentDAO();
        boolean isTrue = dao.paymentClass(payment, myLecture);
        return new ResponseEntity<>(isTrue, HttpStatus.OK);

    }
//    // 장바구니 결제
//    @PostMapping("/payCart")
//    public ResponseEntity<Boolean> paymentCartClass(@RequestBody Map<String, String> payCartData) {
//        try {
//            int lectureNum = Integer.parseInt(payCartData.get("lectureNum"));
//            int memberNum = Integer.parseInt(payCartData.get("memberNum"));
//            BigDecimal amount = new BigDecimal(payCartData.get("amount"));
//
//            PaymentVo paymentVo = new PaymentVo();
//            paymentVo.setLectureNum(lectureNum);
//            paymentVo.setMemberNum(memberNum);
//            paymentVo.setAmount(amount);
//
//            MyLectureVo myLectureVo = new MyLectureVo();
//            myLectureVo.setMemberNum(memberNum);
//            myLectureVo.setLectureNum(lectureNum);
//
//            MyCartVO myCartVO = new MyCartVO();
//            myCartVO.setMemNum(memberNum);
//            myCartVO.setLecNum(lectureNum);
//
//        }
//    }


    // 구독권 결제 환불
    @PostMapping("/payBack")
    public ResponseEntity<Boolean> paymentRefund(@RequestBody Map<String, Integer> refundData) {
        int paymentNum = refundData.get("paymentNum");
        PaymentDAO dao = new PaymentDAO();
        boolean isRefunded = dao.payBack(paymentNum);
        return new ResponseEntity<>(isRefunded, HttpStatus.OK);
    }
    // 클래스 결제 환불
    @PostMapping("/payBackClass")
    public ResponseEntity<Boolean> payBackClass(@RequestBody Map<String, Integer> refundData) {
        int paymentNum = refundData.get("paymentNum");
        PaymentDAO dao = new PaymentDAO();
        boolean isRefunded = dao.payBackClass(paymentNum);
        return new ResponseEntity<>(isRefunded, HttpStatus.OK);
    }

}