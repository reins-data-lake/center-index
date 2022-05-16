package com.reins.centerIndex.dto;

import com.reins.centerIndex.metaData.entity.ParseConfig;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class SearchTableDTO {
    Set<String> nodes;
    ParseConfig parseConfig;
}
