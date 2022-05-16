package com.reins.centerIndex.globalIndex.dao;

import com.google.common.hash.BloomFilter;
import com.mongodb.client.DistinctIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.model.Filters;
import com.reins.centerIndex.bloomFilter.BloomFilterConvert;
import com.reins.centerIndex.globalIndex.entity.AnalyticRecord;
import com.reins.centerIndex.globalIndex.entity.RealtimeRecord;
import com.reins.centerIndex.globalIndex.repository.AnalyticRecordRepository;
import com.reins.centerIndex.globalIndex.utils.FieldFilter;
import com.reins.centerIndex.metaData.dao.ParseConfigDao;
import com.reins.centerIndex.metaData.entity.ParseConfig;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AnalyticRecordDao {
    @Autowired
    MongoClient mongoClient;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    ParseConfigDao parseConfigDao;
    @Autowired
    AnalyticRecordRepository analyticRecordRepository;
    public void addAnalyticRecord(String streamGroup,String node,  Integer timeSlot, Map<String, List<String>> filters){
        //base64 String
        List<AnalyticRecord> list= new ArrayList<>();
        for(Map.Entry<String, List<String>> entry: filters.entrySet()){

            int count = 0;
            for(String str: entry.getValue()){
                AnalyticRecord analyticRecord = new AnalyticRecord();
                analyticRecord.setTimeSlot(timeSlot);
                analyticRecord.setStreamGroup(streamGroup);
                analyticRecord.setNode(node);
                analyticRecord.setFilterName(entry.getKey());
                analyticRecord.setFilterIndex(count);
                analyticRecord.setBits(Base64.getDecoder().decode(str));
                list.add(analyticRecord);
                count += 1;
            }
        }
        analyticRecordRepository.saveAll(list);
    }


    public Set<String> searchRecord(String topic, int startSlot, int endSlot, List<FieldFilter> fieldFilters){

        Set<String> res = new HashSet<>();
        ParseConfig parseConfig = parseConfigDao.getParseConfigByTopic(topic);
        String topicPrefix = parseConfig.getTopicPrefix();
        String topicTail = topic.substring(topicPrefix.length());
        if(topicTail.length() >0 &&topicTail.charAt(0) == '/'){
            topicTail = topicTail.substring(1);
        }
        StringBuilder fields = new StringBuilder();
        fields.append("('topic'");
        for(FieldFilter fieldFilter : fieldFilters){
            fields.append(",'");
            fields.append(fieldFilter.getField());
            fields.append('\'');
        }
        fields.append(")");
        String sql =  "SELECT bit_or(bits) as bloom, node, filter_name, filter_index FROM analytic_record " +
                " where stream_group = ? and time_slot between ? and ? and filter_name in " +fields.toString()+
                " group by node, filter_name, filter_index ";


        List<Map<String, Object>> list =  jdbcTemplate.queryForList(sql, topicPrefix, startSlot, endSlot);

        Map<String, Map<String, List<byte[]>>> bloomFilterMap = new HashMap<>();
        // init index
        for(Map<String, Object> queryRes: list){
            String node = (String) queryRes.get("node");
            String filter_name = (String) queryRes.get("filter_name");
            int index = (int) queryRes.get("filter_index");
            byte[] bytes = (byte[]) queryRes.get("bloom");
            Map<String, List<byte[]>> fieldMap = null;
            if(!bloomFilterMap.containsKey(node)){
                fieldMap = new HashMap<>();
                bloomFilterMap.put(node, fieldMap);
            }else {
                fieldMap = bloomFilterMap.get(node);
            }
            List<byte[]> bloomFilters = null;
            if(fieldMap.containsKey(filter_name)){
                bloomFilters = fieldMap.get(filter_name);
            }else{
                bloomFilters = new ArrayList<>();
                fieldMap.put(filter_name, bloomFilters);
            }
            bloomFilters.add(index, bytes);
        }
        //Search
        for(Map.Entry<String, Map<String, List<byte[]>>> entry: bloomFilterMap.entrySet()){
            String node = entry.getKey();
            Map<String, List<byte[]>> fieldsBloomFilters = entry.getValue();
            if(!topicTail.equals("")){
                boolean topicCheck = BloomFilterConvert.stringCheck(topicTail, "/", fieldsBloomFilters.get("topic"));
                if(!topicCheck) continue;
            }
            boolean fieldCheck = true;
            for(FieldFilter fieldFilter: fieldFilters){
                Map<String,String> fieldInfo = parseConfig.getFields().get(fieldFilter.getField());
                String type = fieldInfo.get("type");
                List<byte[]> bloomFilters = fieldsBloomFilters.get(fieldFilter.getField());
                int val_start = 0;
                int val_end = 0;
                int range = 0;
                switch (type){
                    case "int":
                    case "float":
                        float max = Float.parseFloat(fieldInfo.get("max"));
                        float min = Float.parseFloat(fieldInfo.get("min"));
                        float granularity = Float.parseFloat(fieldInfo.get("granularity"));
                        float filter_val1 = Float.parseFloat(fieldFilter.getVal1());
                        float filter_val2 = Float.parseFloat(fieldFilter.getVal2());
                        val_start = (int)((Math.max(Math.min(max, filter_val1), min) - min) / granularity);
                        val_end = (int)((Math.max(Math.min(max, filter_val2), min) - min) / granularity);
                        fieldCheck &= BloomFilterConvert.rangeCheck(val_start, val_end, bloomFilters);
                        break;
                    case "double":
                        double maxd = Double.parseDouble(fieldInfo.get("max"));
                        double mind = Double.parseDouble(fieldInfo.get("min"));
                        double granularityd = Double.parseDouble(fieldInfo.get("granularity"));
                        double filter_val1d = Double.parseDouble(fieldFilter.getVal1());
                        double filter_val2d = Double.parseDouble(fieldFilter.getVal2());
                        val_start = (int)((Math.max(Math.min(maxd, filter_val1d), mind) - mind) / granularityd);
                        val_end = (int)((Math.max(Math.min(maxd, filter_val2d), mind) - mind) / granularityd);
                        fieldCheck &= BloomFilterConvert.rangeCheck(val_start, val_end, bloomFilters);
                        break;
                    case "str":
                        String val = fieldFilter.getVal1();
                        String reg = fieldInfo.getOrDefault("split", "/");
                        fieldCheck &= BloomFilterConvert.stringCheck(val, reg, bloomFilters);
                        break;
                }
                if(!fieldCheck) break;
            }
            if(fieldCheck){
                res.add(node);
            }
        }
        return res;
    }



}
