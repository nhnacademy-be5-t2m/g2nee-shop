package com.t2m.g2nee.shop.ShoppingCart.domain;

import com.t2m.g2nee.shop.BookSet.Book.domain.Book;
import com.t2m.g2nee.shop.MemberSet.Member.domain.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "ShoppingCart")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shoppingCartId;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "bookId")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

}
