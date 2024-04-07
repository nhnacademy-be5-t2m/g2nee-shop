package com.t2m.g2nee.shop;

import com.t2m.g2nee.shop.bookset.book.repository.BookCustomRepositoryImpl;
import com.t2m.g2nee.shop.bookset.book.repository.BookRepository;
import com.t2m.g2nee.shop.fileset.bookfile.domain.BookFile;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
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

    private final BookRepository bookRepository;
    HttpHeaders header = new HttpHeaders();

    public TestController(BookRepository bookRepository1) {
        this.bookRepository = bookRepository1;

        header.setContentType(new MediaType("application", "json"));
    }

    @Value("${server.port}")
    private String port;

    @GetMapping("/hello")
    public ResponseEntity<String> getHello() {
        String result = "hello g2nee shop : " + port;
        return new ResponseEntity<>(result, header, HttpStatus.OK);
    }

    @GetMapping("/file")
    public ResponseEntity fileTest(){

        List<BookFile> bookFile = bookRepository.getBookFile();

        return ResponseEntity.status(HttpStatus.OK).body(bookFile);
    }
}
