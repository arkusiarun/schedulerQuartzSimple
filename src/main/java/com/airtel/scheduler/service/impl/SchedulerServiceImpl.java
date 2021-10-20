package com.airtel.scheduler.service.impl;

import com.airtel.scheduler.components.ScheduledJobCreator;
import com.airtel.scheduler.model.ScheduledJob;
import com.airtel.scheduler.repository.JobRepositoryCustom;
import com.airtel.scheduler.repository.ScheduledJobRepository;
import com.airtel.scheduler.service.SchedulerService;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Service
public class SchedulerServiceImpl implements SchedulerService {

    private Logger logger = LoggerFactory.getLogger(SchedulerServiceImpl.class);

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Autowired
    private ScheduledJobCreator scheduledJobCreator;

    @Autowired
    private JobRepositoryCustom jobRepositoryCustom;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private ScheduledJobRepository scheduledJobRepository;

    @Override
    public void initSchedulers() {
        List<ScheduledJob> jobsList = this.jobRepositoryCustom.findJobsByCategory("SCHEDULED_TRIGGER");
        if (jobsList != null) {
            Scheduler scheduler = this.schedulerFactoryBean.getScheduler();
            jobsList.forEach(job -> {
                try {
                    JobDetail jobDetail = JobBuilder.newJob((Class<? extends Job>) Class.forName(job.getJobClass()))
                            .withIdentity(job.getJobName(), job.getJobGroup()).build();
                    if (!scheduler.checkExists(jobDetail.getKey())) {
                        Trigger trigger;
                        jobDetail = this.scheduledJobCreator.createJob((Class<? extends Job>) Class.forName(job.getJobClass()),
                                false, context, job.getJobName(), job.getJobGroup());

                        if (!StringUtils.isEmpty(job.getCronExpression()) && CronExpression.isValidExpression(job.getCronExpression())) {
                            trigger = this.scheduledJobCreator.createCronTrigger(job.getJobName(), new Date(),
                                    job.getCronExpression(), SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
                        } else {
                            trigger = this.scheduledJobCreator.createSimpleTrigger(job.getJobName(), new Date(),
                                    job.getRepeatTime(), SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
                        }
                        scheduler.scheduleJob(jobDetail, trigger);
                    }
                } catch (ClassNotFoundException e) {
                    logger.error("Job Class Not Found : {}", job.getJobClass(), e);
                } catch (SchedulerException e) {
                    logger.error(e.getMessage(), e);
                }
            });
        }
    }

    @Override
    public void scheduleNewJob(ScheduledJob scheduledJob) {
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();

            JobDetail jobDetail = JobBuilder.newJob((Class<? extends Job>) Class.forName(scheduledJob.getJobClass()))
                    .withIdentity(scheduledJob.getJobName(), scheduledJob.getJobGroup()).build();
            if (!scheduler.checkExists(jobDetail.getKey())) {

                jobDetail = this.scheduledJobCreator.createJob((Class<? extends Job>) Class.forName(scheduledJob.getJobClass()),
                        false, context, scheduledJob.getJobName(), scheduledJob.getJobGroup());

                Trigger trigger;
                if (!StringUtils.isEmpty(scheduledJob.getCronExpression())) {
                    trigger = scheduledJobCreator.createCronTrigger(scheduledJob.getJobName(), new Date(), scheduledJob.getCronExpression(),
                            SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
                } else {
                    trigger = scheduledJobCreator.createSimpleTrigger(scheduledJob.getJobName(), new Date(), scheduledJob.getRepeatTime(),
                            SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
                }

                scheduler.scheduleJob(jobDetail, trigger);
                this.scheduledJobRepository.save(scheduledJob);
            } else {
                logger.error("Job Already Exists");
            }
        } catch (ClassNotFoundException e) {
            logger.error("Class Not Found : {}", scheduledJob.getJobClass(), e);
        } catch (SchedulerException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void updateScheduledJob(ScheduledJob scheduledJob) {
        Trigger newTrigger;
        if (!StringUtils.isEmpty(scheduledJob.getCronExpression())) {
            newTrigger = scheduledJobCreator.createCronTrigger(scheduledJob.getJobName(), new Date(), scheduledJob.getCronExpression(),
                    SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
        } else {
            newTrigger = scheduledJobCreator.createSimpleTrigger(scheduledJob.getJobName(), new Date(), scheduledJob.getRepeatTime(),
                    SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
        }
        try {
            this.schedulerFactoryBean.getScheduler().rescheduleJob(TriggerKey.triggerKey(scheduledJob.getJobName()), newTrigger);
            this.scheduledJobRepository.save(scheduledJob);
        } catch (SchedulerException e) {
            logger.error(e.getMessage(), e);
        }
    }


    @Override
    public boolean unScheduleJob(String jobName) {
        try {
            return this.schedulerFactoryBean.getScheduler().unscheduleJob(new TriggerKey(jobName));
        } catch (SchedulerException e) {
            logger.error("Failed to un-schedule job : {}", jobName, e);
            return false;
        }
    }

    @Override
    public boolean deleteScheduledJob(ScheduledJob scheduledJob) {
        try {
            return this.schedulerFactoryBean.getScheduler().deleteJob(new JobKey(scheduledJob.getJobName(), scheduledJob.getJobGroup()));
        } catch (SchedulerException e) {
            logger.error("Failed to delete job : {}", scheduledJob.getJobName(), e);
            return false;
        }
    }

    @Override
    public boolean pauseScheduledJob(ScheduledJob scheduledJob) {
        try {
            this.schedulerFactoryBean.getScheduler().pauseJob(new JobKey(scheduledJob.getJobName(), scheduledJob.getJobGroup()));
            return true;
        } catch (SchedulerException e) {
            logger.error("Failed to pause job : {}", scheduledJob.getJobName(), e);
            return false;
        }
    }

    @Override
    public boolean resumeJob(ScheduledJob scheduledJob) {
        try {
            this.schedulerFactoryBean.getScheduler().resumeJob(new JobKey(scheduledJob.getJobName(), scheduledJob.getJobGroup()));
            return true;
        } catch (SchedulerException e) {
            logger.error("Failed to resume job : {}", scheduledJob.getJobName(), e);
            return false;
        }
    }

    @Override
    public boolean startScheduledJob(ScheduledJob scheduledJob) {
        try {
            this.schedulerFactoryBean.getScheduler().triggerJob(new JobKey(scheduledJob.getJobName(), scheduledJob.getJobGroup()));
            return true;
        } catch (SchedulerException e) {
            logger.error("Failed to start job : {}", scheduledJob.getJobName(), e);
            return false;
        }
    }
}