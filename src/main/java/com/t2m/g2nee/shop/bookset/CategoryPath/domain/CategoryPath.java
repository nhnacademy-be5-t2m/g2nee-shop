package com.t2m.g2nee.shop.bookset.CategoryPath.domain;

import com.t2m.g2nee.shop.bookset.Category.domain.Category;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "CategoryPaths")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryPath {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryPathId;

    @ManyToOne
    @JoinColumn(name = "ancestorId")
    private Category ancestor;

    @ManyToOne
    @JoinColumn(name = "descendantId")
    private Category descendant;
}
