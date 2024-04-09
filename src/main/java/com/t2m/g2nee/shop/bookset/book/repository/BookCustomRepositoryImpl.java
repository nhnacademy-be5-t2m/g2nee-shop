package com.t2m.g2nee.shop.bookset.book.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.t2m.g2nee.shop.bookset.book.domain.Book;
import com.t2m.g2nee.shop.bookset.book.domain.QBook;
import com.t2m.g2nee.shop.bookset.book.dto.BookDto;
import com.t2m.g2nee.shop.bookset.bookcategory.domain.QBookCategory;
import com.t2m.g2nee.shop.bookset.bookcontributor.domain.BookContributor;
import com.t2m.g2nee.shop.bookset.bookcontributor.domain.QBookContributor;
import com.t2m.g2nee.shop.bookset.bookcontributor.dto.BookContributorDto;
import com.t2m.g2nee.shop.bookset.bookcontributor.mapper.BookContributorMapper;
import com.t2m.g2nee.shop.bookset.booktag.domain.QBookTag;
import com.t2m.g2nee.shop.bookset.category.domain.QCategory;
import com.t2m.g2nee.shop.bookset.category.dto.response.CategoryInfoDto;
import com.t2m.g2nee.shop.bookset.categoryPath.domain.QCategoryPath;
import com.t2m.g2nee.shop.bookset.publisher.domain.QPublisher;
import com.t2m.g2nee.shop.bookset.tag.domain.QTag;
import com.t2m.g2nee.shop.bookset.tag.dto.TagDto;
import com.t2m.g2nee.shop.elasticsearch.BooksIndex;
import com.t2m.g2nee.shop.fileset.bookfile.domain.BookFile;
import com.t2m.g2nee.shop.fileset.bookfile.domain.QBookFile;
import com.t2m.g2nee.shop.fileset.file.domain.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;


public class BookCustomRepositoryImpl extends QuerydslRepositorySupport implements BookCustomRepository {

    private final BookContributorMapper bookContributorMapper;
    private final ElasticsearchOperations operations;

    public BookCustomRepositoryImpl(BookContributorMapper bookContributorMapper,
                                    ElasticsearchOperations operations) {
        super(Book.class);
        this.bookContributorMapper = bookContributorMapper;
        this.operations = operations;
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


        return toListResponseList(responseList, bookContributor);
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

        /*
            검색할 카테고리의 모든 하위 카테고리 id를 함께 가져옵니다.
            ex) 이과 / 수학 / 미분 일때 이과를 검색 시 이과, 수학, 미분 카테고리를 가진 모든 책이 조회됩니다.
            ex) 수학만 검색 시 수학, 미분이 카테고리를 가진 책이 검색됩니다.
         */
        List<Long> categoryIdList = from(categoryPath)
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
                        .where(bookCategory.category.categoryId.in(categoryIdList)
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
                        .where(bookCategory.category.categoryId.in(categoryIdList))
                        .select(book.count())
                        .fetchOne();


        List<BookDto.ListResponse> responses = toListResponseList(responseList, bookContributor);

        return new PageImpl<>(responses, pageable, count);
    }

    /**
     * 책 상세 정보를 조회하는 메서드 입니다.
     *
     * @param bookId 책 아이디
     * @return BookDto.Response
     */
    public BookDto.Response getBookDetail(Long bookId){

        QBook book = QBook.book;
        QPublisher publisher = QPublisher.publisher;
        QBookContributor bookContributor = QBookContributor.bookContributor;
        QCategoryPath categoryPath = QCategoryPath.categoryPath;
        QCategory category = QCategory.category;
        QBookCategory bookCategory = QBookCategory.bookCategory;
        QBookFile bookFile = QBookFile.bookFile;
        QTag tag = QTag.tag;
        QBookTag bookTag = QBookTag.bookTag;

        BookDto.Response response =
                from(book)
                        .innerJoin(publisher).on(book.publisher.publisherId.eq(publisher.publisherId))
                        .where(book.bookId.eq(bookId))
                        .select(Projections.fields(BookDto.Response.class
                                , book.bookId
                                , book.quantity
                                , book.title
                                , book.engTitle
                                , book.bookIndex
                                , book.description
                                , book.publishedDate
                                , book.price
                                , book.salePrice
                                , book.isbn
                                , book.viewCount
                                , book.bookStatus
                                , book.pages))
                        .fetchOne();


        return toResponse(response, bookContributor, bookFile, bookCategory, tag, bookTag, category, categoryPath);

    }

    /**
     * Elasticsearch를 이용해서 키워드를 통해 가중치를 부여하여 검색하고 필요에 따라 카테고리를 필터링하여 검색하는 메서드 입니다.
     *
     * @param page       페이지 번호
     * @param categoryId 카테고리 아이디
     * @param keyword    검색할 키워드
     * @return List<BookDto.ListResponse>
     */
    public List<BookDto.ListResponse> getBooksByElasticSearchAndCategory(int page, Long categoryId, String keyword) {

        /*
         Elasticsearch search 쿼리
           {
          "query": {
            "bool": {
              "should": [
                {
                  "match": {
                    "title": {
                      "query": keyword,
                      "boost": 50
                    }
                  }
                },
                ...
                }
         */
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        MatchQueryBuilder titleQuery = QueryBuilders.matchQuery("title", keyword).boost(50);
        MatchQueryBuilder bookIndexQuery = QueryBuilders.matchQuery("bookIndex", keyword).boost(20);
        MatchQueryBuilder descriptionQuery = QueryBuilders.matchQuery("description", keyword).boost(30);
        MatchQueryBuilder contributorQuery = QueryBuilders.matchQuery("categoryName",keyword).boost(40);
        MatchQueryBuilder publisherQuery = QueryBuilders.matchQuery("publisherName",keyword).boost(40);

        boolQuery.should(titleQuery);
        boolQuery.should(bookIndexQuery);
        boolQuery.should(descriptionQuery);
        boolQuery.should(contributorQuery);
        boolQuery.should(publisherQuery);

        NativeSearchQueryBuilder searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQuery);


        /*
            search 결과로 가져온 인덱스 객체들을 포함하는 hitList
         */
        List<SearchHit<BooksIndex>> hitList = operations.search(searchQuery.build(), BooksIndex.class).toList();

        QBook book = QBook.book;
        QBookFile bookFile = QBookFile.bookFile;
        QPublisher publisher = QPublisher.publisher;
        QBookContributor bookContributor = QBookContributor.bookContributor;
        QBookCategory bookCategory = QBookCategory.bookCategory;

        /*
            인덱스의 id 값을 추출
         */
        List<Long> indexIdList = hitList
                .stream()
                .map(h ->
                        h.getContent().getBookId())
                .collect(Collectors.toList());

        List<BookDto.ListResponse> responses = new ArrayList<>();
        /*
            인덱스에 해당하는 book 들을 조회
         */
        for (Long bookId : indexIdList) {

            BookDto.ListResponse response =
                    from(book)
                            .innerJoin(bookCategory).on(book.bookId.eq(bookCategory.book.bookId))
                            .innerJoin(publisher).on(book.publisher.publisherId.eq(publisher.publisherId))
                            .innerJoin(bookFile).on(book.bookId.eq(bookFile.book.bookId))
                            .where(bookFile.imageType.eq(BookFile.ImageType.THUMBNAIL)
                                    .and(eqCategoryId(bookCategory, categoryId))
                                    .and(book.bookId.eq(bookId)))
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
                            .fetchOne();

            if (response != null) {
                responses.add(response);
            }

        }


        return toListResponseList(responses, bookContributor);

    }


    /**
     * 도서에 기여자와 역할 정보를 설정하고 반환하는 메서드입니다.
     *
     * @param responseList    책 responseDto 리스트
     * @param bookContributor 책, 기여자, 역할 관계 객체
     * @return dto response
     */
    private List<BookDto.ListResponse> toListResponseList(List<BookDto.ListResponse> responseList,
                                                               QBookContributor bookContributor) {

        if (responseList.isEmpty()) {
            return responseList;
        }

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

    /**
     * 도서 응답에 필요한 객체를 설정하고 반환하는 메서드입니다.
     *
     * @param bookResponse    책 responseDto
     * @param bookContributor 책, 기여자, 역할 관계 객체
     * @param bookFile        이미지 관계 객체
     * @param bookCategory    카테고리 관계 객체
     * @param tag             태그 관계 객체
     * @param bookTag         책태그 관계 객체
     * @param categoryPath    카테고리들의 관계 객체
     * @return dto response
     */
    private BookDto.Response toResponse(BookDto.Response bookResponse,
                                        QBookContributor bookContributor,
                                        QBookFile bookFile,
                                        QBookCategory bookCategory,
                                        QTag tag,
                                        QBookTag bookTag,
                                        QCategory category,
                                        QCategoryPath categoryPath) {

        // 기여자 역할 정보
        List<BookContributor> bookContributorList =
                from(bookContributor)
                        .where(bookContributor.book.bookId.eq(bookResponse.getBookId()))
                        .fetch();
        List<BookContributorDto.Response> bookContributorResponseList =
                bookContributorMapper.entitiesToDtos(bookContributorList);

        // 상세이미지 정보
        List<BookFile> bookFileList =
                from(bookFile)
                        .where(bookFile.book.bookId.eq(bookResponse.getBookId()))
                        .fetch();
        List<String> imageUrlList =
                bookFileList.stream()
                        .map(File::getUrl)
                        .collect(Collectors.toList());

       /*
        책에 연관된 카테고리 ID를 가져옵니다
        책의 카테고리는 최하위 카테고리를 가집니다.
        */
        List<Long> categoryIdList =
                from(bookCategory)
                        .innerJoin(category)
                        .on(bookCategory.category.categoryId.eq(category.categoryId))
                        .where(bookCategory.book.bookId.eq(bookResponse.getBookId()))
                        .select(bookCategory.category.categoryId)
                        .fetch();

        List<List<CategoryInfoDto>> categoriesList = new ArrayList<>();

         /* 연관된 카테고리 ID에 해당하는 이름을 넣어줍니다.
         이과 / 수학 / 미분 에서 최하위 카테고리인 미분 ID를 가지고 있을 것이니 미분의 모든 상위 카테고리 이름을 가져옵니다.
         만약 카테고리가 2개라면 [이과, 수학, 미분] , [문과, 국어, 작문] 두 개의 리스트를 리스트에 넣어줍니다.
          */
        for (Long categoryId : categoryIdList) {
            List<CategoryInfoDto> categories =
                    from(category)
                            .innerJoin(categoryPath).on(categoryPath.ancestor.categoryId.eq(category.categoryId))
                            .where(categoryPath.descendant.categoryId.eq(categoryId))
                            .orderBy(categoryPath.depth.desc())
                            .select(Projections.fields(CategoryInfoDto.class
                                    , category.categoryId
                                    , category.categoryName
                                    , category.categoryEngName))
                            .fetch();
            categoriesList.add(categories);
        }

        /*
          태그 정보
         */
        List<TagDto.Response> tagList =
                from(tag)
                        .innerJoin(bookTag).on(bookTag.tag.tagId.eq(tag.tagId))
                        .where(bookTag.book.bookId.eq(bookResponse.getBookId()))
                        .select(Projections.fields(TagDto.Response.class
                                , tag.tagId
                                , tag.tagName
                        ))
                        .fetch();


        bookResponse.setContributorRoleList(bookContributorResponseList);
        bookResponse.setDetailImageUrl(imageUrlList);
        bookResponse.setCategoryList(categoriesList);
        bookResponse.setTagList(tagList);

        return bookResponse;
    }

    /**
     * 카테고리 ID가 요청으로 왔을 때 필터링을 위한 메서드
     * @param bookCategory bookCategory 객체
     * @param categoryId   카테고리 아이디
     * @return BooleanExpression
     */
    private BooleanExpression eqCategoryId(QBookCategory bookCategory, Long categoryId) {
        if (categoryId == null) {
            return null;
        } else {
            return bookCategory.category.categoryId.eq(categoryId);
        }
    }
}
