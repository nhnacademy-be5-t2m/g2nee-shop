package com.t2m.g2nee.shop.pageUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

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
    private boolean hasPreviousPage;
    private boolean hasNextPage;
    private boolean isFirstPage;
    private boolean isLastPage;
    private boolean hasData;
    private int totalPage;
    private Long totalElements;

    public PageResponse(Page<T> result) {
        this.data = result.getContent();
        this.totalPage = result.getTotalPages();
        this.currentPage = result.getNumber();
        this.hasPreviousPage = result.hasPrevious();
        this.hasNextPage = result.hasNext();
        this.isFirstPage = result.isFirst();
        this.isLastPage = result.isLast();
        this.hasData = result.hasContent();
        this.totalElements = result.getTotalElements();
    }
}
