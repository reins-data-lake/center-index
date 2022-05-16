package com.reins.centerIndex.controller;

import com.reins.centerIndex.dto.AnalyticDTO;
import com.reins.centerIndex.dto.SearchTableDTO;
import com.reins.centerIndex.dto.SearchTableFilterDTO;
import com.reins.centerIndex.service.StatisticService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("statistic")
public class StatisticController {
    @Autowired
    StatisticService statisticService;
    @RequestMapping("/realtime")
    public void realtimeUpdate(@RequestBody @NotNull Map<String, String> body){
        String topic = body.get("tc");
        String node = body.get("nd");
        int timeSlot = Integer.parseInt(body.get("ts"));

        statisticService.realtimeUpdate(topic, node, timeSlot);

    }
    @RequestMapping("/searchTables")
    public SearchTableDTO searchTables(@RequestBody @NotNull SearchTableFilterDTO body){

        return statisticService.searchTables(body.getTopic(), body.getStartTime(), body.getEndTime(), body.getFilters(), body.isRealtime());
        //TODO:filters
    }
    @RequestMapping("/longTimeStatistic")
    public void uploadStatistic(@RequestBody @NotNull List<AnalyticDTO> body) {
        //TODO append data
        statisticService.appendStatisticData(body);
    }
}
