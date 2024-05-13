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
import java.util.stream.Collectors;
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
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    public ShoppingCartDto.Response getBookForCart(String bookId, int quantity) {

        ShoppingCartDto.Response response = shoppingCartRepository.getBookForCart(Long.valueOf(bookId));
        response.setQuantity(quantity);

        if (response.getBookQuantity() < quantity) {
            throw new BadRequestException("재고보다 많이 담을 수는 없습니다.");
        }
        return response;
    }

    /**
     * DB로 장바구니 정보를 옮기는 메서드
     *
     * @param memberId    회원아이디
     * @param requestList 장바구니 객체리스트
     */
    public void migrateCartToDB(Long memberId, List<ShoppingCartDto.Request> requestList) {

        // DB 장바구니를 비웁니다.
        shoppingCartRepository.deleteByMemberId(memberId);

        Optional<Member> optionalMember = memberRepository.findById(memberId);

        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();

            List<ShoppingCart> shoppingCartList = requestList.stream()
                    .map(sc -> {
                        Optional<Book> optionalBook = bookRepository.findById(Long.valueOf(sc.getBookId()));
                        if (optionalBook.isPresent()) {
                            Book book = optionalBook.get();
                            return ShoppingCart.builder()
                                    .member(member)
                                    .book(book)
                                    .quantity(sc.getQuantity())
                                    .build();
                        } else {
                            throw new NotFoundException("책 정보가 없습니다");
                        }
                    }).collect(Collectors.toList());
            shoppingCartRepository.saveAll(shoppingCartList);
        } else {
            throw new NotFoundException("회원 정보가 없습니다");
        }
    }

}
