package com.airtel.scheduler.service;

import com.airtel.scheduler.model.ScheduledJob;

public interface SchedulerService {

    void initSchedulers();

    void scheduleNewJob(ScheduledJob scheduledJob);

    void updateScheduledJob(ScheduledJob scheduledJob);

    boolean unScheduleJob(String jobName);

    boolean deleteScheduledJob(ScheduledJob scheduledJob);

    boolean pauseScheduledJob(ScheduledJob scheduledJob);

    boolean resumeJob(ScheduledJob scheduledJob);

    boolean startScheduledJob(ScheduledJob scheduledJob);
}
