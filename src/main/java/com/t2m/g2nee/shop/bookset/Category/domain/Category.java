package com.t2m.g2nee.shop.bookset.Category.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Categories")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    private String categoryName;
    private String categoryEngName;
}
