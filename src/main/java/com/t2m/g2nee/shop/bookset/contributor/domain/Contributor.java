package com.t2m.g2nee.shop.bookset.contributor.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Contributors")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contributor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contributorId;
    private String contributorName;
    private String contributorEngName;

}
