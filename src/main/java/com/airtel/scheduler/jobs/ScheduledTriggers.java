package com.airtel.scheduler.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Arun Singh
 */
public class ScheduledTriggers implements Job {

    private Logger logger = LoggerFactory.getLogger(ScheduledTriggers.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("Scheduled Trigger Started : {}", context.getJobDetail().getKey());
    }
}
