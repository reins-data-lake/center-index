package com.reins.centerIndex.metaData.dao;

import com.reins.centerIndex.metaData.entity.NodeInfo;
import com.reins.centerIndex.metaData.repository.NodeInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class NodeInfoDao {
    @Autowired
    NodeInfoRepository nodeInfoRepository;

    public String getMongoUrl(String nodeName){
        NodeInfo node = nodeInfoRepository.findByNodeName(nodeName);
        return node.getMongoUrl();
    }
}
