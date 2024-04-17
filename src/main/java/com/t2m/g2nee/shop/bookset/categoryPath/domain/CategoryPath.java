package com.t2m.g2nee.shop.bookset.categoryPath.domain;

import com.t2m.g2nee.shop.bookset.category.domain.Category;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 카테고리 경로 엔티티
 *
 * @author : 김수빈
 * @since : 1.0
 */
@Entity
@Table(name = "CategoryPaths")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
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

    private Long depth;

    public CategoryPath(Category ancestor, Category descendant, Long depth) {
        this.ancestor = ancestor;
        this.descendant = descendant;
        this.depth = depth;
    }
}
