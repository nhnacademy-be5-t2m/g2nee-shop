package com.t2m.g2nee.shop.Like.domain;

import com.t2m.g2nee.shop.BookSet.Book.domain.Book;
import com.t2m.g2nee.shop.MemberSet.Member.domain.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "BookLikes")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookLikeId;

    @ManyToOne
    @JoinColumn(name = "bookId")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "memberId", referencedColumnName = "customerId")
    private Member member;
}
