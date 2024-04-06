package com.t2m.g2nee.shop.bookset.book.repository;

import com.t2m.g2nee.shop.bookset.book.domain.Book;
import com.t2m.g2nee.shop.bookset.book.domain.QBook;
import com.t2m.g2nee.shop.bookset.book.dto.BookDto;
import com.t2m.g2nee.shop.bookset.book.mapper.BookMapper;
import com.t2m.g2nee.shop.bookset.bookcategory.domain.QBookCategory;
import com.t2m.g2nee.shop.bookset.bookcontributor.domain.BookContributor;
import com.t2m.g2nee.shop.bookset.bookcontributor.domain.QBookContributor;
import com.t2m.g2nee.shop.bookset.bookcontributor.mapper.BookContributorMapper;
import com.t2m.g2nee.shop.bookset.categoryPath.domain.QCategoryPath;
import com.t2m.g2nee.shop.bookset.publisher.domain.QPublisher;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;



public class BookCustomRepositoryImpl extends QuerydslRepositorySupport implements BookCustomRepository {

    private final BookMapper bookMapper;
    private final BookContributorMapper bookContributorMapper;

    public BookCustomRepositoryImpl(BookMapper bookMapper, BookContributorMapper bookContributorMapper) {
        super(Book.class);
        this.bookMapper = bookMapper;
        this.bookContributorMapper = bookContributorMapper;
    }

    /**
     * 가장 최근에 출판된 책 10권을 조회하는 메서드입니다.
     *
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
                        .orderBy(book.publishedDate.desc())
                        .limit(8)
                        .fetch();


        return setContributorsAndRoles(bookList, bookContributor);
    }

    /**
     * 카테고리와 하위 카테고리에 해당하는 책들을 조회하는 메서드입니다.
     *
     * @param categoryId 카테고리 아이디
     * @return List<BookDto.Response>
     */
    @Override
    public Page<BookDto.ListResponse> getBookListByCategory(Long categoryId, Pageable pageable) {

        QBook book = QBook.book;
        QPublisher publisher = QPublisher.publisher;
        QBookContributor bookContributor = QBookContributor.bookContributor;
        QCategoryPath categoryPath = QCategoryPath.categoryPath;
        QBookCategory bookCategory = QBookCategory.bookCategory;

        // 검색할 카테고리의 모든 하위 카테고리 id를 함께 가져옵니다.
        // ex) 이과 / 수학 / 미분 일때 이과를 검색 시 이과, 수학, 미분 카테고리를 가진 모든 책이 조회됩니다.
        // ex) 수학만 검색 시 수학, 미분이 카테고리를 가진 책이 검색됩니다.
        List<Long> categoryList = from(categoryPath)
                .where(categoryPath.ancestor.categoryId.eq(categoryId))
                .fetch()
                .stream()
                .mapToLong(cp -> cp.getDescendant().getCategoryId())
                .boxed()
                .collect(Collectors.toList());


        List<Book> bookList =
                from(book)
                        .innerJoin(publisher).on(book.publisher.publisherId.eq(publisher.publisherId))
                        .innerJoin(bookCategory).on(book.bookId.eq(bookCategory.book.bookId))
                        .where(bookCategory.category.categoryId.in(categoryList))
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch();

        Long count =
                from(book)
                        .innerJoin(publisher).on(book.publisher.publisherId.eq(publisher.publisherId))
                        .innerJoin(bookCategory).on(book.bookId.eq(bookCategory.book.bookId))
                        .where(bookCategory.category.categoryId.in(categoryList))
                        .select(book.count())
                        .fetchOne();


        List<BookDto.ListResponse> responses = setContributorsAndRoles(bookList, bookContributor);

        return new PageImpl<>(responses, pageable, count);
    }

    /**
     * 도서에 기여자와 역할 list를 설정하고 dto를 반환해주는 메서드입니다.
     *
     * @param bookList        책 리스트
     * @param bookContributor 책, 기여자, 역할 관계 객체
     * @return dto response
     */
    private List<BookDto.ListResponse> setContributorsAndRoles(List<Book> bookList, QBookContributor bookContributor) {

        List<BookDto.ListResponse> responses = new ArrayList<>();
        for (Book b : bookList) {
            List<BookContributor> bookContributorList =
                    from(bookContributor)
                            .where(bookContributor.book.bookId.eq(b.getBookId()))
                            .fetch();

            BookDto.ListResponse response = bookMapper.entityToListDto(b, bookContributorList);
            responses.add(response);
        }
        return responses;
    }

}
