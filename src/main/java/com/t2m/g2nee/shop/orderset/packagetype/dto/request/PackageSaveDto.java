package com.t2m.g2nee.shop.orderset.packagetype.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 포장지를 저장하기 위한 객체입니다.
 *
 * @author : 김수빈
 * @since : 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PackageSaveDto {

    /**
     * 저장할 포장지 이름
     */
    @NotBlank(message = "포장지 명은 비울 수 없습니다.")
    @Size(min = 1, max = 20, message = "20자 미만으로 입력해 주세요.")
    private String name;

    /**
     * 포장지 가격
     */
    @NotNull(message = "가격을 입력해주세요")
    @Min(value = 0, message = "가격은 음수가 될 수 없습니다.")
    private Integer price;

    /**
     * 포장지 활성 유무
     */
    @NotNull(message = "포장지 활성 정보가 없음!")
    private Boolean isActivated;
}
