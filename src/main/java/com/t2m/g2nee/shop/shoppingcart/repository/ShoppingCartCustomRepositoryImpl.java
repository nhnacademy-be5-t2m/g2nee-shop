package com.t2m.g2nee.shop.shoppingcart.repository;

import com.querydsl.core.types.Projections;
import com.t2m.g2nee.shop.bookset.book.domain.QBook;
import com.t2m.g2nee.shop.fileset.bookfile.domain.BookFile;
import com.t2m.g2nee.shop.fileset.bookfile.domain.QBookFile;
import com.t2m.g2nee.shop.memberset.member.domain.QMember;
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

    QBook book = QBook.book;
    QBookFile bookFile = QBookFile.bookFile;


    @Override
    public ShoppingCartDto.Response getBookForCart(Long bookId) {
        return from(book)
                .innerJoin(bookFile).on(book.bookId.eq(bookFile.book.bookId))
                .where(book.bookId.eq(bookId).and(bookFile.imageType.eq(BookFile.ImageType.THUMBNAIL)))
                .select(Projections.fields(ShoppingCartDto.Response.class
                        , book.bookId
                        , book.title
                        , book.engTitle
                        , book.salePrice.as("price")
                        , bookFile.url.as("imageUrl")
                        , book.quantity.as("bookQuantity")))
                .fetchOne();
    }
}
