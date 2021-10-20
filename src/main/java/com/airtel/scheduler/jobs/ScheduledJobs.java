package com.airtel.scheduler.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Arun Singh
 */

public class ScheduledJobs implements Job {

    private Logger logger = LoggerFactory.getLogger(ScheduledJobs.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("Scheduled Job Started : {}", context.getJobDetail().getKey());
    }
}