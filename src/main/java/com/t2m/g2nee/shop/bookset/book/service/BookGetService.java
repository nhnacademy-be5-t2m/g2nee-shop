package com.t2m.g2nee.shop.bookset.book.service;


import com.t2m.g2nee.shop.bookset.book.dto.BookDto;
import com.t2m.g2nee.shop.bookset.book.repository.BookRepository;
import com.t2m.g2nee.shop.bookset.tag.domain.Tag;
import com.t2m.g2nee.shop.bookset.tag.dto.TagDto;
import com.t2m.g2nee.shop.pageUtils.PageResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 책 조회에 대한 Service 입니다.
 *
 * @author : 신동민
 * @since : 1.0
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookGetService {

    private final BookRepository bookRepository;

    /**
     * 가장 최신 출판된 10개의 책을 조회하는 메서드 입니다.
     * @return List<BookDto.ListResponse>
     */
    public List<BookDto.ListResponse> getNewBooks(){

        return bookRepository.getNewBookList();
    }

    /**
     * 카테고리와 하위 카테고리의 책을 모두 조회하는 메서드 입니다.
     * @param page 페이지 번호
     * @param categoryId 카테고리 아이디
     * @return PageResponse<BookDto.ListResponse>
     */

    public PageResponse<BookDto.ListResponse> getBooksByCategory(int page, Long categoryId){

        int size = 8;
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("viewCount"));

        Page<BookDto.ListResponse> bookPage = bookRepository.getBookListByCategory(categoryId,pageable);

        int maxPageButtons = 8;
        int startPage = (int) Math.max(1, bookPage.getNumber() - Math.floor((double) maxPageButtons / 2));
        int endPage = Math.min(startPage + maxPageButtons - 1, bookPage.getTotalPages());

        if (endPage - startPage + 1 < maxPageButtons) {
            startPage = Math.max(1, endPage - maxPageButtons + 1);
        }


        return PageResponse.<BookDto.ListResponse>builder()
                .data(bookPage.getContent())
                .currentPage(page)
                .startPage(startPage)
                .endPage(endPage)
                .totalPage(bookPage.getTotalPages())
                .totalElements(bookPage.getTotalElements())
                .build();
    }
}
