package com.kh.mini_oneclick.controller;

import com.kh.mini_oneclick.dao.DetailLectureInfoDAO;
import com.kh.mini_oneclick.dao.LectureDAO;
import com.kh.mini_oneclick.vo.DetailLectureInfoVO;
import com.kh.mini_oneclick.vo.LectureVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class LectureController {
    // 강의 정보 출력1
    @GetMapping("/class")
    public ResponseEntity<List<DetailLectureInfoVO>> lectureList(@RequestParam String category, @RequestParam String lectureNum) {
        System.out.println("LectureList 메소드 실행 !\n category : " + category + ", lectureNum : " + lectureNum);
        DetailLectureInfoDAO dao = new DetailLectureInfoDAO();
        int parseId = Integer.parseInt(category);
        int parseNum = Integer.parseInt(lectureNum);
        List<DetailLectureInfoVO> result = dao.LectureList(parseId, parseNum);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/regWishChk")
    public ResponseEntity<Boolean> wishChk (@RequestParam String lectureNum, @RequestParam String memberNum) {
        System.out.println("wishChk 메소드 실행! lectureNum : " + lectureNum + ", memberNum : " + memberNum);
        DetailLectureInfoDAO dao = new DetailLectureInfoDAO();
        int parsedLectureNum  = Integer.parseInt(lectureNum);
        int parsedMemberNum  = Integer.parseInt(memberNum);
        Boolean istrue = dao.checkWishReg(parsedLectureNum, parsedMemberNum);
        return new ResponseEntity<>(istrue, HttpStatus.OK);
    }

    @PostMapping("/acceptWishList")
    public ResponseEntity<Boolean> acceptWish(@RequestBody String lectureNum, @RequestBody String memberNum) {
        System.out.println("acceptWish 실행! lectureNum : " + lectureNum + ", memberNum : " + memberNum);
        DetailLectureInfoDAO dao = new DetailLectureInfoDAO();
        int parsedLectureNum  = Integer.parseInt(lectureNum);
        int parsedMemberNum  = Integer.parseInt(memberNum);
        Boolean istrue = dao.acceptWish(parsedLectureNum, parsedMemberNum);
        return new ResponseEntity<>(istrue, HttpStatus.OK);
    }

    @PostMapping("/delWishList")
    public ResponseEntity<Boolean> delWish(@RequestBody String lectureNum, @RequestBody String memberNum) {
        System.out.println("delWishList 실행! lectureNum : " + lectureNum + ", memberNum : " + memberNum);
        int parsedLectureNum  = Integer.parseInt(lectureNum);
        int parsedMemberNum  = Integer.parseInt(memberNum);
        DetailLectureInfoDAO dao = new DetailLectureInfoDAO();
        boolean istrue = dao.deleteWish(parsedLectureNum, parsedMemberNum);
        return new ResponseEntity<>(istrue, HttpStatus.OK);
    }
}