package com.kh.mini_oneclick.controller;

import com.kh.mini_oneclick.dao.LectureDAO;
import com.kh.mini_oneclick.dao.MemberDAO;
import com.kh.mini_oneclick.vo.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000") // 동일 출처 에러를 찾아주는? 웹 페이지의 제한된 자원을 외부 도메인에서 접근을 허용해주는 매커니즘
@RestController
public class MyPageController {
    // POST : 로그인
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> memberLogin(@RequestBody Map<String, String> loginData, HttpSession session) {
        String id = loginData.get("id"); // 프론트의 memberLogin에서 id 키를 찾고 그 값을 가져 옴
        String pwd = loginData.get("pwd");
        MemberDAO dao = new MemberDAO();
        boolean result = dao.loginCheck(id, pwd);
        Map<String, Object> response = new HashMap<>();
        response.put("success", result);
        if(result) {
            session.setAttribute("userId", id);
            response.put("userId", id);
        }
        return ResponseEntity.ok(response);
    }

    // GET : 회원 조회
    @GetMapping("/myInfo")
    public ResponseEntity<List<MemberVO>> myInfoList(@RequestParam String id) {
        System.out.println("ID : " + id);
        MemberDAO dao = new MemberDAO();
        List<MemberVO> list = dao.myInfoSelect(id);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // GET : 회원 조회
    @GetMapping("/member")
    public ResponseEntity<List<MemberVO>> memberList(@RequestParam String id) {
        System.out.println("ID : " + id);
        MemberDAO dao = new MemberDAO();
        List<MemberVO> list = dao.memberSelect(id);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // GET : 회원 가입 여부 확인
    @GetMapping("/check")
    public ResponseEntity<Boolean> memberCheck(@RequestParam String id) {
        MemberDAO dao = new MemberDAO();
        boolean isTrue = dao.regMemberCheck(id);
        return new ResponseEntity<>(isTrue, HttpStatus.OK);
    }

    // 회원 가입
    @PostMapping("/new")
    public ResponseEntity<Boolean> memRegister(@RequestBody Map<String, String> regData) {
        String getName = regData.get("name");
        String getMail = regData.get("mail");
        String getTel = regData.get("tel");
        String getId = regData.get("id");
        String getPwd = regData.get("pwd");
        String getIsTeacher = regData.get("isTeacher");
        MemberDAO dao = new MemberDAO();
        boolean isTrue = dao.memRegister(getName, getMail, getTel, getId, getPwd, getIsTeacher);
        return new ResponseEntity<>(isTrue, HttpStatus.OK);
    }

    // POST : 회원 탈퇴
    @PostMapping("/del")
    public ResponseEntity<Boolean> memberDelete(@RequestBody Map<String, String> delData) {
        String getId = delData.get("id");
        System.out.println("ID : " + getId);
        MemberDAO dao = new MemberDAO();
        boolean isTrue = dao.memberDelete(getId);
        return new ResponseEntity<>(isTrue, HttpStatus.OK);
    }

    // 전화번호 수정
    @PostMapping("/updateTel")
    public ResponseEntity<Boolean> updateTel(@RequestBody Map<String, String> upTel) {
        String getTel = upTel.get("tel");
        String getId = upTel.get("id");
        MemberDAO dao = new MemberDAO();
        boolean isTrue = dao.updateTel(getTel,getId);
        return new ResponseEntity<>(isTrue, HttpStatus.OK);
    }

    // 전화번호 중복값 체크
    @GetMapping("/telCheck")
    public ResponseEntity<Boolean> telRegCheck(@RequestParam String tel) {
        MemberDAO dao = new MemberDAO();
        boolean isTrue = dao.regTelCheck(tel);
        return new ResponseEntity<>(isTrue, HttpStatus.OK);
    }

    // 이메일 수정
    @PostMapping("/updateMail")
    public ResponseEntity<Boolean> updateMail(@RequestBody Map<String, String> upMail) {
        String getMail = upMail.get("mail");
        String getId = upMail.get("id");
        MemberDAO dao = new MemberDAO();
        boolean isTrue = dao.updateMail(getMail,getId);
        return new ResponseEntity<>(isTrue, HttpStatus.OK);
    }
    // 이메일 중복값 체크
    @GetMapping("/mailCheck")
    public ResponseEntity<Boolean> mailRegCheck(@RequestParam String mail) {
        MemberDAO dao = new MemberDAO();
        boolean isTrue = dao.regMailCheck(mail);
        return new ResponseEntity<>(isTrue, HttpStatus.OK);
    }

    // 내 구독권 조회
    @GetMapping("/mySubs")
    public ResponseEntity<List<MemberVO>> mySubList(@RequestParam String id) {
        System.out.println("ID : " + id);
        MemberDAO dao = new MemberDAO();
        List<MemberVO> list = dao.mySubsSelect(id);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    // 내 강의 조회
    @GetMapping("/myClass")
    public ResponseEntity<List<LectureVO>> myClassList(@RequestParam String id) {
        System.out.println("ID : " + id);
        MemberDAO dao = new MemberDAO();
        List<LectureVO> list = dao.myClassSelect(id);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    // 내 위시 조회
    @GetMapping("/myWish")
    public ResponseEntity<List<LectureVO>> myWishList(@RequestParam String id) {
        System.out.println("ID : " + id);
        MemberDAO dao = new MemberDAO();
        List<LectureVO> list = dao.myWishSelect(id);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // 위시 삭제 및 좋아요 카운트 업데이트
    @PostMapping("/delWish")
    public ResponseEntity<Boolean> wishDelete(@RequestBody Map<String, String> delData) {
        String getNum = delData.get("num");
        String getWishNum = delData.get("wishNum");
        System.out.println("NUM : " + getNum);
        System.out.println("wishNum : " + getWishNum);
        MemberDAO dao = new MemberDAO();
        boolean isSuccess = dao.wishDeleteLikeUpdate(getNum, getWishNum);
        if (isSuccess) {
            return new ResponseEntity<>(isSuccess, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // 작성 후기 조회
    @GetMapping("/myReview")
    public ResponseEntity<List<MyReviewVO>> myReviewList(@RequestParam String id) {
        System.out.println("ID : " + id);
        MemberDAO dao = new MemberDAO();
        List<MyReviewVO> list = dao.myReviewSelect(id);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    // 작성 가능한 후기 조회
    @GetMapping("/myWritable")
    public ResponseEntity<List<MyReviewVO>> myWritableList(@RequestParam String id) {
        System.out.println("ID : " + id);
        MemberDAO dao = new MemberDAO();
        List<MyReviewVO> list = dao.myWritableSelect(id);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    // 클래스 세부 정보 조회
    @GetMapping("/classDetail")
    public ResponseEntity<List<LectureVO>> classDetailList(@RequestParam int id) {
        System.out.println("ID : " + id);
        MemberDAO dao = new MemberDAO();
        List<LectureVO> list = dao.classDetail(id);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


    // 새로운 후기 작성 및 상태값 변경
    @PostMapping("/writeReview")
    public ResponseEntity<Boolean> writeReview(@RequestBody Map<String, String> write) {
        String getMemNum = write.get("memNum");
        String getLecNum = write.get("lecNum");
        String getContent = write.get("content");
        String getImgUrl = write.get("url");
        MemberDAO dao = new MemberDAO();
        boolean isSuccess = dao.writeReviewUpdateWritten(getMemNum, getLecNum, getContent, getImgUrl);
        if (isSuccess) {
            return new ResponseEntity<>(isSuccess, HttpStatus.OK);
        }
        return new ResponseEntity<>(isSuccess, HttpStatus.OK);
    }

    // 후기 수정
    @PostMapping("/updateReview")
    public ResponseEntity<Boolean> updateReview(@RequestBody Map<String, String> write) {
        String getNum = write.get("num");
        String getContent = write.get("content");
        MemberDAO dao = new MemberDAO();
        boolean isTrue = dao.updateReview(getNum, getContent);
        return new ResponseEntity<>(isTrue, HttpStatus.OK);
    }
    // 이미지 업데이트(강의 썸네일)
    @PostMapping("/updateImg")
    public ResponseEntity<Boolean> updateImg(@RequestBody Map<String, String> update) {
        String getUrl = update.get("img");
        System.out.println(getUrl);
        MemberDAO dao = new MemberDAO();
        boolean isTrue = dao.updateImg(getUrl);
        return new ResponseEntity<>(isTrue, HttpStatus.OK);
    }
    // 결제 내역 조회
    @GetMapping("/myPayment")
    public ResponseEntity<List<MyPaymentVO>> myPaymentList(@RequestParam String id) {
        System.out.println("ID : " + id);
        MemberDAO dao = new MemberDAO();
        List<MyPaymentVO> list = dao.myPayment(id);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    // 장바구니 내역 조회
    @GetMapping("/myCart")
    public ResponseEntity<List<MyCartVO>> myCartList(@RequestParam String id) {
        System.out.println("ID : " + id);
        MemberDAO dao = new MemberDAO();
        List<MyCartVO> list = dao.myCart(id);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // 장바구니 내역 삭제
    @PostMapping("/delCart")
    public ResponseEntity<Boolean> deleteCart(@RequestBody Map<String, Object> request) {
        List<Integer> cartIds = (List<Integer>) request.get("items");
        for (Integer cartId : cartIds) {
            System.out.println(cartId);
        }
        MemberDAO dao = new MemberDAO();
        boolean isTrue = dao.deleteCart(cartIds);
        return new ResponseEntity<>(isTrue, HttpStatus.OK);
    }

    // 전체 클래스 조회 (검색)
    @GetMapping("/lecture")
    public ResponseEntity<List<LectureVO>> lectureList(@RequestParam String num) {
        System.out.println("num : " +  num);
        MemberDAO dao = new MemberDAO();
        List<LectureVO> list = dao.lectureSelect(num);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // 베이킹 클래스 조회
    @GetMapping("/baking")
    public ResponseEntity<List<LectureVO>> bakingList(@RequestParam String num) {
        System.out.println("num : " +  num);
        LectureDAO dao = new LectureDAO();
        List<LectureVO> list = dao.bakingSelect(num);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    // 좋아요 순으로 조회 (사이드바)
    @GetMapping("/sidebar")
    public ResponseEntity<List<LectureVO>> likeCountList(@RequestParam String num) {
        System.out.println("num : " +  num);
        LectureDAO dao = new LectureDAO();
        List<LectureVO> list = dao.likeCountSelect(num);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    // 메인화면 추천 클래스 (좋아요 낮은 순 3개)
    @GetMapping("/down")
    public ResponseEntity<List<LectureVO>> downList(@RequestParam String num) {
        System.out.println("num : " + num);
        LectureDAO dao = new LectureDAO();
        List<LectureVO> list = dao.recommendLecture(num);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // 아이디 찾기
    @PostMapping("/lostId")
    public ResponseEntity<String> idList (@RequestBody Map<String, String> regData) {
        String getName = regData.get("name");
        String getMail = regData.get("mail");
        System.out.println("이름 : " + getName + ", 메일 : " + getMail);
        MemberDAO dao = new MemberDAO();
        String getId = dao.memberId(getName, getMail);
        return new ResponseEntity<>(getId, HttpStatus.OK);
    }
    // 비밀번호 찾기
    @PostMapping("/lostPw")
    public ResponseEntity<String> pwList (@RequestBody Map<String, String> regData) {
        String getId = regData.get("id");
        String getMail = regData.get("mail");
        String getName = regData.get("name");
        System.out.println("아이디 : " + getId + ", 메일 : " + getMail);
        MemberDAO dao = new MemberDAO();
        String getPw = dao.memberPw(getName, getId, getMail);
        return new ResponseEntity<>(getPw, HttpStatus.OK);
    }
}
