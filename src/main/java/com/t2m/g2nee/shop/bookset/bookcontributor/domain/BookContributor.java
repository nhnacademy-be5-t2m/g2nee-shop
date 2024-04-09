package com.t2m.g2nee.shop.bookset.bookcontributor.domain;

import com.t2m.g2nee.shop.bookset.book.domain.Book;
import com.t2m.g2nee.shop.bookset.contributor.domain.Contributor;
import com.t2m.g2nee.shop.bookset.role.domain.Role;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "BookContributor")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookContributor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookContributorId;

    @ManyToOne
    @JoinColumn(name = "contributorId")
    private Contributor contributor;

    @ManyToOne
    @JoinColumn(name = "bookId")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "roleId")
    private Role role;
}
