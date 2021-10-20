package com.airtel.scheduler.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Arun Singh
 */

@Data
@Document(collection = "scheduled_tasks")
public class JobGroup {

    @Id
    private String id;

    private String description;
}
