package com.t2m.g2nee.shop;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shop")
public class TestController {

    @Value("${server.port}")
    private String port;

    @GetMapping("/hello")
    public String getHello(){
        return "hello g2nee shop : "+port;
    }
}
