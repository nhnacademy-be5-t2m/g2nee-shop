package com.t2m.g2nee.shop.BookSet.Publisher.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Publishers")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long publisherId;

    private String publisherName;

    private String publisherEngName;
}
