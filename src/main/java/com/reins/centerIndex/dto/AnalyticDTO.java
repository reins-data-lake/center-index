package com.reins.centerIndex.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AnalyticDTO {
    String node;
    String streamGroup;
    Integer timeslot;
    Map<String, List<String>> analyticData;
}
