package com.kh.mini_oneclick.controller;

import com.kh.mini_oneclick.dao.DetailLectureInfoDAO;
import com.kh.mini_oneclick.dao.LectureDAO;
import com.kh.mini_oneclick.vo.DetailLectureInfoVO;
import com.kh.mini_oneclick.vo.ImgVO;
import com.kh.mini_oneclick.vo.LectureVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class LectureController {
    // 강의 정보 출력
    @GetMapping("/class")
    public ResponseEntity<Map<String, Object>> lectureList(@RequestParam String category, @RequestParam String lectureNum) {
        System.out.println("LectureList 메소드 실행 !\n category : " + category + ", lectureNum : " + lectureNum);
        DetailLectureInfoDAO dao = new DetailLectureInfoDAO();
        int parsedCategory  = Integer.parseInt(category);
        int parsedLectureNum  = Integer.parseInt(lectureNum);
        List<DetailLectureInfoVO> lectureList = dao.LectureList(parsedCategory, parsedLectureNum );
        List<ImgVO> imgList = dao.getLectureImg(parsedLectureNum);

        Map<String, Object> result = new HashMap<>();
        result.put("lectureList", lectureList);
        result.put("imgList", imgList);

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
    public ResponseEntity<Boolean> acceptWish(@RequestBody Map<String, String> data) {
        System.out.println("acceptWish 실행!");
        DetailLectureInfoDAO dao = new DetailLectureInfoDAO();
        String getLectureNum = data.get("lectureNum");
        String getMemberNum = data.get("memberNum");
        int parsedLectureNum  = Integer.parseInt(getLectureNum);
        int parsedMemberNum  = Integer.parseInt(getMemberNum);
        Boolean istrue = dao.acceptWish(parsedLectureNum, parsedMemberNum);
        return new ResponseEntity<>(istrue, HttpStatus.OK);
    }

    @PostMapping("/delWishList")
    public ResponseEntity<Boolean> delWish(@RequestBody Map<String, String> data) {
        System.out.println("delWishList 실행!");
        DetailLectureInfoDAO dao = new DetailLectureInfoDAO();
        String getLectureNum = data.get("lectureNum");
        String getMemberNum = data.get("memberNum");
        int parsedLectureNum  = Integer.parseInt(getLectureNum);
        int parsedMemberNum  = Integer.parseInt(getMemberNum);
        boolean istrue = dao.deleteWish(parsedLectureNum, parsedMemberNum);
        return new ResponseEntity<>(istrue, HttpStatus.OK);
    }

    @GetMapping("/regCartChk")
    public ResponseEntity<Boolean> cartChk (@RequestParam String lectureNum, @RequestParam String memberNum) {
        System.out.println("cartChk 실행!");
        DetailLectureInfoDAO dao = new DetailLectureInfoDAO();
        int parsedLectureNum  = Integer.parseInt(lectureNum);
        int parsedMemberNum  = Integer.parseInt(memberNum);
        Boolean istrue = dao.checkCartReg(parsedLectureNum, parsedMemberNum);
        return new ResponseEntity<>(istrue, HttpStatus.OK);
    }

    @PostMapping("/acceptCartList")
    public ResponseEntity<Boolean> acceptCart(@RequestBody Map<String, String> data) {
        System.out.println("acceptCart 실행!");
        DetailLectureInfoDAO dao = new DetailLectureInfoDAO();
        String getLectureNum = data.get("lectureNum");
        String getMemberNum = data.get("memberNum");
        String getQuantity = data.get("quantity");
        int parsedLectureNum  = Integer.parseInt(getLectureNum);
        int parsedMemberNum  = Integer.parseInt(getMemberNum);
        int parsedQuantity = Integer.parseInt(getQuantity);
        Boolean istrue = dao.acceptCart(parsedLectureNum, parsedMemberNum, parsedQuantity);
        return new ResponseEntity<>(istrue, HttpStatus.OK);
    }
}