package com.airtel.scheduler.repository;

import com.airtel.scheduler.model.ScheduledTasks;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Arun Singh
 */

@Repository
public interface ScheduledTaskRepository extends MongoRepository<ScheduledTasks, String> {
}