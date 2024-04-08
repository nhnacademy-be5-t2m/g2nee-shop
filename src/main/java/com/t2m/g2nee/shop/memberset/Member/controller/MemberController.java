package com.t2m.g2nee.shop.memberset.Member.controller;

import com.t2m.g2nee.shop.memberset.Member.dto.request.CustomerLoginRequestDto;
import com.t2m.g2nee.shop.memberset.Member.dto.request.MemberLoginRequestDto;
import com.t2m.g2nee.shop.memberset.Member.dto.request.SignUpMemberRequestDto;
import com.t2m.g2nee.shop.memberset.Member.dto.response.MemberResponse;
import com.t2m.g2nee.shop.memberset.Member.service.Impl.MemberServiceImpl;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 회원 정보를 다루는 컨트롤러 입니다.
 *
 * @author : 정지은
 * @since : 1.0
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/shop")
public class MemberController {
    private final MemberServiceImpl memberService;

    /**
     * 회원가입을 처리하는 메소드
     *
     * @param signUpDto 회원가입시 기입하는 회원정보가 입력
     * @return 회원정보 저장 후 기본 회원정보 response 반환
     */
    @PostMapping("/member/signup")
    public ResponseEntity<MemberResponse> memberSignUp(@Valid @RequestBody SignUpMemberRequestDto signUpDto) {
        MemberResponse memberResponse = memberService.signUp(signUpDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(memberResponse);

    }

    /**
     * 로그인을 처리하는 메소드
     *
     * @param loginDto 로그인시 기입하는 로그인정보가 입력
     * @return 로그인이 가능한지 여부를 boolean 으로 반환
     */
    @PostMapping("/member/login")
    public ResponseEntity<Boolean> memberLogin(@Valid @RequestBody MemberLoginRequestDto loginDto) {
        boolean loginResponse = memberService.login(loginDto.getUserName(), loginDto.getPassword());
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(loginResponse);
    }

    /**
     * 비회원 주문확인을 위한
     *
     * @param loginDto 로그인시 기입하는 로그인정보가 입력
     * @return 로그인이 가능한지 여부를 boolean 으로 반환
     */
    //주문에서 customer정보 받아서 확인하는 비회원로그인
    @PostMapping("/customer/login")
    public ResponseEntity<Boolean> customerLogin(@Valid @RequestBody CustomerLoginRequestDto loginDto)
}
