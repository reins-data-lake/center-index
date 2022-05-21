package com.reins.centerIndex.controller;

import com.reins.centerIndex.dto.NodesSearchDTO;
import com.reins.centerIndex.service.NodesSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class SeachNodes {
    @Autowired
    NodesSearchService nodesSearchService;
    @RequestMapping("searchNodes")
    public Set<String> searchNodes(@RequestBody NodesSearchDTO nodesSearchDTO){
        System.out.println(nodesSearchDTO);
        return nodesSearchService.findNodes(nodesSearchDTO);
    }
}
