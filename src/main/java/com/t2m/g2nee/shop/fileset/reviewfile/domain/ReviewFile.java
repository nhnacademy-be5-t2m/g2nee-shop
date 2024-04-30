package com.t2m.g2nee.shop.fileset.reviewfile.domain;

import com.t2m.g2nee.shop.fileset.file.domain.File;
import com.t2m.g2nee.shop.review.domain.Review;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "ReviewFiles")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("ReviewFile")
public class ReviewFile extends File {

    @ManyToOne
    @JoinColumn(name = "reviewId")
    private Review review;
}
