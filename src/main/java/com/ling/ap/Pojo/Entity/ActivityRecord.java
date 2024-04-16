package com.ling.ap.Pojo.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityRecord {
    String recordId;
    String oldsterId;
    String activityName;
    String activityContent;
    String setTime;
    Date executionTime;
}
