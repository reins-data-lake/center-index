package com.reins.centerIndex.globalIndex.repository;


import com.reins.centerIndex.globalIndex.entity.AnalyticRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AnalyticRecordRepository extends JpaRepository<AnalyticRecord,Integer> {

}
