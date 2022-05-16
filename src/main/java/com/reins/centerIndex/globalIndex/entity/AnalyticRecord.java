package com.reins.centerIndex.globalIndex.entity;

import lombok.Data;
import org.bson.types.Binary;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Map;

//@Document(collection = "analytic")
//@Data
//public class AnalyticRecord {
//    @Id
//    String id;
//    String node;
//    @Indexed
//    Integer timeSlot;
//    Map<String, Binary> filters;
//}
@Data
@Entity
public class AnalyticRecord {
    @GeneratedValue()
    @Id
    int idBloom;
    String node;
    String streamGroup;
    int timeSlot;
    String filterName;
    int filterIndex;
    byte[] bits;
}