package com.reins.centerIndex.controller;

import com.reins.centerIndex.metaData.entity.ParseConfig;
import com.reins.centerIndex.service.MetaDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("metaData")
public class MetaDataController {
    @Autowired
    MetaDataService metaDataService;
    @RequestMapping("/register")
    public ParseConfig register(@RequestBody ParseConfig parseConfig){
        return metaDataService.parseConfigUpdate(parseConfig);
    }
    @RequestMapping("/parseConfig")
    public ParseConfig queryParseConfig(@RequestParam String topic){
        return  metaDataService.parseConfigQuery(topic);
    }
}
