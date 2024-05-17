package com.t2m.g2nee.shop.bookset.book.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.t2m.g2nee.shop.bookset.book.domain.Book;
import com.t2m.g2nee.shop.bookset.book.domain.QBook;
import com.t2m.g2nee.shop.bookset.book.dto.BookDto;
import com.t2m.g2nee.shop.bookset.bookcategory.domain.QBookCategory;
import com.t2m.g2nee.shop.bookset.bookcontributor.domain.QBookContributor;
import com.t2m.g2nee.shop.bookset.bookcontributor.dto.BookContributorDto;
import com.t2m.g2nee.shop.bookset.booktag.domain.QBookTag;
import com.t2m.g2nee.shop.bookset.category.domain.QCategory;
import com.t2m.g2nee.shop.bookset.category.dto.response.CategoryInfoDto;
import com.t2m.g2nee.shop.bookset.categorypath.domain.QCategoryPath;
import com.t2m.g2nee.shop.bookset.contributor.domain.QContributor;
import com.t2m.g2nee.shop.bookset.publisher.domain.QPublisher;
import com.t2m.g2nee.shop.bookset.role.domain.QRole;
import com.t2m.g2nee.shop.bookset.tag.domain.QTag;
import com.t2m.g2nee.shop.bookset.tag.dto.TagDto;
import com.t2m.g2nee.shop.elasticsearch.BooksIndex;
import com.t2m.g2nee.shop.fileset.bookfile.domain.BookFile;
import com.t2m.g2nee.shop.fileset.bookfile.domain.QBookFile;
import com.t2m.g2nee.shop.fileset.file.domain.QFile;
import com.t2m.g2nee.shop.like.domain.QBookLike;
import com.t2m.g2nee.shop.orderset.orderdetail.domain.QOrderDetail;
import com.t2m.g2nee.shop.review.domain.QReview;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.elasticsearch.index.query.AbstractQueryBuilder;
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

    private final ElasticsearchOperations operations;

    QBook book = QBook.book;
    QPublisher publisher = QPublisher.publisher;
    QBookContributor bookContributor = QBookContributor.bookContributor;
    QCategoryPath categoryPath = QCategoryPath.categoryPath;
    QCategory category = QCategory.category;
    QBookCategory bookCategory = QBookCategory.bookCategory;
    QContributor contributor = QContributor.contributor;
    QRole role = QRole.role;
    QBookFile bookFile = QBookFile.bookFile;
    QFile file = QFile.file;
    QTag tag = QTag.tag;
    QBookTag bookTag = QBookTag.bookTag;
    QReview review = QReview.review;
    QBookLike bookLike = QBookLike.bookLike;
    QOrderDetail orderDetail = QOrderDetail.orderDetail;

    public BookCustomRepositoryImpl(ElasticsearchOperations operations) {
        super(Book.class);
        this.operations = operations;
    }

    /**
     * 가장 최근에 출판된 책 8권을 조회하는 메서드입니다.
     *
     * @return List<BookDto.Response>
     */
    @Override
    public List<BookDto.ListResponse> getNewBookList() {


        return from(book)
                .innerJoin(publisher).on(book.publisher.publisherId.eq(publisher.publisherId))
                .innerJoin(bookFile).on(book.bookId.eq(bookFile.book.bookId))
                .where(bookFile.imageType.eq(BookFile.ImageType.THUMBNAIL))
                .select(Projections.fields(BookDto.ListResponse.class
                        , book.bookId
                        , bookFile.url.as("thumbnailImageUrl")
                        , book.title
                        , book.engTitle
                        , book.publishedDate
                        , book.quantity
                        , book.price, book.salePrice, book.bookStatus
                        , publisher.publisherName
                )).orderBy(book.publishedDate.desc()).limit(6)
                .fetch();
    }

    /**
     * 모든 책들을 조회하는 메서드 입니다.
     *
     * @param pageable 페이징 설정
     * @return Page<BookDto.ListResponse>
     */
    @Override
    public Page<BookDto.ListResponse> getAllBook(Pageable pageable) {

        List<BookDto.ListResponse> responseList =
                from(book).innerJoin(publisher).on(book.publisher.publisherId.eq(publisher.publisherId))
                        .innerJoin(bookFile).on(book.bookId.eq(bookFile.book.bookId)
                                .and(bookFile.imageType.eq(BookFile.ImageType.THUMBNAIL)))
                        .select(Projections.fields(BookDto.ListResponse.class
                                , book.bookId
                                , bookFile.url.as("thumbnailImageUrl")
                                , book.title
                                , book.engTitle
                                , book.publishedDate
                                , book.price
                                , book.salePrice
                                , book.bookStatus
                                , publisher.publisherName,
                                publisher.publisherEngName)).offset(pageable.getOffset()).limit(pageable.getPageSize())
                        .fetch();

        Long count = from(book).select(book.count()).fetchOne();


        List<BookDto.ListResponse> responses = toListResponseList(responseList);

        return new PageImpl<>(responses, pageable, count);

    }

    /**
     * 카테고리와 하위 카테고리에 해당하는 책들을 조회하는 메서드입니다.
     *
     * @param pageable   페이징 설정
     * @param categoryId 카테고리 아이디
     * @return List<BookDto.Response>
     */
    @Override
    public Page<BookDto.ListResponse> getBookListByCategory(Long memberId, Long categoryId, Pageable pageable,
                                                            String sort) {


        // 정렬 조건

        OrderSpecifier<?> orderSpecifier = sorting(book, review, sort);

        // 요청으로 들어온 회원아이디와 조인한 테이블의 memberId가 같은 튜플은 isLiked를 true로 설정

        BooleanExpression isLiked;
        if (memberId == null) {
            isLiked = Expressions.asBoolean(false);
        } else {
            isLiked = new CaseBuilder().when(bookLike.member.customerId.eq(memberId)).then(true)
                    .otherwise(false);
        }
        // 리뷰가 없어서 평점이 null이면 0 아니면 평점으로 설정
        NumberExpression<Double>
                score = new CaseBuilder().when(review.score.avg().isNull()).then(0D).otherwise(review.score.avg());

          /*
            검색할 카테고리의 모든 하위 카테고리 id를 함께 가져옵니다.
            ex) 이과 / 수학, 과학 / 미분, 적분 일때 이과를 검색 시 이과, 수학, 과학, 미분, 적분 카테고리를 가진 모든 책이 조회됩니다.
            ex) 수학만 검색 시 수학, 미분 , 적분이 카테고리를 가진 책이 검색됩니다.
         */
        List<BookDto.ListResponse> responseList;

        if(orderSpecifier != null) {

            responseList =
                    from(book)
                            .innerJoin(publisher).on(book.publisher.publisherId.eq(publisher.publisherId))
                            .innerJoin(bookFile).on(book.bookId.eq(bookFile.book.bookId))
                            .leftJoin(review).on(book.bookId.eq(review.book.bookId))
                            .leftJoin(bookLike).on(book.bookId.eq(bookLike.book.bookId))
                            .where(bookFile.imageType.eq(BookFile.ImageType.THUMBNAIL)
                                    .and(eqCategoryBookId(categoryId)
                                    ))
                            .select(Projections.fields(BookDto.ListResponse.class
                                    , book.bookId
                                    , bookFile.url.as("thumbnailImageUrl")
                                    , book.title
                                    , book.engTitle
                                    , book.publishedDate
                                    , book.viewCount
                                    , book.price
                                    , book.salePrice
                                    , book.bookStatus
                                    , book.quantity
                                    , publisher.publisherName
                                    , publisher.publisherEngName
                                    , isLiked.as("isLiked")
                                    , review.count().as("reviewCount")
                                    , score.as("scoreAverage")))
                            .groupBy(book, bookFile, publisher, bookLike, bookFile.url)
                            // 정렬 조건에 따라 리스트 반환
                            .orderBy(orderSpecifier)
                            .offset(pageable.getOffset())
                            .limit(pageable.getPageSize())
                            .fetch().stream()
                            .map(b -> {
                                double roundedScore = Math.round(b.getScoreAverage() * 10) / 10.0;
                                b.setScoreAverage(roundedScore);
                                return b;
                            })
                            .collect(Collectors.toList());
        } else {
            responseList =
                    from(book)
                            .innerJoin(publisher).on(book.publisher.publisherId.eq(publisher.publisherId))
                            .innerJoin(bookFile).on(book.bookId.eq(bookFile.book.bookId))
                            .leftJoin(review).on(book.bookId.eq(review.book.bookId))
                            .leftJoin(bookLike).on(book.bookId.eq(bookLike.book.bookId))
                            .where(bookFile.imageType.eq(BookFile.ImageType.THUMBNAIL)
                                    .and(eqCategoryBookId(categoryId)
                                    ))
                            .select(Projections.fields(BookDto.ListResponse.class
                                    , book.bookId
                                    , bookFile.url.as("thumbnailImageUrl")
                                    , book.title
                                    , book.engTitle
                                    , book.publishedDate
                                    , book.viewCount
                                    , book.price
                                    , book.salePrice
                                    , book.bookStatus
                                    , book.quantity
                                    , publisher.publisherName
                                    , publisher.publisherEngName
                                    , isLiked.as("isLiked")
                                    , review.count().as("reviewCount")
                                    , score.as("scoreAverage")))
                            .groupBy(book, bookFile, publisher, bookLike, bookFile.url)
                            // 정렬 조건에 따라 리스트 반환
                            .offset(pageable.getOffset())
                            .limit(pageable.getPageSize())
                            .fetch().stream()
                            .map(b -> {
                                double roundedScore = Math.round(b.getScoreAverage() * 10) / 10.0;
                                b.setScoreAverage(roundedScore);
                                return b;
                            })
                            .collect(Collectors.toList());
        }

        List<BookDto.ListResponse> responses = toListResponseList(responseList);

        return new PageImpl<>(responses, pageable, responses.size());
    }

    /**
     * 책 상세 정보를 조회하는 메서드 입니다.
     *
     * @param memberId 회원 아이디
     * @param bookId   책 아이디
     * @return BookDto.Response
     */
    public BookDto.Response getBookDetail(Long memberId, Long bookId) {


        // 요청으로 들어온 회원아이디와 조인한 테이블의 memberId가 같은 튜플은 isLiked를 true로 설정
        BooleanExpression isLiked;
        if (memberId == null) {
            isLiked = Expressions.asBoolean(false);
        } else {
            isLiked = new CaseBuilder().when(bookLike.member.customerId.eq(memberId)).then(true)
                    .otherwise(false);
        }
        // 리뷰가 없어서 평점이 null이면 0 아니면 평점으로 설정
        NumberExpression<Double>
                score = new CaseBuilder().when(review.score.avg().isNull()).then(0D).otherwise(review.score.avg());

        BookDto.Response bookResponse =
                from(book)
                        .innerJoin(publisher).on(book.publisher.publisherId.eq(publisher.publisherId))
                        .leftJoin(review).on(book.bookId.eq(review.book.bookId))
                        .leftJoin(bookLike).on(book.bookId.eq(bookLike.book.bookId))
                        .where(book.bookId.eq(bookId))
                        .select(Projections.fields(BookDto.Response.class
                                , book.bookId
                                , book.title
                                , book.engTitle
                                , book.viewCount
                                , book.bookIndex, book.description, publisher.publisherName, publisher.publisherEngName
                                , book.publishedDate
                                , book.price
                                , book.salePrice
                                , book.isbn
                                , book.viewCount
                                , book.bookStatus
                                , book.pages
                                , book.quantity
                                , review.count().as("reviewCount")
                                , isLiked.as("isLiked")
                                , score.as("scoreAverage")
                        ))
                        .groupBy(book, bookLike, publisher)
                        .fetchOne();
        // 조회수 증가
        addViewCount(book, bookId);


        return toResponse(bookId, bookResponse);


    }

    /**
     * Elasticsearch를 이용해서 키워드를 통해 가중치를 부여하여 검색하고 필요에 따라 카테고리를 필터링하여 검색하는 메서드 입니다.
     *
     * @param categoryId 카테고리 아이디
     * @param keyword    검색할 키워드
     * @return List<BookDto.ListResponse>
     */
    public Page<BookDto.ListResponse> getBooksByElasticSearchAndCategory(Long memberId, Long categoryId, String keyword,
                                                                         Pageable pageable, String sort,
                                                                         String condition) {

        /*
         Elasticsearch search 쿼리
         */

        // 통합, 출판사, 태그, 참여자 검색 쿼리 생성
        AbstractQueryBuilder<?> query = searchCondition(keyword, condition);

        NativeSearchQueryBuilder searchQuery = new NativeSearchQueryBuilder()
                .withQuery(query);
        /*
            search 결과로 가져온 인덱스 객체들을 포함하는 hitList
         */
        List<SearchHit<BooksIndex>> hitList = operations.search(searchQuery.build(), BooksIndex.class).toList();

        /*
            인덱스의 id 값을 추출
         */
        List<Long> indexIdList = hitList
                .stream()
                .map(h ->
                        h.getContent().getBookId())
                .collect(Collectors.toList());

        /*
            인덱스 id에 해당하는 book 들을 조회
         */

        // 정렬 조건
        OrderSpecifier<?> orderSpecifier = sorting(book, review, sort);

        // 요청으로 들어온 회원아이디와 조인한 테이블의 memberId가 같은 튜플은 isLiked를 true로 설정
        BooleanExpression isLiked;
        if (memberId == null) {
            isLiked = Expressions.asBoolean(false);
        } else {
            isLiked = new CaseBuilder().when(bookLike.member.customerId.eq(memberId)).then(true)
                    .otherwise(false);
        }
        // 리뷰가 없어서 평점이 null이면 0 아니면 평점으로 설정
        NumberExpression<Double>
                score = new CaseBuilder().when(review.score.avg().isNull()).then(0D).otherwise(review.score.avg());

        List<BookDto.ListResponse> responseList;

        // 정렬 조건이 있을 때
        if(orderSpecifier != null) {

            responseList =
                    from(book)
                            .innerJoin(publisher).on(book.publisher.publisherId.eq(publisher.publisherId))
                            .innerJoin(bookFile).on(book.bookId.eq(bookFile.book.bookId))
                            .leftJoin(review).on(book.bookId.eq(review.book.bookId))
                            .leftJoin(bookLike).on(book.bookId.eq(bookLike.book.bookId))
                            .where(book.bookId.in(indexIdList)
                                    .and(eqCategoryBookId(categoryId))
                                    .and(bookFile.imageType.eq(BookFile.ImageType.THUMBNAIL)))
                            .select(Projections.fields(BookDto.ListResponse.class
                                    , book.bookId
                                    , bookFile.url.as("thumbnailImageUrl")
                                    , book.title
                                    , book.engTitle
                                    , book.publishedDate
                                    , book.viewCount
                                    , book.quantity
                                    , book.price
                                    , book.salePrice
                                    , book.bookStatus
                                    , publisher.publisherName
                                    , publisher.publisherEngName
                                    , bookLike.bookLikeId
                                    , isLiked.as("isLiked")
                                    , review.count().as("reviewCount")
                                    , score.as("scoreAverage")))
                            .groupBy(book, bookFile, publisher, bookLike, bookFile.url)
                            // 정렬 조건에 따라 리스트 반환
                            .offset(pageable.getOffset())
                            .limit(pageable.getPageSize())
                            .orderBy(orderSpecifier)
                            .fetch().stream()
                            .map(b -> {
                                double roundedScore = Math.round(b.getScoreAverage() * 10) / 10.0;
                                b.setScoreAverage(roundedScore);
                                return b;
                            })
                            .collect(Collectors.toList());
        }
        // default 정렬 조건
        else {
            responseList =
                    from(book)
                            .innerJoin(publisher).on(book.publisher.publisherId.eq(publisher.publisherId))
                            .innerJoin(bookFile).on(book.bookId.eq(bookFile.book.bookId))
                            .leftJoin(review).on(book.bookId.eq(review.book.bookId))
                            .leftJoin(bookLike).on(book.bookId.eq(bookLike.book.bookId))
                            .where(book.bookId.in(indexIdList)
                                    .and(eqCategoryBookId(categoryId))
                                    .and(bookFile.imageType.eq(BookFile.ImageType.THUMBNAIL)))
                            .select(Projections.fields(BookDto.ListResponse.class
                                    , book.bookId
                                    , bookFile.url.as("thumbnailImageUrl")
                                    , book.title
                                    , book.engTitle
                                    , book.publishedDate
                                    , book.viewCount
                                    , book.quantity
                                    , book.price
                                    , book.salePrice
                                    , book.bookStatus
                                    , publisher.publisherName
                                    , publisher.publisherEngName
                                    , bookLike.bookLikeId
                                    , isLiked.as("isLiked")
                                    , review.count().as("reviewCount")
                                    , score.as("scoreAverage")))
                            .groupBy(book, bookFile, publisher, bookLike, bookFile.url)
                            // 정렬 조건에 따라 리스트 반환
                            .offset(pageable.getOffset())
                            .limit(pageable.getPageSize())
                            .fetch().stream()
                            .map(b -> {
                                double roundedScore = Math.round(b.getScoreAverage() * 10) / 10.0;
                                b.setScoreAverage(roundedScore);
                                return b;
                            })
                            .collect(Collectors.toList());
        }

        List<BookDto.ListResponse> responses = toListResponseList(responseList);

        return new PageImpl<>(responses, pageable, responses.size());

    }

    /**
     * 같은 카테고리를 지닌 추천 책들을 15권 조회하는 메서드
     *
     * @param categoryIdList categoryIdList 카테고리 아이디들
     * @return List<BookDto.ListResponse>
     */
    @Override
    public List<BookDto.ListResponse> getRecommendBooks(List<Long> categoryIdList, Long bookId) {

        return from(book)
                .innerJoin(bookCategory).on(book.bookId.eq(bookCategory.book.bookId))
                .innerJoin(bookFile).on(book.bookId.eq(bookFile.book.bookId))
                .where(bookFile.imageType.eq(BookFile.ImageType.THUMBNAIL)
                        .and(bookCategory.book.bookId.ne(bookId))
                        .and(bookCategory.category.categoryId.in(categoryIdList)))
                .select(Projections.fields(BookDto.ListResponse.class
                        , book.bookId
                        , bookFile.url.as("thumbnailImageUrl")
                        , book.title
                        , book.quantity
                        , book.engTitle
                        , book.bookStatus
                        , book.viewCount))
                .orderBy(book.viewCount.desc())
                .limit(15)
                .fetch();
    }

    /**
     * 책 수량을 조회하는 메서드
     *
     * @param bookIdList 책 아이디 리스트
     * @return List<BookDto.ListResponse>
     */
    @Override
    public List<BookDto.ListResponse> getBooksForCheck(List<Long> bookIdList) {
        return from(book)
                .where(book.bookId.in(bookIdList))
                .select(Projections.fields(BookDto.ListResponse.class
                        , book.bookId
                        , book.title
                        , book.salePrice
                        , book.quantity))
                .fetch();
    }


    /**
     * 도서에 기여자와 역할 정보를 설정하고 반환하는 메서드입니다.
     *
     * @param responseList 책 responseDto 리스트
     * @return dto response
     */
    private List<BookDto.ListResponse> toListResponseList(List<BookDto.ListResponse> responseList) {

        // 빈 리스트면 바로 반환
        if (responseList.isEmpty()) {
            return responseList;
        }

        // 비어있지 않으면 기여자 설정 후 반환
        for (BookDto.ListResponse b : responseList) {

            List<BookContributorDto.Response> bookContributorResponseList =
                    from(bookContributor)
                            .innerJoin(contributor).on(bookContributor.contributor.contributorId.eq(
                                    contributor.contributorId))
                            .innerJoin(role).on(bookContributor.role.roleId.eq(role.roleId))
                            .where(bookContributor.book.bookId.eq(b.getBookId()))
                            .select(Projections.fields(BookContributorDto.Response.class
                                    , contributor.contributorId
                                    , contributor.contributorName
                                    , contributor.contributorEngName
                                    , role.roleId
                                    , role.roleName))
                            .fetch();

            b.setContributorRoleList(bookContributorResponseList);
        }


        return responseList;
    }

    /**
     * 도서 응답에 필요한 객체를 설정하고 반환하는 메서드입니다.
     *
     * @param bookId       책 아이디
     * @param bookResponse 책 responseDto
     * @return dto response
     */
    private BookDto.Response toResponse(Long bookId, BookDto.Response bookResponse) {


        /*
            세부 이미지를 얻습니다.
         */

        List<String> imageUrlList =
                from(bookFile)
                        .innerJoin(file).on(bookFile.fileId.eq(file.fileId))
                        .where(bookFile.book.bookId.eq(bookId)
                                .and(bookFile.imageType.eq(BookFile.ImageType.DETAIL)))
                        .select(file.url)
                        .fetch();

    /*
        기여자 역할 정보를 얻습니다.
     */

        List<BookContributorDto.Response> bookContributorList =
                from(bookContributor)
                        .innerJoin(contributor).on(bookContributor.contributor.contributorId.eq(
                                contributor.contributorId))
                        .innerJoin(role).on(bookContributor.role.roleId.eq(role.roleId))
                        .where(bookContributor.book.bookId.eq(bookId))
                        .select(Projections.fields(BookContributorDto.Response.class
                                , contributor.contributorId
                                , contributor.contributorName
                                , contributor.contributorEngName
                                , role.roleId
                                , role.roleName))
                        .fetch();

         /*
        책에 연관된 카테고리 ID를 가져옵니다
        책의 카테고리는 최하위 카테고리를 가집니다.
        */
        List<Long> categoryIdList =
                from(bookCategory)
                        .join(bookCategory.category, category)
                        .where(bookCategory.book.bookId.eq(bookId))
                        .select(bookCategory.category.categoryId)
                        .fetch();

        List<List<CategoryInfoDto>> categoriesList = new ArrayList<>();

         /* 연관된 카테고리 ID에 해당하는 이름을 넣어줍니다.
         이과 / 수학 / 미분 에서 최하위 카테고리인 미분 ID를 가지고 있을 것이니 미분의 모든 상위 카테고리 이름을 순서대로 가져옵니다.
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
            태그 객체를 얻습니다.
         */
        List<TagDto.Response> bookTagList =
                from(bookTag)
                        .innerJoin(tag).on(bookTag.tag.tagId.eq(tag.tagId))
                        .where(bookTag.book.bookId.eq(bookId))
                        .select(Projections.fields(TagDto.Response.class
                                , tag.tagId
                                , tag.tagName))
                        .fetch();

        // 반환할 객체에 모두 설정해줍니다.
        bookResponse.setContributorRoleList(bookContributorList);
        bookResponse.setCategoryList(categoriesList);
        bookResponse.setTagList(bookTagList);
        bookResponse.setDetailImageUrl(imageUrlList);

        return bookResponse;


    }

    /**
     * 카테고리 ID가 요청으로 왔을 때 필터링을 위한 메서드
     *
     * @param categoryId 카테고리 아이디
     * @return BooleanExpression
     */
    private BooleanExpression eqCategoryBookId(Long categoryId) {
        if (categoryId == null) {
            return null;
        } else {

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

            // 위 코드에서 얻은 카테고리 아이디를 가진 책의 아이디를 가져옵니다.
            List<Long> bookIdList = from(book)
                    .innerJoin(bookCategory).on(bookCategory.book.bookId.eq(book.bookId))
                    .where(bookCategory.category.categoryId.in(categoryIdList))
                    .distinct()
                    .select(book.bookId)
                    .fetch();

            return book.bookId.in(bookIdList);
        }
    }

    /**
     * 받은 카테고리 아이디에서 최하위 카테고리만 얻는 메서드
     *
     * @param categoryIdList 카테고리 아이디 리스트
     * @return List<Long>
     */
    public List<Long> getLowestCategoryId(List<Long> categoryIdList) {

        return from(category)
                .innerJoin(categoryPath).on(categoryPath.ancestor.categoryId.eq(category.categoryId))
                .where(category.categoryId.in(categoryIdList))
                .select(category.categoryId)
                .groupBy(category)
                .having(category.count().eq(1L))
                .fetch();

    }

    /**
     * 회원이 좋아요한 책을 조회하는 메서드
     *
     * @param memberId 회원 아이디
     * @return List<BookDto.ListResponse>
     */
    @Override
    public Page<BookDto.ListResponse> getMemberLikeBook(Pageable pageable, Long memberId) {

        BooleanExpression isLiked;
        if (memberId == null) {
            isLiked = Expressions.asBoolean(false);
        } else {
            isLiked = new CaseBuilder().when(bookLike.member.customerId.eq(memberId)).then(true)
                    .otherwise(false);
        }

        List<BookDto.ListResponse> bookList = from(book)
                .innerJoin(bookFile).on(book.bookId.eq(bookFile.book.bookId))
                .innerJoin(bookLike).on(book.bookId.eq(bookLike.book.bookId))
                .where(bookFile.imageType.eq(BookFile.ImageType.THUMBNAIL).and(bookLike.member.customerId.eq(memberId)))
                .select(Projections.fields(BookDto.ListResponse.class
                        , book.bookId
                        , book.title
                        , book.engTitle
                        , book.bookStatus
                        , book.quantity
                        , bookFile.url.as("thumbnailImageUrl")
                        , isLiked.as("isLiked")))
                .fetch();

        return new PageImpl<>(bookList, pageable, bookList.size());
    }

    /**
     * 주문량이 많은 책 6권을 조회하는 메서드
     *
     * @return List<BookDto.ListResponse>
     */
    @Override
    public List<BookDto.ListResponse> getBestSeller() {

        return from(book)
                .innerJoin(publisher).on(book.publisher.publisherId.eq(publisher.publisherId))
                .innerJoin(bookFile).on(book.bookId.eq(bookFile.book.bookId))
                .innerJoin(orderDetail).on(book.bookId.eq(orderDetail.book.bookId))
                .where(bookFile.imageType.eq(BookFile.ImageType.THUMBNAIL))
                .groupBy(book, bookFile)
                .select(Projections.fields(BookDto.ListResponse.class
                        , book.bookId
                        , bookFile.url.as("thumbnailImageUrl")
                        , book.title
                        , book.engTitle
                        , book.publishedDate
                        , book.quantity
                        , book.price, book.salePrice, book.bookStatus
                        , publisher.publisherName
                ))
                .orderBy(orderDetail.quantity.sum().desc())
                .limit(6)
                .fetch();
    }

    /**
     * 조회수를 증가 시키는 메서드
     *
     * @param book   책 객체
     * @param bookId 책 아이디
     */
    private synchronized void addViewCount(QBook book, Long bookId) {
        update(book)
                .set(book.viewCount, book.viewCount.add(1))
                .where(book.bookId.eq(bookId))
                .execute();
    }


    /**
     * 조건에 따라 정렬을 설정하는 메서드
     *
     * @param book   책 객체
     * @param review 리뷰 객체
     * @param sort   정렬 조건
     * @return OrderSpecifier<?>
     */
    private OrderSpecifier<?> sorting(QBook book, QReview review, String sort) {

        OrderSpecifier<?> orderSpecifier;

        switch (sort) {

            case "viewCount" :
                orderSpecifier = book.viewCount.desc();
                break;
            case "review":
                orderSpecifier = review.count().desc();
                break;
            case "score":
                orderSpecifier = review.score.avg().desc();
                break;
            case "publishedDate":
                orderSpecifier = book.publishedDate.desc();
                break;
            case "salePriceDesc":
                orderSpecifier = book.salePrice.desc();
                break;
            case "salePriceAsc":
                orderSpecifier = book.salePrice.asc();
                break;
            default:
                orderSpecifier = null;
        }

        return orderSpecifier;
    }

    /**
     * 통합검색, 출판사 검색, 기여자검색, 태그 검색 선택 시 조건을 반영하는 메서드
     *
     * @param keyword   검색 키워드
     * @param condition 검색 조건
     * @return
     */
    private BoolQueryBuilder searchCondition(String keyword, String condition) {

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        MatchQueryBuilder titleTokenQuery = QueryBuilders.matchQuery("title.token", keyword).boost(100);
        MatchQueryBuilder titleJasoQuery = QueryBuilders.matchQuery("title.jaso", keyword).boost(100);
        MatchQueryBuilder engTitleTokenQuery = QueryBuilders.matchQuery("engTitle.token", keyword).boost(100);
        MatchQueryBuilder engTitleJasoQuery = QueryBuilders.matchQuery("engTitle.jaso", keyword).boost(100);

        switch (condition) {
            case "INTEGRATION":

                // 통합 검색은 다른 필드에 추가적인 가중치 설정

                MatchQueryBuilder bookIndexQuery = QueryBuilders.matchQuery("bookIndex.token", keyword).boost(5);
                MatchQueryBuilder descriptionQuery = QueryBuilders.matchQuery("description.token", keyword).boost(5);
                MatchQueryBuilder contributorQuery =
                        QueryBuilders.matchQuery("contributorName.token", keyword).boost(20);
                MatchQueryBuilder tagQuery = QueryBuilders.matchQuery("tagName.token", keyword).boost(15);
                MatchQueryBuilder publisherQuery = QueryBuilders.matchQuery("publisherName.token", keyword).boost(20);

                boolQuery.should(titleTokenQuery);
                boolQuery.should(titleJasoQuery);
                boolQuery.should(bookIndexQuery);
                boolQuery.should(descriptionQuery);
                boolQuery.should(contributorQuery);
                boolQuery.should(tagQuery);
                boolQuery.should(publisherQuery);
                boolQuery.should(engTitleTokenQuery);
                boolQuery.should(engTitleJasoQuery);

                return boolQuery;

            case "PUBLISHER":

                MatchQueryBuilder publisherTokenQuery = QueryBuilders.matchQuery("publisherName.token", keyword).boost(100);
                MatchQueryBuilder publisherJasoQuery = QueryBuilders.matchQuery("publisherName.jaso", keyword).boost(100);
                return boolQuery
                        .should(publisherTokenQuery)
                        .should(publisherJasoQuery);

            case "CONTRIBUTOR":
                MatchQueryBuilder contributorTokenQuery = QueryBuilders.matchQuery("contributorName.token", keyword).boost(100);
                MatchQueryBuilder contributorJasoQuery = QueryBuilders.matchQuery("contributorName.jaso", keyword).boost(100);
                return boolQuery
                        .should(contributorTokenQuery)
                        .should(contributorJasoQuery);
            case "TAG":
                MatchQueryBuilder tagTokenQuery = QueryBuilders.matchQuery("tagName.token", keyword).boost(100);
                MatchQueryBuilder tagJasoQuery = QueryBuilders.matchQuery("tagName.jaso", keyword).boost(100);
                return boolQuery
                        .should(tagTokenQuery)
                        .should(tagJasoQuery);
            default:
                return boolQuery
                        .should(titleTokenQuery)
                        .should(titleJasoQuery)
                        .should(engTitleTokenQuery)
                        .should(engTitleJasoQuery);

        }
    }
}
