package com.reins.centerIndex.metaData.repository;

import com.reins.centerIndex.metaData.entity.NodeInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NodeInfoRepository extends MongoRepository<NodeInfo, String> {
    public NodeInfo findByNodeName(String nodeName);
}
