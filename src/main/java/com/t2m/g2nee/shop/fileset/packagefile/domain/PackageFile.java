package com.t2m.g2nee.shop.fileset.packagefile.domain;

import com.t2m.g2nee.shop.fileset.file.domain.File;
import com.t2m.g2nee.shop.orderset.packagetype.domain.PackageType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Entity
@Table(name = "PackageFiles")
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("PackageFiles")
public class PackageFile extends File {

    @ManyToOne
    @JoinColumn(name = "packageId")
    private PackageType packageType;
}
