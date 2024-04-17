package com.t2m.g2nee.shop.bookset.book.service;


import com.t2m.g2nee.shop.bookset.book.dto.BookDto;
import com.t2m.g2nee.shop.bookset.book.repository.BookRepository;
import com.t2m.g2nee.shop.pageUtils.PageResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
     * 가장 최신 출판된 6개의 책을 조회하는 메서드 입니다.
     *
     * @return List<BookDto.ListResponse>
     */
    public List<BookDto.ListResponse> getNewBooks() {

        return bookRepository.getNewBookList();
    }

    /**
     * 모든 도서 목록을 조회하는 메서드 입니다.
     *
     * @return List<BookDto.ListResponse>
     */
    public PageResponse<BookDto.ListResponse> getAllBook(int page) {

        int size = 8;
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("title"));

        Page<BookDto.ListResponse> bookPage = bookRepository.getAllBook(pageable);
        return getPageResponse(page, bookPage);
    }

    /**
     * 카테고리와 하위 카테고리의 책을 모두 조회하고 sort에 따라 정렬하는 메서드 입니다.
     *
     * @param page       페이지 번호
     * @param categoryId 카테고리 아이디
     * @param sort       정렬 기준
     * @return PageResponse<BookDto.ListResponse>
     */

    public PageResponse<BookDto.ListResponse> getBooksByCategory(int page, Long categoryId, String sort) {

        // 조건에 맞게 정렬하여 페이지 생성
        int size = 8;
        Pageable pageable = PageRequest.of(page-1, size);

        Page<BookDto.ListResponse> bookPage = bookRepository.getBookListByCategory(categoryId, pageable, sort);

        return getPageResponse(page, bookPage);
    }


    /**
     * 책 상세 정보를 조회하는 메서드입니다.
     *
     * @param bookId 책 아이디
     * @return BookDto.Response
     */
    @Transactional
    public BookDto.Response getBookDetail(Long bookId) {
        return bookRepository.getBookDetail(bookId);
    }

    /**
     * Elasticsearch를 이용해서 책 목록을 조회하는 메서드입니다.
     *
     * @param page       페이지 번호
     * @param categoryId 카테고리 아이디
     * @param keyword    키워드
     * @param sort       정렬 기준 default viewCount
     * @return PageResponse<BookDto.ListResponse>
     */
    public PageResponse<BookDto.ListResponse> getBookByCategoryAndElasticsearch(int page, Long categoryId,
                                                                                String keyword, String sort) {
        // 조건에 맞게 정렬하여 페이지 생성
        int size = 8;
        Pageable pageable = PageRequest.of(page-1, size);
        Page<BookDto.ListResponse> bookPage;

        switch (sort) {
            case "salesVolume":
                // 정렬한 pageResponse 반환
                break;
            case "score":
                // 정렬한 pageResponse 반환
                break;
            case "review":
                // 정렬한 pageResponse 반환
                break;
            default:

                // categoryId가 null 일 경우 repository에서 키워드만 받도록 처리
                List<BookDto.ListResponse> responseList =
                        bookRepository.getBooksByElasticSearchAndCategory(categoryId, keyword, sort);

                int start = (int) pageable.getOffset();
                int end = Math.min((start + pageable.getPageSize()), responseList.size());

                List<BookDto.ListResponse> sortedList = responseList.subList(start, end);
                bookPage = new PageImpl<>(sortedList, pageable, responseList.size());
                return getPageResponse(page, bookPage);
        }

        return null;
    }

    /**
     * Paging하여 응답객체를 생성하는 메서드 입니다.
     *
     * @param page     페이지 번호
     * @param bookPage 페이징할 페이지 객체
     * @return PageResponse<BookDto.ListResponse>
     */
    private PageResponse<BookDto.ListResponse> getPageResponse(int page,
                                                               Page<BookDto.ListResponse> bookPage) {

        // 최대 버튼 개수 8개
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
