package com.sevenXnetworks.treasure.web.runner;

import com.sevenXnetworks.treasure.entity.ActivityEntity;
import com.sevenXnetworks.treasure.service.ActivityService;
import com.sevenXnetworks.treasure.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @Description 活动结束后自动抽奖
 * @Author sulongfei
 * @Date 2018/11/15 19:35
 * @Version 1.0
 */
@Component
public class TimerTaskRunner implements CommandLineRunner {
    @Autowired
    private ActivityService activityService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private Timer timer;

    @Override
    public void run(String... strings) {
        List<ActivityEntity> activityVos = activityService.list();
        activityVos.forEach(activityVo -> {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    orderService.random(activityVo.getId());
                }
            }, activityVo.getEndTime());
        });
    }
}
