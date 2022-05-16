package com.reins.centerIndex.service;

import com.reins.centerIndex.metaData.dao.ParseConfigDao;
import com.reins.centerIndex.metaData.entity.ParseConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MetaDataService {
    @Autowired
    ParseConfigDao parseConfigDao;

    public ParseConfig parseConfigUpdate(ParseConfig parseConfig){
        return parseConfigDao.updateParseConfig(parseConfig);
    }

    public ParseConfig parseConfigQuery(String topic){
        return parseConfigDao.getParseConfigByTopic(topic);
    }
}
