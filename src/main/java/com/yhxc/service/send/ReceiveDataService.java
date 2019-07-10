package com.yhxc.service.send;

import com.yhxc.entity.send.ReceiveData;
import net.sf.json.JSONArray;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Author: 刘俊涛
 * @Date: 2019/3/20 14:32
 */
public interface ReceiveDataService {
    public ReceiveData findAllList(String uuid);

    public ReceiveData findNnm1(String uuid);

    public ReceiveData findNnm2(String uuid);

    /**
     * 查看室内温度，室外湿度，室外温度，空调送风口温度
     * @param someDate
     * @return
     */
    JSONArray findTemps(String someDate,String uuid,int num);

    /**
     * 查看室内温度，空调送风口温度
     * @param someDate
     * @return
     */
    JSONArray findIndoorTemps(String someDate,String uuid,int num);

    /**
     * 查看室外温湿度
     * @param someDate
     * @return
     */
    JSONArray findOutdoorTempAndHumidity(String someDate,String uuid);

    /**
     * 查看室外湿度，室外温度，室内温度
     * @param someDate
     * @return
     */
    JSONArray findDoorTempAndHumidity(String someDate,String uuid);
    /**
     * 查看空调送风口温度
     * @param someDate
     * @return
     */
    JSONArray findTempsNum(String someDate,String uuid,int num);

    /**
     *查总有功率
     * @param uuid
     * @return
     */
    public Double findTotalpower(String uuid);

    /**
     *查询空调的状态（app
     * @param uuid
     * @param airnum(第几台空调)
     * @return
     */
    public JSONArray findAirApp(String uuid,String project_id,int airnum);




    /**
     *查询最新的三项电流
     * @param uuid
     * @return
     */
    public JSONArray findcurrent(String uuid, String date);



}
