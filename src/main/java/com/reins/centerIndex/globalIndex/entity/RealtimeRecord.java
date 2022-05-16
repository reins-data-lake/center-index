package com.reins.centerIndex.globalIndex.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "realtime")
@Data
public class RealtimeRecord {
    @Id
    String id;
    @Indexed(name = "topicName")
    String topicName;
    String node;
    @Indexed(name = "deleteDate", expireAfterSeconds = 200)
    Date createDate;
    int timeSlot;

}
