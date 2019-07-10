package com.yhxc.service.aftersale;

import com.yhxc.entity.aftersale.InspectionRecord;
import com.yhxc.service.BaseService;

import java.util.List;

/**
 * @Author: 赵贺飞
 * @Date: 2018/12/19 10:43
 */

public interface InspectionRecordService extends BaseService<InspectionRecord> {

    List<InspectionRecord> listRecord(InspectionRecord inspectionRecord, Integer pageNum, Integer pageSize);

    List<InspectionRecord> listAllRecord(InspectionRecord inspectionRecord, Integer pageNum, Integer pageSize);

    int updProblem(String problem,String createTime, String id);

    void deleteByinspectionId(String inspectionId);
}
