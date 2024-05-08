package com.t2m.g2nee.shop.point.controller;

import com.t2m.g2nee.shop.point.dto.request.PointCreateRequestDto;
import com.t2m.g2nee.shop.point.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/shop/point")
@RequiredArgsConstructor
public class PointController {
    private final PointService pointService;

    @PostMapping("/create")
    public void createPoint(@RequestBody PointCreateRequestDto request) {
        pointService.savePoint(request);
    }

    @GetMapping("/totalAmount/{memberId}")
    public ResponseEntity<Integer> getTotalPoint(@PathVariable("memberId") Long memberId) {
        Integer totalPoint = pointService.getTotalPoint(memberId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(totalPoint);
    }
}
