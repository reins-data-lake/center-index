package com.reins.centerIndex.dto;

import com.reins.centerIndex.globalIndex.utils.FieldFilter;
import lombok.Data;

import java.util.List;

@Data
public class SearchTableFilterDTO {
    String topic;
    boolean realtime;
    long startTime;
    long endTime;
    List<FieldFilter> filters;
}
