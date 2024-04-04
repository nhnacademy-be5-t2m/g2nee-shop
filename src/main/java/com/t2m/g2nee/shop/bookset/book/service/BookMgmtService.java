package com.t2m.g2nee.shop.bookset.book.service;

import static com.t2m.g2nee.shop.fileset.bookfile.domain.BookFile.ImageType;

import com.t2m.g2nee.shop.bookset.book.domain.Book;
import com.t2m.g2nee.shop.bookset.book.dto.BookDto;
import com.t2m.g2nee.shop.bookset.book.mapper.BookMapper;
import com.t2m.g2nee.shop.bookset.book.repository.BookRepository;
import com.t2m.g2nee.shop.bookset.bookcategory.domain.BookCategory;
import com.t2m.g2nee.shop.bookset.bookcategory.repository.BookCategoryRepository;
import com.t2m.g2nee.shop.bookset.bookcontributor.domain.BookContributor;
import com.t2m.g2nee.shop.bookset.bookcontributor.repository.BookContributorRepository;
import com.t2m.g2nee.shop.bookset.booktag.domain.BookTag;
import com.t2m.g2nee.shop.bookset.booktag.repository.BookTagRepository;
import com.t2m.g2nee.shop.bookset.category.domain.Category;
import com.t2m.g2nee.shop.bookset.category.repository.CategoryRepository;
import com.t2m.g2nee.shop.bookset.contributor.domain.Contributor;
import com.t2m.g2nee.shop.bookset.contributor.repository.ContributorRepository;
import com.t2m.g2nee.shop.bookset.publisher.domain.Publisher;
import com.t2m.g2nee.shop.bookset.publisher.repository.PublisherRepository;
import com.t2m.g2nee.shop.bookset.role.domain.Role;
import com.t2m.g2nee.shop.bookset.role.repository.RoleRepository;
import com.t2m.g2nee.shop.bookset.tag.domain.Tag;
import com.t2m.g2nee.shop.bookset.tag.repository.TagRepository;
import com.t2m.g2nee.shop.exception.BadRequestException;
import com.t2m.g2nee.shop.exception.NotFoundException;
import com.t2m.g2nee.shop.fileset.bookfile.domain.BookFile;
import com.t2m.g2nee.shop.fileset.bookfile.repostitory.BookFileRepository;
import com.t2m.g2nee.shop.nhnstorage.AuthService;
import com.t2m.g2nee.shop.nhnstorage.ObjectService;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class BookMgmtService {

    private final BookRepository bookRepository;
    private final BookMapper mapper;
    private final BookFileRepository bookFileRepository;
    private final CategoryRepository categoryRepository;
    private final ObjectService objectService;
    private final AuthService authService;
    private final BookCategoryRepository bookCategoryRepository;
    private final BookTagRepository bookTagRepository;
    private final TagRepository tagRepository;
    private final PublisherRepository publisherRepository;
    private final ContributorRepository contributorRepository;
    private final RoleRepository roleRepository;
    private final BookContributorRepository bookContributorRepository;

    /**
     * 도서를 등록하는 메서드입니다.
     *
     * @param request   도서 등록에 필요한 정보가 담긴 객체
     * @param thumbnail 도서 섬네일 이미지 파일
     * @param details   도서 세부 이미지 파일
     * @return 등록한 책 정보 응답 객체
     */
    public BookDto.Response registerBook(BookDto.Request request, MultipartFile thumbnail, MultipartFile[] details) {


        // 요청을 Book 엔티티로 변경합니다.
        Book book = mapper.dtoToEntity(request);
        // 출판사 정보를 얻어 Book 엔티티에 매핑 후 저장합니다.
        Publisher publisher = publisherRepository.findByPublisherName(request.getPublisherName())
                .orElseThrow(() -> new NotFoundException("출판사 정보가 없습니다."));
        book.setPublisher(publisher);
        book.setBookStatus(Book.BookStatus.ONSALE);
        Book saveBook = bookRepository.save(book);

        // storage 사용을 위한 토큰을 발급합니다.
        String tokenId = authService.requestToken();
        // storage에 이미지를 업로드합니다
        uploadImage(thumbnail, book, tokenId, "thumb_" + request.getEngTitle(), ImageType.THUMBNAIL);
        for (int i = 0; i < details.length; i++) {
            String objectName = "detail_" + request.getEngTitle() + (i + 1);
            uploadImage(details[i], book, tokenId, objectName, ImageType.DETAIL);
        }
        // 도서카테고리 연관 관계를 설정합니다.
        saveBookCategory(request.getCategoryNameList(), book);

        // 도서태그 연관관계를 설정해줍니다.
        saveBookTag(book, request.getTagNameList());

        // 도서기여자 연관관계를 설정해줍니다.
        saveBookContributor(book, request.getContributorNameList(), request.getRoleNameList());

        return mapper.entityToDto(saveBook);

    }

    /**
     * 도서 정보를 수정하는 메서드입니다.
     *
     * @param bookId    도서 아이디
     * @param request   도서 수정할 정보가 담긴 객체
     * @param thumbnail 바꿀 섬네일 이미지 파일
     * @param details    바꿀 세부 이미지 파일
     * @return 수정된 도서 응답 객체
     */
    public BookDto.Response updateBook(Long bookId, BookDto.Request request, MultipartFile thumbnail,
                                       MultipartFile[] details) {

        Optional<Book> optionalBook = bookRepository.findById(bookId);

        if (optionalBook.isPresent()) {

            Book book = optionalBook.get();

            if (thumbnail != null || details != null) {
                // storage 사용을 위한 토큰을 발급합니다.
                String tokenId = authService.requestToken();

                // 섬네일 이미지 파일을 변경했을 때 실행합니다.
                if (thumbnail != null) {

                    String objectName = "thumb_" + request.getEngTitle();
                    // 기존 이미지를 삭제합니다'
                    BookFile bookFile = bookFileRepository.findByBookIdAndImageType(bookId, ImageType.THUMBNAIL);
                    String imageUrl = bookFile.getUrl();
                    deleteImage(imageUrl, tokenId);
                    // 기존 이미지의 연관관계를 없앱니다.
                    bookFileRepository.deleteByBookIdAndImageType(bookId, ImageType.THUMBNAIL);
                    // storage에 새 이미지를 업로드합니다
                    uploadImage(thumbnail, book, tokenId, objectName, ImageType.THUMBNAIL);

                }
                // 세부 이미지 파일을 변경했을 때 실행합니다.
                if (details != null) {

                    // 기존 이미지를 삭제합니다'
                    BookFile bookFile = bookFileRepository.findByBookIdAndImageType(bookId, ImageType.DETAIL);
                    String imageUrl = bookFile.getUrl();
                    deleteImage(imageUrl, tokenId);
                    // 기존 이미지의 연관관계를 없앱니다.
                    bookFileRepository.deleteByBookIdAndImageType(bookId, ImageType.DETAIL);
                    // storage에 새 이미지를 업로드합니다

                    for (int i = 0; i < details.length; i++) {
                        String objectName = "detail_" + request.getEngTitle() + (i + 1);
                        uploadImage(details[i], book, tokenId, objectName, ImageType.DETAIL);

                    }
                }

            }
            // 책 정보에 변경이 있다면 수정합니다.
            Optional.ofNullable(request.getTitle()).ifPresent(book::setTitle);
            Optional.ofNullable(request.getEngTitle()).ifPresent(book::setEngTitle);
            Optional.ofNullable(request.getBookIndex()).ifPresent(book::setBookIndex);
            Optional.ofNullable(request.getDescription()).ifPresent(book::setDescription);
            Optional.ofNullable(request.getPublishedDate()).ifPresent(book::setPublishedDate);
            Optional.of(request.getPrice()).ifPresent(book::setPrice);
            Optional.of(request.getSalePrice()).ifPresent(book::setSalePrice);
            Optional.ofNullable(request.getIsbn()).ifPresent(book::setIsbn);
            Optional.of(request.getPages()).ifPresent(book::setPages);
            Optional.of(request.getQuantity()).ifPresent(book::setQuantity);

            // 출판사, 기여자, 역할, 태그 연관관계에 변경사항이 있다면 다시 관계를 만들어 줍니다.
            Optional.ofNullable(request.getPublisherName())
                    .map(publisherRepository::findByPublisherName)
                    .orElseThrow(() -> new NotFoundException("출판사 정보가 없습니다."))
                    .ifPresent(book::setPublisher);
            Optional.ofNullable(request.getContributorNameList())
                    .ifPresent(contributorNameList -> {
                        bookContributorRepository.deleteByBookId(bookId);
                        saveBookContributor(book, contributorNameList, request.getRoleNameList());
                    });
            Optional.ofNullable(request.getCategoryNameList())
                    .ifPresent(categoryNameList -> {
                        bookCategoryRepository.deleteByBookId(bookId);
                        saveBookCategory(categoryNameList, book);
                    });
            Optional.ofNullable(request.getTagNameList())
                    .ifPresent(tagNameList -> {
                        bookTagRepository.deleteByBookId(bookId);
                        saveBookTag(book, tagNameList);
                    });

            return mapper.entityToDto(book);

        } else {
            throw new NotFoundException("책 정보가 없습니다.");
        }

    }

    /**
     * 책 상태를 바꾸는 메서드 입니다
     * @param bookId 상태를 바꿀 책 아이디
     * @param request 상태를 정보를 가진 객체
     * @return
     */

    public BookDto.statusResponse modifyStatus(Long bookId, BookDto.Request request) {

        Optional<Book> optionalBook = bookRepository.findById(bookId);

        if (optionalBook.isPresent()) {

            Book book = optionalBook.get();
            book.setBookStatus(request.getBookStatus());

            return BookDto.statusResponse.builder()
                    .status(book.getBookStatus())
                    .build();
        } else {
            throw new NotFoundException("책 정보가 없습니다.");
        }

    }

    /**
     * 책 등록 시 이미지 정보를 저장하는 메서드 입니다.
     *
     * @param url  이미지 url
     * @param book 책 객체
     */

    public void saveBookFile(String url, Book book, ImageType imageType) {

        BookFile bookFile = BookFile.builder()
                .url(url)
                .type("book")
                .book(book)
                .imageType(imageType)
                .build();

        bookFileRepository.save(bookFile);

    }

    /**
     * 책 등록 시 카테고리 연관관계를 설정하는 메서드 입니다.
     *
     * @param categoryNameList 카테고리 이름 목록
     * @param book             책 객체
     */
    public void saveBookCategory(List<String> categoryNameList, Book book) {
        for (String categoryName : categoryNameList) {
            Category category = categoryRepository.findByCategoryName(categoryName)
                    .orElseThrow(() -> new NotFoundException("카테고리가 존재하지 않습니다."));

            BookCategory bookCategory = BookCategory.builder()
                    .book(book)
                    .category(category)
                    .build();
            bookCategoryRepository.save(bookCategory);
        }
    }

    /**
     * 책 등록 시 태그에 대한 설정을 하는 메서드 입니다.
     *
     * @param book        책 객체
     * @param tagNameList 태그 이름 목록
     */
    public void saveBookTag(Book book, List<String> tagNameList) {

        // 태그가 없으면 설정하지 않습니다.
        if (!tagNameList.isEmpty()) {
            for (String tagName : tagNameList) {
                Tag findTag =
                        tagRepository.findByTagName(tagName).orElseThrow(() -> new NotFoundException("태그 정보가 없습니다."));
                BookTag bookTag = BookTag.builder()
                        .book(book)
                        .tag(findTag)
                        .build();
                bookTagRepository.save(bookTag);
            }
        }
    }

    /**
     * 책 등록 시 기여자와 역할에 대한 설정을 하는 메서드 입니다.
     *
     * @param book                책 객체
     * @param contributorNameList 기여자 이름 목록
     * @param roleNameList        역할 이름 목록
     */
    public void saveBookContributor(Book book, List<String> contributorNameList, List<String> roleNameList) {

        if (contributorNameList.size() != roleNameList.size()) {
            throw new BadRequestException("기여자 이름 목록과 역할 이름 목록이 일치하지 않습니다.");
        }

        for (int i = 0; i < contributorNameList.size(); i++) {

            String contributorName = contributorNameList.get(i);
            String roleName = roleNameList.get(i);

            Contributor contributor = contributorRepository.findByContributorName(contributorName)
                    .orElseThrow(() -> new NotFoundException("기여자 정보가 없습니다."));

            Role role =
                    roleRepository.findByRoleName(roleName).orElseThrow(() -> new NotFoundException("역할 정보가 없습니다."));

            BookContributor bookContributor = BookContributor.builder()
                    .book(book)
                    .role(role)
                    .contributor(contributor)
                    .build();
            bookContributorRepository.save(bookContributor);
        }
    }

    public void uploadImage(MultipartFile file, Book book, String tokenId, String engTitle, ImageType imageType) {

        try {
            // 파일을 업로드합니다. 이때 url은 /book/도서영문명 으로 저장됩니다.
            InputStream inputStream = file.getInputStream();
            String url = objectService.uploadObject(tokenId, "book", engTitle, inputStream);
            // 도서파일 연관 관계를 설정합니다.
            saveBookFile(url, book, imageType);
        } catch (IOException e) {
            throw new NotFoundException("파일을 찾을 수 없습니다.");
        }
    }

    public void deleteImage(String url, String tokenId) {

        objectService.deleteObject(url, tokenId);
    }
}
