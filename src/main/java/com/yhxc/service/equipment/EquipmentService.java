package com.yhxc.service.equipment;

import com.yhxc.entity.equipment.Equipment;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: 赵贺飞
 * @Date: 2018/10/31 14:52
 */
public interface EquipmentService {

    void delete(String id);

    void saveOrUpdate(Equipment equipment);

    Equipment findById(String id);

    void updState(String uuid);

    Equipment findByUUID(String uuid);

    long count(Equipment equipment);

    List<Equipment> findAll();

    List<Equipment> findAll(Equipment equipment);

    //返回到前端
    List<Equipment> listEquipmentResPage(Equipment equipment, Integer pageNum, Integer pageSize);


    List<Equipment> listEquipmentRes(Equipment equipment);


    /**
     * 根据房间号查询设备
     *
     * @param roomId
     */
    public JSONArray findByRoomId(String roomId);


    /**
     * 项目管理中，项目设备的信息，设备总数
     *
     * @param projectId
     */
    public JSONArray eqMessage(String projectId);


    public void deleted(String id);

    public void reduction(String id);


    /**
     * 根据项目id查询设数量
     *
     * @param projectId
     */
    public String eqcount(String projectId);

    /**
     * 设备分布统计(省)
     *
     * @param
     */

    List<Map> equipmentsheng(Equipment equipment);
    /**
     * 设备分布统计(市)
     *
     * @param
     */

    //
    List<Map> equipmentshi(Equipment equipment);
    /**
     * 设备分布统计(区)
     *
     * @param
     */

    List<Map> equipmentqu(Equipment equipment);




    /**
     * 查询项目中绑定设备 的运行状态
     */
    public JSONObject findEqStatus(String pId,String unitId);



    /**
     * 查询首页中 ，项目数量，设备数量，空调数量
     */
    public JSONObject findIndexData(String pId,String unitId);



    /**
     * 查询项目中设备运行状态（分页）
     *
     */
    public JSONObject pageList(String pId,String unitId, String projectType,String address, String pname, String allDate,String runStatus ,int pageNum, int pageSize);





    /**
     * 根据项目组查询uuid
     * @param
     * @return
     */
    public List<String> finduuidByGroup(String group_name);

    /**
     * 绑定定时任务
     * @param
     * @return
     */
    public void updateGroup_name( String group_name, String uuid);

    public JSONArray findByuuidGroup( String uuid);

    public JSONArray findweiBanding( String uuid);


    public JSONArray findUUidNameList(String ename);


    public void updateName(String newName,String name);

    /**
     * 根据ID修改互感器倍率和电压
     * @return
     */
    public void updateTransrate(int transrate,int voltage,String id);


    /**
     * 根据unitID机构ID查询未绑定的设备
     * @return
     */
    public JSONArray  findByUnitId(String unitId,String uuid);


    /**
     * 查询项目中设备运行状态
     *
     */
    public JSONArray  findrunStatus(String date,String receiveDate,String runStatus,String uuid);

}
