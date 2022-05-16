package com.reins.centerIndex.globalIndex.utils;

import lombok.Data;

import java.util.HashMap;

@Data
public class FieldFilter {
    static HashMap<String, String> convert;
    static {
        convert = new HashMap<>();
        convert.put("gt", " > ");
        convert.put("lt", " < ");
        convert.put("gte", " >= ");
        convert.put("lte", " <= ");
        convert.put("eq", " = ");
        convert.put("neq", " != ");
    }
    public String getTransOp(){
        return convert.get(op);
    }
    String field;
    String op;
    String val1;
    String val2;
}