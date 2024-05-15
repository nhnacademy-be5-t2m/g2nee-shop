package com.t2m.g2nee.shop.point.domain;

import com.t2m.g2nee.shop.memberset.member.domain.Member;
import com.t2m.g2nee.shop.orderset.order.domain.Order;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Points")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pointId;

    private int point;
    @Enumerated(EnumType.STRING)
    private ChangeReason changeReason;
    private LocalDateTime changeDate;

    @ManyToOne
    @JoinColumn(name = "orderId")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    public enum ChangeReason {
        REVIEW("리뷰작성"), PHOTO_REVIEW("사진리뷰작성"), SIGNUP("회원가입"), PURCHASE("구매 적립"), RETIRE("구매 적립금 회수(반품)"),
        RETURN("사용 포인트 반환"),
        USE("포인트 사용");

        private final String name;

        ChangeReason(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
