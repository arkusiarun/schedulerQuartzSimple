package com.airtel.scheduler.model;

import com.airtel.scheduler.enums.Status;
import com.airtel.scheduler.utils.CommonUtils;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @author Arun Singh
 */

@Data
@Document(collection = "scheduled_tasks")
public class ScheduledTasks implements Serializable {

    @Id
    private String id;
    private String job_group;
    private String action;
    private Date scheduledTime;
    private Date archivalTime;
    private Boolean deleted;
    private Status status;
    private String comments;
    private Map<String, Object> meta;

    @Override
    public String toString() {
        return CommonUtils.getJson(this);
    }
}
