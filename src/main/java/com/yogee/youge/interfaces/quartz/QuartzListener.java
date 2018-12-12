package com.yogee.youge.interfaces.quartz;

import com.yogee.youge.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 定时任务
 * Created by Haipeng.Ren on 2017/8/2.
 */
@Service("taskJob")
@Lazy(false)
public class QuartzListener  {


    // 定时轮询
//    @Scheduled(cron = "0 0 1 1 * ?")      //每月1号凌晨1点执行
    @Scheduled(cron = "0 0/10 * * * ?")    //每隔10分钟执行
//    @Scheduled(cron = "0/10 * * * * ?")     //10秒钟
    public synchronized void executeLapsedOrder() {


    }
}
