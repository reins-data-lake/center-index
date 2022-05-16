package com.reins.centerIndex.metaData.dao;

import com.reins.centerIndex.metaData.entity.ParseConfig;
import com.reins.centerIndex.metaData.repository.ParseConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Arrays;

@Repository
public class ParseConfigDao {
    @Autowired
    ParseConfigRepository parseConfigRepository;
    public ParseConfig getParseConfigByTopic(String topic){
        String[] pathSplit = topic.split("/");
        for(int i=pathSplit.length;i>0;i--){
            String prefix = String.join("/",Arrays.copyOfRange(pathSplit, 0, i));
            ParseConfig parseConfig = parseConfigRepository.findByTopicPrefix(prefix);
            if(parseConfig != null) return parseConfig;
        }
        return null;
    }
    public ParseConfig updateParseConfig(ParseConfig parseConfig){
        return parseConfigRepository.save(parseConfig);
    }
}
