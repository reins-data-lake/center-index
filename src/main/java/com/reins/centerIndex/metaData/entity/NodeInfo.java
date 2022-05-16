package com.reins.centerIndex.metaData.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "nodeInfo")
@Data
public class NodeInfo {
    String nodeName;
    String mongoUrl;
}
