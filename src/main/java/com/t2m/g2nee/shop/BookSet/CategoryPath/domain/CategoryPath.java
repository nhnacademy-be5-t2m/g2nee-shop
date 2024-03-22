package com.t2m.g2nee.shop.BookSet.CategoryPath.domain;

import com.t2m.g2nee.shop.BookSet.Categroy.domain.Category;
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
    @JoinColumn(name = "ancestorId", referencedColumnName = "categoryId")
    private Category ancestor;

    @ManyToOne
    @JoinColumn(name = "descendantId", referencedColumnName = "categoryId")
    private Category descendant;
}
