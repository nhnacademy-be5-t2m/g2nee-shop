package com.t2m.g2nee.shop.bookset.Tag.domain;

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
