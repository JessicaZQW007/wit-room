package com.yhxc.mapper.inspection;

import com.yhxc.entity.aftersale.InspectionRecord;

import java.util.List;

/**
 * @Author: 赵贺飞
 * @Date: 2018/12/19 11:24
 */
public interface InspectionRecordMapper {
    List<InspectionRecord> listRecord(InspectionRecord inspectionRecord);

    List<InspectionRecord> listAllRecord(InspectionRecord inspectionRecord);
}
