package com.t2m.g2nee.shop.memberset.Member.controller;

import com.t2m.g2nee.shop.memberset.Member.dto.request.SignUpMemberRequestDto;
import com.t2m.g2nee.shop.memberset.Member.dto.request.SignUpOAuthMemberRequestDto;
import com.t2m.g2nee.shop.memberset.Member.dto.response.MemberResponse;
import com.t2m.g2nee.shop.memberset.Member.service.Impl.MemberServiceImpl;
import com.t2m.g2nee.shop.memberset.Member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 회원 정보를 다루는 컨트롤러 입니다.
 *
 * @author : 정지은
 * @since : 1.0
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/member")
public class MemberController {
    private MemberServiceImpl memberService;

    /**
     * 회원가입을 처리하는 메소드
     *
     * @param signUpDto 회원가입시 기입하는 회원정보가 입력.
     * @return 회원정보 저장 후 기본 회원정보 response 반환
     */
    @PutMapping("/signup")
    public ResponseEntity<MemberResponse> memberSignUp(@Valid @RequestBody SignUpMemberRequestDto signUpDto){
        MemberResponse memberResponse = memberService.signUp(signUpDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(memberResponse);
    }

}
