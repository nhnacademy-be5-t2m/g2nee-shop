package com.t2m.g2nee.shop.shoppingcart.repository;

import com.querydsl.core.types.Projections;
import com.t2m.g2nee.shop.bookset.book.domain.QBook;
import com.t2m.g2nee.shop.fileset.bookfile.domain.BookFile;
import com.t2m.g2nee.shop.fileset.bookfile.domain.QBookFile;
import com.t2m.g2nee.shop.shoppingcart.domain.QShoppingCart;
import com.t2m.g2nee.shop.shoppingcart.domain.ShoppingCart;
import com.t2m.g2nee.shop.shoppingcart.dto.ShoppingCartDto;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class ShoppingCartCustomRepositoryImpl extends QuerydslRepositorySupport
        implements ShoppingCartCustomRepository {
    public ShoppingCartCustomRepositoryImpl() {
        super(ShoppingCart.class);
    }

    QShoppingCart shoppingCart = QShoppingCart.shoppingCart;
    QBook book = QBook.book;

    QBookFile bookFile = QBookFile.bookFile;


    /**
     * 회원의 장바구니 정보를 가져오는 메서드
     *
     * @param memberId 회원 아이디
     * @return List<ShoppingCartDto.Response>
     */
    @Override
    public List<ShoppingCartDto.Response> getShoppingCart(Long memberId) {

        return from(shoppingCart)
                .innerJoin(book).on(book.bookId.eq(shoppingCart.book.bookId))
                .innerJoin(bookFile).on(bookFile.book.bookId.eq(book.bookId))
                .where(shoppingCart.member.customerId.eq(memberId)
                        .and(bookFile.imageType.eq(BookFile.ImageType.THUMBNAIL)))
                .select(Projections.fields(ShoppingCartDto.Response.class
                        , shoppingCart.shoppingCartId
                        , shoppingCart.quantity
                        , book.title
                        , book.salePrice.as("price")
                        , bookFile.url.as("imageUrl")
                        , book.quantity))
                .fetch();
    }

    @Override
    public ShoppingCartDto.Response getCartBook(Long bookId) {
        return from(shoppingCart)
                .innerJoin(book).on(book.bookId.eq(shoppingCart.book.bookId))
                .innerJoin(bookFile).on(book.bookId.eq(bookFile.book.bookId))
                .where(book.bookId.eq(bookId).and(bookFile.imageType.eq(BookFile.ImageType.THUMBNAIL)))
                .select(Projections.fields(ShoppingCartDto.Response.class
                        , shoppingCart.shoppingCartId
                        , shoppingCart.quantity
                        , book.title
                        , book.salePrice.as("price")
                        , bookFile.url.as("imageUrl")
                        , book.quantity))
                .fetchOne();
    }
}
