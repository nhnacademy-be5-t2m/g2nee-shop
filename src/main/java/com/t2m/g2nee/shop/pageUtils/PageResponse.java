package com.t2m.g2nee.shop.pageUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Pageing 객체를 반환 시 필요한 정보를 담기 위한 클래스 입니다.
 *
 * @author : 신동민
 * @since : 1.0
 */

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageResponse<T> {

    private List<T> data;
    private int currentPage;
    private int startPage;
    private int endPage;
    private int totalPage;
    private Long totalElements;
}
