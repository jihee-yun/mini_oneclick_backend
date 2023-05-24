package com.kh.mini_oneclick.controller;

import com.kh.mini_oneclick.dao.CategoryDAO;
import com.kh.mini_oneclick.vo.CategoryVo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class CategoryController {
    private CategoryDAO categoryDAO = new CategoryDAO();

    // 앞단에서 요청한 카테고리 목록 조회1
    @GetMapping("/category")
    public ResponseEntity<List<CategoryVo>> categoryList() {
        List<CategoryVo> categoryVoList = categoryDAO.getAllCategories();
        return new ResponseEntity<>(categoryVoList, HttpStatus.OK); // OK = 200(성공)
    }
    // 카테고리별 강의 세부 조회
    @GetMapping("/category/details")
    public ResponseEntity<List<CategoryVo>> categoryDetails(@RequestParam int categoryNum) {
        System.out.println("categoryNum: " + categoryNum);
        List<CategoryVo> list = categoryDAO.getLecturesByCategory(categoryNum);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}