package com.yhxc.repository.fault;

import com.yhxc.entity.fault.FaultSet;
import com.yhxc.entity.send.ReceiveData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**故障预警
 *
 * @Author: 刘俊涛
 * @Date: 2019/3/22 14:51
 */
public interface FaultSetRepository extends JpaRepository<FaultSet, String>, JpaSpecificationExecutor<FaultSet> {

 /**
  * 根据uuid查询故障预警
  * @author lwg
  *
  */
 @Query(value = "SELECT * FROM ex_fault_set where uuid=:uuid ", nativeQuery = true)

 public FaultSet findByUuid(@Param("uuid") String uuid);

 /**
  * 查询所有最新上传数据
  * @author: 李文光
  */
 @Query(value="select e.receive_date,e.uuid,e.error_codee,e.ktccurrent1,e.ktccurrent2,e.sfktemp1,e.sfktemp2,e.hjtemp ,p.id from s_receive_data e,t_equipment q,p_project p where  e.uuid = q.uuid  and p.eq_id=q.id " +
         " and e.id in (select max(id) from s_receive_data where receive_date > DATE_ADD(NOW(), INTERVAL -10 MINUTE)    GROUP BY uuid ) \n" +
         " and substr(e.receive_date ,1,10)=:date ",nativeQuery=true)
 public List<?> findAllList(@Param("date")String date);





 /**
  * 查询额定电流
  * @author: 李文光
  */
 @Query(value="SELECT current FROM p_air a where a.project_id=:projectId and air_name=:airName ",nativeQuery=true)
 public String findcurrent(@Param("projectId")String projectId,@Param("airName")int airName);




}
