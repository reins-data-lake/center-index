package com.reins.centerIndex.service;

import com.reins.centerIndex.TPTree.Utils.TreeNodeUtil;
import com.reins.centerIndex.dto.AnalyticDTO;
import com.reins.centerIndex.dto.SearchTableDTO;
import com.reins.centerIndex.globalIndex.dao.AnalyticRecordDao;
import com.reins.centerIndex.globalIndex.dao.RealtimeRecordDao;

import com.reins.centerIndex.globalIndex.utils.FieldFilter;
import com.reins.centerIndex.metaData.dao.ParseConfigDao;
import com.reins.centerIndex.metaData.entity.ParseConfig;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatisticService {
    @Autowired
    RealtimeRecordDao realtimeRecordDao;
    @Autowired
    AnalyticRecordDao analyticRecordDao;
    @Autowired
    TreeNodeUtil treeNodeUtil;
    @Autowired
    ParseConfigDao parseConfigDao;
    public void realtimeUpdate(String topicName, String node, int timeSlot){
        //TODO: notify center
        realtimeRecordDao.insertRecord(topicName, node, timeSlot);
    }

    public SearchTableDTO searchTables(String topic, long startTime, long endTime, List<FieldFilter> filters, boolean realtime){
        //for topic test, we will search ^test$|/

        long startTimeSlot = treeNodeUtil.timeToSlot(startTime);
        long entTimeSlot =  treeNodeUtil.timeToSlot(endTime);

        SearchTableDTO searchTableDTO = new SearchTableDTO();
        Set<String> node_search_res;
        if(realtime) {
            //not using now time in simulation, because data timestamp is old, we just set now time to  endTime
            node_search_res= realtimeRecordDao.searchRecord(topic, (int) entTimeSlot, (int) entTimeSlot);
        }
        //no realtime, for
        else {node_search_res = new HashSet<>();}
        //find latest time slot data

        long startCountTime = System.nanoTime();
        //REALTIME only
//        Set<String> node_analytic = realtimeRecordDao.searchRecord(topic, (int) startTimeSlot, (int) entTimeSlot);

        //Rosseta

        Set<String> node_analytic = analyticRecordDao.searchRecord(topic, (int) startTimeSlot, (int) entTimeSlot, filters);
        node_search_res.addAll(node_analytic);
        searchTableDTO.setNodes(node_search_res);


        //broadcast
//        String[] node = new String[]{"e00","e01","e02", "e03","e10","e11","e12","e13","e20","e21","e22","e23"};
//        searchTableDTO.setNodes(Arrays.stream(node).collect(Collectors.toSet()));

        long endCountTime = System.nanoTime();
        System.out.println(endCountTime-startCountTime);
        searchTableDTO.setParseConfig(parseConfigDao.getParseConfigByTopic(topic));

        return searchTableDTO;
    }

    public void appendStatisticData(@NotNull List<AnalyticDTO> analyticDTOList){
        for(AnalyticDTO it: analyticDTOList){
            analyticRecordDao.addAnalyticRecord(it.getStreamGroup() ,it.getNode(), it.getTimeslot(), it.getAnalyticData());
        }
    }

}
