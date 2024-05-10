package com.t2m.g2nee.shop.shoppingcart.service;

import com.t2m.g2nee.shop.bookset.book.domain.Book;
import com.t2m.g2nee.shop.bookset.book.repository.BookRepository;
import com.t2m.g2nee.shop.exception.BadRequestException;
import com.t2m.g2nee.shop.exception.NotFoundException;
import com.t2m.g2nee.shop.memberset.member.domain.Member;
import com.t2m.g2nee.shop.memberset.member.repository.MemberRepository;
import com.t2m.g2nee.shop.shoppingcart.domain.ShoppingCart;
import com.t2m.g2nee.shop.shoppingcart.dto.ShoppingCartDto;
import com.t2m.g2nee.shop.shoppingcart.repository.ShoppingCartRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 장바구니 service 클래스
 *
 * @author : 신동민
 * @since : 1.0
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;

    public ShoppingCartDto.Response getBookForCart(String bookId, int quantity){

        ShoppingCartDto.Response response = shoppingCartRepository.getBookForCart(Long.valueOf(bookId));
        response.setQuantity(quantity);

        if(response.getBookQuantity() < quantity){
            throw new BadRequestException("재고보다 많이 담을 수는 없습니다.");
        }
        return response;
    }


}
