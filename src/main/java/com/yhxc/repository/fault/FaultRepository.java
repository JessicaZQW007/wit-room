package com.yhxc.repository.fault;

import com.yhxc.entity.fault.Fault;
import com.yhxc.entity.fault.FaultCode;
import com.yhxc.entity.fault.FaultSet;
import com.yhxc.entity.fault.Report;
import com.yhxc.entity.send.ReceiveData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**告警信息
 * @Author: 刘俊涛
 * @Date: 2019/3/22 14:51
 */
public interface FaultRepository extends JpaRepository<Fault, String>, JpaSpecificationExecutor<Fault> {

//    分页查告警全部信息（可根据项目名，故障名，故障等级，地址，时间范围查）
   @Query(value = "select co.message,co.code,co.rank,eq.uuid,pr.pname,eq.name,pr.address,re.createtime,re.endtime,re.state,re.air_id,re.user_name\n" +
           "FROM\n" +
           "(SELECT fa.air_id,fa.code,fa.uuid,fa.ndate createtime,fa.end_date endtime,fa.fa_state state,fa.user_name from ex_fault fa\n" +
           "UNION ALL\n" +
           "SELECT r.air_id,r.code,r.uuid, createtime,NULL,r.state,NULL from ex_report r) re\n" +
           ", t_equipment eq, p_project pr ,ex_fault_code co\n" +
           "WHERE re.code=co.code AND eq.uuid=re.uuid and pr.eq_id=eq.id" +
           " and if(:pname !='', pr.pname like CONCAT('%',:pname,'%'), 1 = 1)" +
           "and if(:message !='', co.message LIKE CONCAT('%',:message,'%'), 1 = 1)" +
           "and if(:rank !='', co.rank =:rank, 1 = 1)" +
           "and if(:address !='', pr.address like CONCAT('%',:address,'%'), 1 = 1)" +
           "AND if(:startDate !='', re.createtime BETWEEN :startDate  and :endDate, 1 = 1)" +
           " ORDER BY re.createtime desc limit :pageNum,:pageSize",nativeQuery = true)
    List<?> findAllFaultMessagePage(@Param("pname")String pname,@Param("message")String message,@Param("rank")String rank,@Param("address")String address,@Param("startDate")String startDate,@Param("endDate")String endDate,@Param("pageNum")int pageNum,@Param("pageSize") int pageSize);




    //    分页带条件查全部（用于统计按当前条件查询的条数）
    @Query(value = "select count(re.air_id) \n" +
            "FROM \n" +
            "(SELECT fa.air_id,fa.code,fa.uuid,fa.ndate createtime,fa.fa_state state from ex_fault fa\n" +
            "UNION ALL\n" +
            "SELECT r.air_id,r.code,r.uuid,r.createtime,r.state from ex_report r) re \n" +
            ", t_equipment eq, p_project pr ,ex_fault_code co\n" +
            "\tWHERE re.code=co.code AND eq.uuid=re.uuid and pr.eq_id=eq.id\n" +
            " and if(:pname !='', pr.pname like CONCAT('%',:pname,'%'), 1 = 1)" +
            "and if(:message !='', co.message LIKE CONCAT('%',:message,'%'), 1 = 1)" +
            "and if(:rank !='', co.rank =:rank, 1 = 1)" +
            "and if(:address !='', pr.address like CONCAT('%',:address,'%'), 1 = 1)" +
            "AND if(:startDate !='', re.createtime BETWEEN :startDate  and :endDate, 1 = 1)" +
            " ORDER BY re.createtime desc",nativeQuery = true)
    int findAllFaultMessage(@Param("pname")String pname,@Param("message")String message,@Param("rank")String rank,@Param("address")String address,@Param("startDate")String startDate,@Param("endDate")String endDate);

 //    查看某月故障数量
    @Query(value = "   SELECT  de.sun,de.num " +
            " from  (   select  substr(substr(re.createtime,1,10),9,10) sun,count(re.createtime) num FROM\n" +
            "(SELECT fa.air_id,fa.code,fa.uuid,fa.ndate createtime,fa.fa_state state from ex_fault fa\n" +
            "UNION ALL\n" +
            "SELECT ex.air_id,ex.code,ex.uuid,ex.createtime, ex.state FROM ex_report ex) re , " +
            " t_equipment eq  , p_project pr ,ex_fault_code co,t_unit u " +
            "WHERE re.code=co.code AND eq.uuid=re.uuid and pr.eq_id=eq.id and eq.unit_id=u.id and pr.unit_id=u.id  " +
            "and substr(re.createtime,1,7)=:date  " +
            "AND if(:pId !='', u.p_id=:pId, 1=1 ) " +
            "AND if(:unitId !='', eq.unit_id=:unitId, 1=1 ) " +
            "GROUP BY substr(re.createtime,1,10)) de ORDER BY de.sun asc", nativeQuery = true)
    public List<?> findMonthCount(@Param("date") String date,@Param("pId") String pId,@Param("unitId") String unitId);







 //    查看某个设备的故障
 @Query(value = "select pr.pname,re.uuid,eq.name,co.rank,co.code,co.message,co.manage,re.createtime,pr.address,re.air_id " +
         "       FROM ex_fault_code co,p_project pr,t_equipment eq, " +
         "      (SELECT ex.air_id,ex.code,ex.uuid,ex.createtime FROM ex_report ex) re " +
         "        WHERE re.code=co.code and pr.eq_id=eq.id and eq.uuid=re.uuid " +
         "         and if(:uuid !='', re.uuid like CONCAT('%',:uuid,'%'), 1 = 1) " +
         "            and if(:air_id !='', re.air_id LIKE CONCAT('%',:air_id,'%'), 1 = 1) " +
         "            ORDER BY re.createtime desc  ",nativeQuery = true)
 List<?> findAllReprot(@Param("uuid")String uuid,@Param("air_id")String air_id);





 @Query(value = " select   s.* from s_receive_data s,t_equipment e where  e.uuid = s.uuid and s.id in (select max(id) from s_receive_data group by uuid ) and  substr(s.receive_date,1,10)=:date  ", nativeQuery = true)
 public List<ReceiveData> findAllList(@Param("date")String date);





 /**
  * 根据查询故障存不存在
  * @author lwg
  *
  */
 @Query(value = " SELECT id from ex_report where uuid=:uuid and air_id=:airId  and `code`=:code ", nativeQuery = true)

 public String findreport(@Param("uuid") String uuid,@Param("airId") String airId,@Param("code") String code);



 /**
  * 根据查询故障存不存在
  * @author lwg
  *
  */
 @Query(value = "SELECT id ,message  FROM ex_fault_code ", nativeQuery = true)

 public List<?> findFaultCode();







 /**
  * 更改时间
  * @param
  * @return
  */
 @Query(value = "update ex_report set createtime =:createtime WHERE id =:id", nativeQuery = true)
 @Transactional
 @Modifying
 public void updateTime(@Param("createtime") String createtime,@Param("id") Integer id);


}
