package com.kh.mini_oneclick.controller;

import com.kh.mini_oneclick.dao.LectureDAO;
import com.kh.mini_oneclick.vo.LectureVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class LectureController {
    // 강의 정보 출력
    @GetMapping("/class")
    public ResponseEntity<List<LectureVO>> lectureList(@RequestParam String category, @RequestParam String lectureNum) {
        System.out.println("LectureList 메소드 실행 !\n category : " + category + ", lectureNum : " + lectureNum);
        LectureDAO dao = new LectureDAO();
        int parseId = Integer.parseInt(category);
        int parseNum = Integer.parseInt(lectureNum);
        List<LectureVO> result = dao.LectureList(parseId, parseNum);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}