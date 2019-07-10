package com.yhxc.repository.aftersale;

import com.yhxc.entity.aftersale.WarrantyRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface WarrantyRecordRepository extends JpaRepository<WarrantyRecord, String>, JpaSpecificationExecutor<WarrantyRecord> {

}
