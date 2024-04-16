package com.t2m.g2nee.shop.orderset.packageType.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PackageSaveDto {

    @NotBlank(message = "포장지 명은 비울 수 없습니다.")
    @Size(min = 1, max = 20, message = "20자 미만으로 입력해 주세요.")
    private String name;

    @Pattern(regexp = "\\d+", message = "가격은 숫자로 입력해주세요")
    private int price;

    @NotNull(message = "포장지 활성 정보가 없음!")
    private Boolean isActivated;
}
