package com.t2m.g2nee.shop.BookSet.Tag.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Tags")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagId;
    private String tagName;
}
