package com.reins.centerIndex.globalIndex.dao;

import com.reins.centerIndex.globalIndex.entity.RealtimeRecord;
import com.reins.centerIndex.globalIndex.repository.RealtimeRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class RealtimeRecordDao {
    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    RealtimeRecordRepository realtimeRecordRepository;

    public void insertRecord(String topicName, String node, int timeSlot){
        RealtimeRecord realtimeRecord = new RealtimeRecord();
        realtimeRecord.setNode(node);
        realtimeRecord.setTimeSlot(timeSlot);
        realtimeRecord.setTopicName(topicName);
        realtimeRecord.setCreateDate(new Date());
        realtimeRecordRepository.save(realtimeRecord);
    }

    public Set<String> searchRecord(String topic, int startSlot, int endSlot){
        String topicRegex = "^" + topic ;
        Criteria criteria = new Criteria().andOperator(
               Criteria.where("topicName").regex(topicRegex),
                Criteria.where("timeSlot").gte(startSlot).lte(endSlot)
        );
        Query query = new Query();
        query.addCriteria(criteria);

        return new HashSet<>(mongoTemplate.findDistinct(query, "node", RealtimeRecord.class, String.class));
//        List<RealtimeRecord> realtimeRecords = realtimeRecordRepository.findRecordByTopicAndTimeSlotRange(topicRegex, startSlot, endSlot);
//        Set<String> result = new HashSet<>();
//        for(RealtimeRecord record: realtimeRecords){
//            result.add(record.getNode());
//        }
//        return result;
    }
}
