package com.reins.centerIndex.service;

import com.reins.centerIndex.dto.NodesSearchDTO;

import java.util.HashSet;
import java.util.Set;

public class NodesSearchService {
    public Set<String> findNodes(NodesSearchDTO nodesSearchDTO){
        Set<String> nodes = new HashSet<>();
        nodes.add("hive");
        nodes.add("mongo1");
        return nodes;
    }
}
