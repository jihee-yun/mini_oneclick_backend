package com.kh.mini_oneclick.controller;

import com.kh.mini_oneclick.dao.LoadReviewListDAO;
import com.kh.mini_oneclick.dao.MemberDAO;
import com.kh.mini_oneclick.dao.WriteReviewDAO;
import com.kh.mini_oneclick.vo.LoadReviewListVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class WriteReviewController {
    @PostMapping("/class/reviewWrite")
    public ResponseEntity<Boolean> WriteReview(@RequestBody Map<String, String> reviewData) {
        int getLecNum = Integer.parseInt(reviewData.get("lecNum"));
        String getMemNum = reviewData.get("memNum");
        String getTitle = reviewData.get("title");
        String getContent = reviewData.get("content");
        String getImg = reviewData.get("img");
        WriteReviewDAO dao = new WriteReviewDAO();
        boolean isTrue = dao.WriteReview(getLecNum, getMemNum, getTitle, getContent, getImg);
        return new ResponseEntity<>(isTrue, HttpStatus.OK);
    }

    // 리뷰 불러오기
    @GetMapping("/class/loadReview")
    public ResponseEntity<List<LoadReviewListVO>> LoadReviewList(@RequestParam String num) {
        System.out.println("LoadReviewList에서 넘어온 값 : " + num);
        LoadReviewListDAO dao = new LoadReviewListDAO();
        List<LoadReviewListVO> list = dao.loadReviewList(num);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}