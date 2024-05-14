package com.t2m.g2nee.shop.bookset.book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureRestDocs
@WebMvcTest(BookControllerTest.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

}
