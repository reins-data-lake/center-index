package com.reins.centerIndex.TPTree;

import java.util.Map;

public class StaticValue{
    String bloomFilter;//KeyTag
    Map<String, Float> filterRangeMax;
    Map<String, Float> filterRangeMin;
    //filter range
    long updateTime;//代表最后一次更新的时间
}