package com.yhxc.mapper.equipment;

import com.yhxc.entity.equipment.Equipment;
import java.util.List;
import java.util.Map;

/**
 * @Author: 赵贺飞
 * @Date: 2018/11/9 10:53
 */
public interface EquipmentMapper {

    List<Equipment> listEquipmentRes(Equipment equipment);

    //设备分布统计(省)
    List<Map> equipmentsheng();

    //设备分布统计(市)
    List<Map> equipmentshi();

    //设备分布统计(区)
    List<Map> equipmentqu();
}
