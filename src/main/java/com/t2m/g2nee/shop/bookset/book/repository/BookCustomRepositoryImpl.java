package com.t2m.g2nee.shop.bookset.book.repository;

import com.t2m.g2nee.shop.bookset.book.domain.Book;
import com.t2m.g2nee.shop.bookset.book.domain.QBook;
import com.t2m.g2nee.shop.bookset.book.dto.BookDto;
import com.t2m.g2nee.shop.bookset.book.mapper.BookMapper;
import com.t2m.g2nee.shop.bookset.bookcontributor.domain.BookContributor;
import com.t2m.g2nee.shop.bookset.bookcontributor.domain.QBookContributor;
import com.t2m.g2nee.shop.bookset.publisher.domain.QPublisher;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;


public class BookCustomRepositoryImpl extends QuerydslRepositorySupport implements BookCustomRepository {

    private final BookMapper mapper;

    public BookCustomRepositoryImpl(BookMapper mapper) {
        super(Book.class);
        this.mapper = mapper;
    }

    /**
     * 가장 최근에 출판된 책 10권을 조회하는 메서드입니다.
     * @return List<BookDto.Response>
     */
    @Override
    public List<BookDto.ListResponse> getNewBookList() {

        QBook book = QBook.book;
        QPublisher publisher = QPublisher.publisher;
        QBookContributor bookContributor = QBookContributor.bookContributor;


        List<Book> bookList =
                from(book)
                        .innerJoin(publisher).on(book.publisher.publisherId.eq(publisher.publisherId))
                        .orderBy(book.publishedDate.asc())
                        .limit(10)
                        .fetch();

        List<BookDto.ListResponse> responses = new ArrayList<>();

        // 모든 책에 기여자, 역할 정보를 각각 넣어줍니다.
        for (Book b : bookList) {
            List<BookContributor> bookContributorList =
                    from(bookContributor)
                            .where(bookContributor.book.bookId.eq(b.getBookId()))
                            .fetch();

            BookDto.ListResponse response = mapper.entityToListDto(b,bookContributorList);
            responses.add(response);
        }
        return responses;
    }
}
