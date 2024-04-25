package com.t2m.g2nee.shop.pageUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.t2m.g2nee.shop.review.dto.ReviewDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

/**
 * Pageing 객체를 반환 시 필요한 정보를 담기 위한 클래스 입니다.
 *
 * @author : 신동민
 * @since : 1.0
 */

@Getter
@Setter
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


    public PageResponse<T> getPageResponse(int page, int maxPageButtons,
                                           Page<T> objectPage) {

        int startPage = (int) Math.max(1, objectPage.getNumber() - Math.floor((double) maxPageButtons / 2));
        int endPage = Math.min(startPage + maxPageButtons - 1, objectPage.getTotalPages());

        if (endPage - startPage + 1 < maxPageButtons) {
            startPage = Math.max(1, endPage - maxPageButtons + 1);
        }
        return PageResponse.<T>builder()
                .data(objectPage.getContent())
                .currentPage(page)
                .startPage(startPage)
                .endPage(endPage)
                .totalPage(objectPage.getTotalPages())
                .totalElements(objectPage.getTotalElements())
                .build();
    }
}
