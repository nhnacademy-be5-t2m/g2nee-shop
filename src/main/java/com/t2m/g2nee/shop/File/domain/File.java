package com.t2m.g2nee.shop.File.domain;


import com.t2m.g2nee.shop.BookSet.Book.domain.Book;
import com.t2m.g2nee.shop.MemberSet.Member.domain.Member;
import com.t2m.g2nee.shop.Review.domain.Review;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Files")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileId;

    private String url;
    private String extensionName;
    private String type;

    @ManyToOne
    @JoinColumn(name = "bookId")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "reviewId")
    private Review review;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;
}
