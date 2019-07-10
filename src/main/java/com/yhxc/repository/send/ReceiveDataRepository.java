package com.yhxc.repository.send;

import com.yhxc.entity.send.ReceiveData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Author: 刘俊涛
 * 数据接收Repository
 * @Date: 2019/3/20 12:53
 */
public interface ReceiveDataRepository extends JpaRepository<ReceiveData,String>,JpaSpecificationExecutor<ReceiveData> {

    /**
     *查全部
     * @param uuid
     * @return
     */
    @Query(value = "select id,uuid,voltageA,voltageB,voltageC,currentA,currentB,currentC,totalpower,hjtemp,sethjtemp,relaycontrolmark,swhumi,swtemp,NBsignal,NBIsmi,NBImei,board_data_report_interval,receive_date FROM s_receive_data WHERE id=(SELECT MAX(id) from s_receive_data WHERE uuid=?1)", nativeQuery = true)
    public ReceiveData findAllList(String uuid);

    /**
     *查空调 1
     * @param uuid
     * @return
     */
    @Query(value = "select sfktemp1,setsfktemp1,ktccurrent1,lmalarm1 FROM s_receive_data WHERE id=(SELECT MAX(id) from s_receive_data WHERE uuid=?1)", nativeQuery = true)
    public ReceiveData findNnm1(String uuid);

    /**
     *查空调 2
     * @param uuid
     * @return
     */
    @Query(value = "select sfktemp2,setsfktemp2,ktccurrent2,lmalarm2 FROM s_receive_data WHERE id=(SELECT MAX(id) from s_receive_data WHERE uuid=?1)", nativeQuery = true)
    public ReceiveData findNnm2(String uuid);

    /**
     * 查看室内温度，室外湿度，室外温度，空调一送风口温度
     * @param someDate
     * @return
     */
    @Query(value = "SELECT re.hjtemp,re.swtemp,re.swhumi,re.sfktemp1,substr(re.receive_date,12,8) receive_date FROM s_receive_data re WHERE substr(re.receive_date,1,10) =:receive_date AND uuid=:uuid", nativeQuery = true)
    List<?> findTempsNum1(@Param("receive_date") String someDate,@Param("uuid") String uuid);

    /**
     * 查看室内温度，室外湿度，室外温度，空调二送风口温度
     * @param someDate
     * @return
     */
    @Query(value = "SELECT re.hjtemp,re.swtemp,re.swhumi,re.sfktemp2,substr(re.receive_date,12,8) receive_date FROM s_receive_data re WHERE substr(re.receive_date,1,10) =:receive_date AND uuid=:uuid", nativeQuery = true)
    List<?> findTempsNum2(@Param("receive_date") String someDate,@Param("uuid") String uuid);



    /**
     * 查看室内温度，空调一送风口温度
     * @param someDate
     * @return
     */
    @Query(value = "SELECT re.hjtemp,re.sfktemp1,substr(re.receive_date,12,8) receive_date FROM s_receive_data re WHERE substr(re.receive_date,1,10) =:receive_date AND uuid=:uuid  ", nativeQuery = true)
    List<?> findIndoorTempNum1(@Param("receive_date") String someDate,@Param("uuid") String uuid);

    /**
     * 查看室内温度，空调二送风口温度
     * @param someDate
     * @return
     */
    @Query(value = "SELECT re.hjtemp,re.sfktemp2,substr(re.receive_date,12,8) receive_date FROM s_receive_data re WHERE substr(re.receive_date,1,10) =:receive_date AND uuid=:uuid", nativeQuery = true)
    List<?> findIndoorTempNum2(@Param("receive_date") String someDate,@Param("uuid") String uuid);

    /**
     * 查看室外湿度，室外温度
     * @param someDate
     * @return
     */
    @Query(value = "SELECT re.swtemp,re.swhumi,substr(re.receive_date,12,8) receive_date FROM s_receive_data re WHERE substr(re.receive_date,1,10) =:receive_date AND uuid=:uuid", nativeQuery = true)
    List<?> findOutdoorTempAndHumidity(@Param("receive_date") String someDate,@Param("uuid") String uuid);

    /**
     * 查看室外湿度，室外温度，室内温度
     * @param someDate
     * @return
     */
    @Query(value = "SELECT re.hjtemp,re.swtemp,re.swhumi,substr(re.receive_date,12,8) receive_date FROM s_receive_data re WHERE substr(re.receive_date,1,10) =:receive_date AND uuid=:uuid", nativeQuery = true)
    List<?> findDoorTempAndHumidity(@Param("receive_date") String someDate,@Param("uuid") String uuid);

    /**
     * 查看空调二送风口温度
     * @param someDate
     * @return
     */
    @Query(value = "SELECT re.sfktemp1,substr(re.receive_date,12,8) receive_date FROM s_receive_data re WHERE substr(re.receive_date,1,10) =:receive_date AND uuid=:uuid", nativeQuery = true)
    List<?> findTempNum1(@Param("receive_date") String someDate,@Param("uuid") String uuid);

    /**
     * 查看空调二送风口温度
     * @param someDate
     * @return
     */
    @Query(value = "SELECT re.sfktemp2,substr(re.receive_date,12,8) receive_date FROM s_receive_data re WHERE substr(re.receive_date,1,10) =:receive_date AND uuid=:uuid", nativeQuery = true)
    List<?> findTempNum2(@Param("receive_date") String someDate,@Param("uuid") String uuid);


    /**
     *查总有功率
     * @param uuid
     * @return
     */
    @Query(value = " select totalpower FROM s_receive_data WHERE id=(SELECT MAX(id) from s_receive_data WHERE uuid=?1)", nativeQuery = true)
    public String findTotalpower(String uuid);



    /**0
     *查询空调1的状态（app）
     * @param uuid
     * @return
     */
    @Query(value = " SELECT s.id,s.sfktemp1,s.kt_run_model1,s.kt_set_fs1,s.kt_set_fx1 ,s.kt_switch1,s.ktccurrent1,s.kt_set_temp1, s.sys_lock,s.xin_fen,s.board_data_report_interval,\n" +
            "s.hjtemp,s.swhumi,s.swtemp,ifnull(b.img,''),b.brand,b.model,s.currenta,s.currentb,s.currentc,s.voltagea,s.voltageb,s.voltagec,s.relaycontrolmark ,s.run_rule,s.hight_temp,s.lower_temp from s_receive_data  s, p_air p,p_ari_brand b WHERE " +
            " p.brand_id=b.id   and p.project_id=:project_id    and p.air_name=1 " +
            " and  s.id=(SELECT MAX(id) from s_receive_data WHERE uuid=:uuid)", nativeQuery = true)
    public List<?> findAir1App(@Param("uuid")String uuid,@Param("project_id")String project_id);




    /**
     *查询空调2的状态（app）
     * @param uuid
     * @return
     */
    @Query(value = " SELECT s.id,s.sfktemp2,s.kt_run_model2,s.kt_set_fs2,s.kt_set_fx2 ,s.kt_switch2,s.ktccurrent2,s.kt_set_temp2, s.sys_lock,s.xin_fen,s.board_data_report_interval,\n" +
            "s.hjtemp,s.swhumi,s.swtemp,ifnull(b.img,''),b.brand,b.model,s.currenta,s.currentb,s.currentc,s.voltagea,s.voltageb,s.voltagec,s.relaycontrolmark,s.run_rule,s.hight_temp,s.lower_temp from s_receive_data  s, p_air p,p_ari_brand b WHERE " +
            " p.brand_id=b.id   and p.project_id=:project_id    and p.air_name=2 " +
            " and  s.id=(SELECT MAX(id) from s_receive_data WHERE uuid=:uuid)", nativeQuery = true)
    public List<?> findAir2App(@Param("uuid")String uuid,@Param("project_id")String project_id);


    /**
     *查询最新的三项电流
     * @param uuid
     * @return
     */
    @Query(value = "select s.currenta,s.currentb,s.currentc,s.receive_date FROM s_receive_data s WHERE substr(s.receive_date,1,10)=:date and s.uuid=:uuid ORDER BY s.receive_date asc ", nativeQuery = true)
    public List<?> findcurrent(@Param("uuid")String uuid,@Param("date")String date);



    /**
     *查询设备当天的温度湿度信息
     * @param uuid
     * @return
     */
    @Query(value = "select swhumi,swtemp,hjtemp,sfktemp1,sfktemp2 from s_receive_data where uuid=:uuid  and receive_date LIKE CONCAT('%',:receiveDate,'%') ", nativeQuery = true)
    public List<?> findHour(@Param("uuid")String uuid,@Param("receiveDate")String receiveDate);

    /**
     *查询设备当月的温度湿度信息
     * @param uuid
     * @return
     */
    @Query(value = "select swhumi,swtemp,hjtemp,sfktemp1,sfktemp2 from s_receive_data where uuid=:uuid  and receive_date LIKE CONCAT('%',:receiveDate,'%') ", nativeQuery = true)
    public List<?> findMonth(@Param("uuid")String uuid,@Param("receiveDate")String receiveDate);



    /**
     *查询设备制冷运行时间 空调1
     * @return
     */
    @Query(value = "SELECT i.uuid,i.receive_date, ( SELECT CASE WHEN(i.kt_run_model1=\"制冷\" and i.ktccurrent1>1  ) THEN 2 ELSE 0 END   )AS Gender  from s_receive_data i,p_project p,t_equipment e where e.uuid=i.uuid and p.eq_id=e.id and i.receive_date LIKE CONCAT('%',:receiveDate,'%') and p.pname=:pname  order by i.uuid,i.receive_date ", nativeQuery = true)
    public List<?> findCoolTime1(@Param("receiveDate")String receiveDate,@Param("pname")String pname);




    /**
     *查询设备制冷运行时间 空调2
     * @return
     */
    @Query(value = "SELECT i.uuid,i.receive_date, ( SELECT CASE WHEN(i.kt_run_model2=\"制冷\" and i.ktccurrent2>1  ) THEN 2 ELSE 0 END   )AS Gender  from s_receive_data i,p_project p,t_equipment e where e.uuid=i.uuid and p.eq_id=e.id and i.receive_date LIKE CONCAT('%',:receiveDate,'%') and p.pname=:pname  order by i.uuid,i.receive_date ", nativeQuery = true)
    public List<?> findCoolTime2(@Param("receiveDate")String receiveDate,@Param("pname")String pname);



    /**
     *查当前每个小时最大的总有功率
     * @param uuid
     * @return
     */
    @Query(value = " select MAX(totalpower) FROM s_receive_data   where uuid=:uuid and receive_date LIKE CONCAT('%',:receiveDate,'%') ", nativeQuery = true)
    public String findTotalpowerHourMax(@Param("uuid")String uuid,@Param("receiveDate")String receiveDate);

}
