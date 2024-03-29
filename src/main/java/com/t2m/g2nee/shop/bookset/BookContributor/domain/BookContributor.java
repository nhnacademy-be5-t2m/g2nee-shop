package com.t2m.g2nee.shop.bookset.BookContributor.domain;

import com.t2m.g2nee.shop.bookset.Book.domain.Book;
import com.t2m.g2nee.shop.bookset.Contributor.domain.Contributor;
import com.t2m.g2nee.shop.bookset.Role.domain.Role;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
