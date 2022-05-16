package com.reins.centerIndex.globalIndex.dao;

import com.reins.centerIndex.globalIndex.entity.AnalyticRecord;
import com.reins.centerIndex.globalIndex.repository.AnalyticRecordRepository;
import com.reins.centerIndex.globalIndex.utils.FieldFilter;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SpringBootTest
public class AnalyticRecordDaoTest {
    @Autowired
    AnalyticRecordDao analyticRecordDao;
    @Autowired
    AnalyticRecordRepository analyticRecordRepository;
    @Test
    public void queryTest(){
        List<FieldFilter> filters = new ArrayList<>();
        FieldFilter fieldFilter = new FieldFilter();
        fieldFilter.setField("lat");
        fieldFilter.setOp("between");
        fieldFilter.setVal1("10.5");
        fieldFilter.setVal2("11.2");
        filters.add(fieldFilter);
        long start = System.currentTimeMillis( );
        for(int i=0;i<1000;i++){
            analyticRecordDao.searchRecord("test/mqtt", 691953, 7919536, filters );
        }
        long end = System.currentTimeMillis( );
        System.out.println(end - start);
//        AnalyticRecord  a = analyticRecordRepository.findByFiltersABC();
//        System.out.println(a);
    }
    @Test
    public void queryTest2(){
        List<FieldFilter> filters = new ArrayList<>();
        FieldFilter fieldFilter = new FieldFilter();
        fieldFilter.setField("TAXI_ID");
        fieldFilter.setOp("eq");
//        fieldFilter.setVal1("20000589");
        fieldFilter.setVal1("20000398");
        filters.add(fieldFilter);
        System.out.println(analyticRecordDao.searchRecord("taxi", 6948746, 7948746, filters ));
    }
}
