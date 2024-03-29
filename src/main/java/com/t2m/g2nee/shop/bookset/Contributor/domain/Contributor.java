package com.t2m.g2nee.shop.bookset.Contributor.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
