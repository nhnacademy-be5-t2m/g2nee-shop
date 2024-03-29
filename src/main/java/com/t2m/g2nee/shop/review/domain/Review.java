package com.t2m.g2nee.shop.review.domain;

import com.t2m.g2nee.shop.bookset.Book.domain.Book;
import com.t2m.g2nee.shop.memberset.Member.domain.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Reviews")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;
    private String content;
    private int score;
    @ManyToOne
    @JoinColumn(name = "bookId")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;
}
