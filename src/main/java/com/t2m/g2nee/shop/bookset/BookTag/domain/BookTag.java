package com.t2m.g2nee.shop.bookset.BookTag.domain;

import com.t2m.g2nee.shop.bookset.Book.domain.Book;
import com.t2m.g2nee.shop.bookset.tag.domain.Tag;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "BookTags")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookTagId;

    @ManyToOne
    @JoinColumn(name = "tagId")
    private Tag tag;

    @ManyToOne
    @JoinColumn(name = "bookId")
    private Book book;

}
