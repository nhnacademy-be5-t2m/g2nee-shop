package com.t2m.g2nee.shop;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shop")
public class TestController {

    HttpHeaders header = new HttpHeaders();

    public TestController() {
        header.setContentType(new MediaType("application", "json"));
    }

    @Value("${server.port}")
    private String port;

    @GetMapping("/hello")
    public ResponseEntity<String> getHello() {
        String result = "hello g2nee shop : " + port;
        return new ResponseEntity<>(result, header, HttpStatus.OK);
    }
}
