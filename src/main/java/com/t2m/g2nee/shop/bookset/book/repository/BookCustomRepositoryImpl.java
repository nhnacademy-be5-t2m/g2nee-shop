package com.t2m.g2nee.shop.bookset.book.repository;

import com.querydsl.core.types.Projections;
import com.t2m.g2nee.shop.bookset.book.domain.Book;
import com.t2m.g2nee.shop.bookset.book.domain.QBook;
import com.t2m.g2nee.shop.bookset.book.dto.BookDto;
import com.t2m.g2nee.shop.bookset.bookcategory.domain.QBookCategory;
import com.t2m.g2nee.shop.bookset.bookcontributor.domain.BookContributor;
import com.t2m.g2nee.shop.bookset.bookcontributor.domain.QBookContributor;
import com.t2m.g2nee.shop.bookset.bookcontributor.dto.BookContributorDto;
import com.t2m.g2nee.shop.bookset.bookcontributor.mapper.BookContributorMapper;
import com.t2m.g2nee.shop.bookset.categoryPath.domain.QCategoryPath;
import com.t2m.g2nee.shop.bookset.publisher.domain.QPublisher;
import com.t2m.g2nee.shop.fileset.bookfile.domain.BookFile;
import com.t2m.g2nee.shop.fileset.bookfile.domain.QBookFile;
import com.t2m.g2nee.shop.fileset.file.domain.File;
import com.t2m.g2nee.shop.fileset.file.domain.QFile;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;



public class BookCustomRepositoryImpl extends QuerydslRepositorySupport implements BookCustomRepository {

    private final BookContributorMapper bookContributorMapper;

    public BookCustomRepositoryImpl(BookContributorMapper bookContributorMapper) {
        super(Book.class);
        this.bookContributorMapper = bookContributorMapper;
    }

    /**
     * 가장 최근에 출판된 책 8권을 조회하는 메서드입니다.
     *
     * @return List<BookDto.Response>
     */
    @Override
    public List<BookDto.ListResponse> getNewBookList() {

        QBook book = QBook.book;
        QPublisher publisher = QPublisher.publisher;
        QBookContributor bookContributor = QBookContributor.bookContributor;
        QBookFile bookFile = QBookFile.bookFile;


        List<BookDto.ListResponse> responseList =
                from(book)
                        .innerJoin(publisher).on(book.publisher.publisherId.eq(publisher.publisherId))
                        .innerJoin(bookFile).on(book.bookId.eq(bookFile.book.bookId))
                        .where(bookFile.imageType.eq(BookFile.ImageType.THUMBNAIL))
                        .select(Projections.fields(BookDto.ListResponse.class
                                , book.bookId
                                , bookFile.url.as("thumbnailImageUrl")
                                , book.title
                                , book.engTitle
                                , book.publishedDate
                                , book.price
                                , book.salePrice
                                , publisher.publisherName
                                , publisher.publisherName
                        ))
                        .orderBy(book.publishedDate.desc())
                        .limit(8)
                        .fetch();


        return setContributorsAndRoles(responseList, bookContributor);
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
        QBookFile bookFile = QBookFile.bookFile;

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


        List<BookDto.ListResponse> responseList =
                from(book)
                        .innerJoin(publisher).on(book.publisher.publisherId.eq(publisher.publisherId))
                        .innerJoin(bookCategory).on(book.bookId.eq(bookCategory.book.bookId))
                        .innerJoin(bookFile).on(book.bookId.eq(bookFile.book.bookId))
                        .where(bookCategory.category.categoryId.in(categoryList)
                                .and(bookFile.imageType.eq(BookFile.ImageType.THUMBNAIL)))
                        .select(Projections.fields(BookDto.ListResponse.class
                                        , book.bookId
                                        , bookFile.url.as("thumbnailImageUrl")
                                        , book.title
                                        , book.engTitle
                                        , book.publishedDate
                                        , book.price
                                        , book.salePrice
                                        , publisher.publisherName
                                        , publisher.publisherName))
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


        List<BookDto.ListResponse> responses = setContributorsAndRoles(responseList, bookContributor);

        return new PageImpl<>(responses, pageable, count);
    }

    public BookDto.Response getBookDetail(Long bookId){

        QBook book = QBook.book;
        QPublisher publisher = QPublisher.publisher;
        QBookContributor bookContributor = QBookContributor.bookContributor;
        QCategoryPath categoryPath = QCategoryPath.categoryPath;
        QBookCategory bookCategory = QBookCategory.bookCategory;
        QBookFile bookFile = QBookFile.bookFile;

return null;

    }


    /**
     * 도서에 기여자와 역할 정보를 설정하고 반환하는 메서드입니다.
     *
     * @param responseList    책 responseDto 리스트
     * @param bookContributor 책, 기여자, 역할 관계 객체
     * @return dto response
     */
    private List<BookDto.ListResponse> setContributorsAndRoles(List<BookDto.ListResponse> responseList,
                                                               QBookContributor bookContributor) {

        for (BookDto.ListResponse book : responseList) {
            List<BookContributor> bookContributorList =
                    from(bookContributor)
                            .where(bookContributor.book.bookId.eq(book.getBookId()))
                            .fetch();
            List<BookContributorDto.Response> bookContributorResponseList =
                    bookContributorMapper.entitiesToDtos(bookContributorList);

            book.setContributorRoleList(bookContributorResponseList);
        }
        return responseList;
    }

}
