package com.t2m.g2nee.shop.fileset.bookfile.domain;

import com.t2m.g2nee.shop.bookset.book.domain.Book;
import com.t2m.g2nee.shop.fileset.file.domain.File;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Entity
@Table(name = "BookFiles")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("BookFiles")
public class BookFile extends File {

    @ManyToOne
    @JoinColumn(name = "bookId")
    private Book book;

    @Enumerated(EnumType.STRING)
    private ImageType imageType;

    public enum ImageType {
        THUMBNAIL, DETAIL;

    }
}
