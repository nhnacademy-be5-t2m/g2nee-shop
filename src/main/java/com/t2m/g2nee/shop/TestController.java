package com.t2m.g2nee.shop;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/shop")
    public String hello(){

        return "hello g2nee-shop";
    }
}
