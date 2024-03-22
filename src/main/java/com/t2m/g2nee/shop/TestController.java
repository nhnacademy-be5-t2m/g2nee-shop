package com.t2m.g2nee.shop;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class TestController {

    @Value("${server.port}")
    private String port;

    @GetMapping
    public String getHello(){
        return port + ":hello g2nee shop";
    }
}
