package com.airtel.scheduler.repository;

import com.airtel.scheduler.model.JobGroup;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Arun Singh
 */

@Repository
public interface JobGroupRepository extends MongoRepository<JobGroup, String> {
}
