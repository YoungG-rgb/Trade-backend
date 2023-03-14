package kg.tech.tradebackend.schedulers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnProperty(value = "scheduler.enable", havingValue = "true")
public class CouponFactoryScheduler {

    @Scheduled(cron = "${scheduler.jobs.create-coupon}")
    public void createCoupon(){
        log.info("coupons added to user: {}", 1);
    }
}
