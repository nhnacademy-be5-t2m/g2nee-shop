package com.t2m.g2nee.shop.scheduler;

import com.t2m.g2nee.shop.orderset.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Scheduler {
    private final OrderService orderService;

    @Scheduled(cron = "0 0 0 4 * ?")
    public void gradeUpdate() {
        orderService.updateGrade();
    }
}
