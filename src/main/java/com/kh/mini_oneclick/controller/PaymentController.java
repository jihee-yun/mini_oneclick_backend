package com.kh.mini_oneclick.controller;

import com.kh.mini_oneclick.dao.PaymentDAO;
import com.kh.mini_oneclick.vo.MySubsVo;
import com.kh.mini_oneclick.vo.PaymentVo;
import com.kh.mini_oneclick.vo.SubsVo;
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
    // 구독권 결제 환불
    @PostMapping("/payback")
    public ResponseEntity<Boolean> paymentRefund(@RequestBody Map<String, Integer> refundData) {
        int paymentNum = refundData.get("paymentNum");
        PaymentDAO dao = new PaymentDAO();
        boolean isRefunded = dao.payBack(paymentNum);
        return new ResponseEntity<>(isRefunded, HttpStatus.OK);
    }

}