package com.reins.centerIndex.metaData.entity;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

// 暂时一个流只会存一个表里面，如果有不同的parse需要的话，可以通过worker将数据读入然后处理完重新发布到新的topic中
@Document(collection = "metaData")
@Data
public class ParseConfig {
    @Id
    String id;
    String topicPrefix;
    String collectionName;
    Integer ttl;
    String payloadType;
    String timeField;
    Map<String, Map<String, String>> fields;
    Integer batchWindow;

}
