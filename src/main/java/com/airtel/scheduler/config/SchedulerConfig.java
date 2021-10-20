package com.airtel.scheduler.config;

import com.airtel.scheduler.service.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class SchedulerConfig {

    @Autowired
    private SchedulerService schedulerService;

    @Autowired
    private ApplicationContext applicationContext;

    private QuartzProperties quartzProperties = new QuartzProperties();

    @PostConstruct
    public void initScheduling() {
        this.schedulerService.initSchedulers();
    }

}
