package com.reins.centerIndex.metaData.repository;

import com.reins.centerIndex.metaData.entity.ParseConfig;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ParseConfigRepository extends MongoRepository<ParseConfig, String> {
    public ParseConfig findByTopicPrefix(String topic);
}
