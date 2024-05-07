package com.t2m.g2nee.shop.shoppingcart.service;

import com.t2m.g2nee.shop.bookset.book.domain.Book;
import com.t2m.g2nee.shop.bookset.book.repository.BookRepository;
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
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;

    /**
     * 장바구니에 책을 넣는 메서드 입니다.
     *
     * @param request 요청 객체
     * @return ShoppingCartDto.Response
     */
    public ShoppingCartDto.Response putBookInCart(ShoppingCartDto.Request request) {

        Book book = checkBook(request.getBookId());
        Optional<Member> member = checkMember(request.getMemberId());


        ShoppingCart shoppingCart = ShoppingCart.builder()
                .book(book)
                .quantity(request.getQuantity())
                .build();

        // 비회원일 경우 DB에 저장하지 않음
        if (member.isEmpty()) {

            return ShoppingCartDto.Response.builder()
                    .shoppingCartId(shoppingCart.getShoppingCartId())
                    .bookId(book.getBookId())
                    .title(shoppingCart.getBook().getTitle())
                    .price(book.getSalePrice())
                    .quantity(shoppingCart.getQuantity())
                    .build();

        }
        // 회원인 경우 저장
        else {

            shoppingCart.setMember(member.get());
            ShoppingCart saveShoppingCart = shoppingCartRepository.save(shoppingCart);

            return ShoppingCartDto.Response.builder()
                    .shoppingCartId(saveShoppingCart.getShoppingCartId())
                    .bookId(book.getBookId())
                    .memberId(member.get().getCustomerId())
                    .title(saveShoppingCart.getBook().getTitle())
                    .quantity(saveShoppingCart.getQuantity())
                    .price(saveShoppingCart.getBook().getPrice())
                    .build();
        }


    }

    //TODO: 나중에
    public ShoppingCartDto.Response updateShoppingCart(ShoppingCartDto.Request request) {
        return null;
    }

    public List<ShoppingCartDto.Response> getShoppingCart(Long memberId) {

        return shoppingCartRepository.getShoppingCart(memberId);
    }

    public void deleteBookInCart(Long shoppingCartId) {

        shoppingCartRepository.deleteById(shoppingCartId);
    }

    private Optional<Member> checkMember(Long memberId) {

        return memberRepository.findById(memberId);
    }

    private Book checkBook(Long bookId) {
        // 프론트 쪽에서 절판, 매진된 책을 넣을 경우 처리하기
        return bookRepository.findByBookId(bookId).orElseThrow(() -> new NotFoundException("책 정보를 찾을 수 없습니다."));
    }

}
