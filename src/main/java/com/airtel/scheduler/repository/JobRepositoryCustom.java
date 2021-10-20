package com.airtel.scheduler.repository;

import com.airtel.scheduler.model.ScheduledJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JobRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<ScheduledJob> findJobsByCategory(String category) {
        return mongoTemplate.find(Query.query(Criteria.where("jobGroup").is(category)), ScheduledJob.class);
    }
}
