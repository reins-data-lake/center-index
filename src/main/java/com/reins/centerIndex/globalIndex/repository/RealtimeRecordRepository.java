package com.reins.centerIndex.globalIndex.repository;

import com.reins.centerIndex.globalIndex.entity.RealtimeRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RealtimeRecordRepository extends MongoRepository<RealtimeRecord, String> {
//    @Query("{'topicName' : {$regex:?0}, 'timeSlot' : {$gt : ?1, $lt: ?2}}")
//    List<RealtimeRecord> findRecordByTopicAndTimeSlotRange(String topicRegex, int startSlot, int endSlot);
}
