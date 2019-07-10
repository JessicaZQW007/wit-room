package com.yhxc.repository.fault;

import com.yhxc.entity.fault.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**告警信息
 * @Author: 李文光
 * @Date: 2019/3/22 14:51
 */
public interface ReportRepository extends JpaRepository<Report, String>, JpaSpecificationExecutor<Report> {

//    查全部报警信息(按项目名称,设备id,时间区间)，分页
    @Query(value = "select re.id,pr.pname,re.uuid,eq.name,co.rank,co.code,co.message,co.manage,re.createtime,pr.address,re.air_id " +
        "FROM ex_fault_code co,p_project pr,t_equipment eq,ex_report re " +
        "WHERE re.code=co.code and pr.eq_id=eq.id and eq.uuid=re.uuid" +
        " and if(:pname !='', pr.pname like CONCAT('%',:pname,'%'), 1 = 1)" +
            "and if(:message !='', co.message LIKE CONCAT('%',:message,'%'), 1 = 1)" +
            "and if(:rank !='', co.rank =:rank, 1 = 1)" +
            "and if(:address !='', pr.address like CONCAT('%',:address,'%'), 1 = 1)" +
            "AND if(:startDate !='', re.createtime BETWEEN :startDate  and :endDate, 1 = 1)" +
            "ORDER BY re.createtime desc limit :pageNum,:pageSize",nativeQuery = true)
    public List<?> findAllReportMessagePage(@Param("pname")String pname,@Param("message")String message,@Param("rank")String rank,@Param("address")String address,@Param("startDate")String startDate,@Param("endDate")String endDate,@Param("pageNum")int pageNum,@Param("pageSize") int pageSize);




    //   查询设备的所有的告警信息
    @Query(value = "select re.id,pr.pname,re.uuid,eq.name,co.rank,co.code,co.message,co.manage,re.createtime,pr.address,re.air_id " +
            "FROM ex_fault_code co,p_project pr,t_equipment eq,ex_report re " +
            "WHERE re.code=co.code and pr.eq_id=eq.id and eq.uuid=re.uuid" +
            "and if(:uuid !='', re.uuid LIKE CONCAT('%',:uuid,'%'), 1 = 1)" +
            "ORDER BY re.createtime desc ",nativeQuery = true)
    public List<?> findAllReport(@Param("uuid")String uuid);




    //  查询设备某台空调的所有的告警信息
    @Query(value = "select re.id,pr.pname,re.uuid,eq.name,co.rank,co.code,co.message,co.manage,re.createtime,pr.address,re.air_id " +
            "FROM ex_fault_code co,p_project pr,t_equipment eq,ex_report re " +
            "WHERE re.code=co.code and pr.eq_id=eq.id and eq.uuid=re.uuid" +
            "and if(:uuid !='', re.uuid LIKE CONCAT('%',:uuid,'%'), 1 = 1)" +
            "and if(:air_id !='', re.air_id LIKE CONCAT('%',:air_id,'%'), 1 = 1)" +
            "ORDER BY re.createtime desc ",nativeQuery = true)
    public List<?> findAllReportAir(@Param("uuid")String uuid,@Param("air_id") String air_id);






    //    查全部报警信息(按项目名称,设备id,时间区间)
    @Query(value = "select COUNT(re.air_id)" +
            "FROM ex_fault_code co,p_project pr,t_equipment eq, ex_report re " +
            "WHERE re.code=co.code and pr.eq_id=eq.id and eq.uuid=re.uuid" +
            " and if(:pname !='', pr.pname like CONCAT('%',:pname,'%'), 1 = 1)" +
            "and if(:message !='', co.message LIKE CONCAT('%',:message,'%'), 1 = 1)" +
            "and if(:rank !='', co.rank =:rank, 1 = 1)" +
            "and if(:address !='', pr.address like CONCAT('%',:address,'%'), 1 = 1)" +
            "AND if(:startDate !='', re.createtime BETWEEN :startDate  and :endDate, 1 = 1)" +
            "ORDER BY re.createtime desc",nativeQuery = true)
    public int findAllReportMessage(@Param("pname")String pname,@Param("message")String message,@Param("rank")String rank,@Param("address")String address,@Param("startDate")String startDate,@Param("endDate")String endDate);

    //    查报警数量
    @Query(value = "select COUNT(re.air_id)" +
            "FROM ex_fault_code co,p_project pr,t_equipment eq, " +
            "(SELECT ex.air_id,ex.code,ex.uuid,ex.createtime FROM ex_report ex) re " +
            "WHERE re.code=co.code and pr.eq_id=eq.id and eq.uuid=re.uuid",nativeQuery = true)
    public int findNumber();

//    根据id删除报警信息
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM ex_report WHERE id=:Id",nativeQuery = true)
    public void deleteByAirIdAndCodeAndUuid(@Param("Id")String id);

    //    查报警信息
    @Query(value = "select pr.pname,re.uuid,eq.name,co.rank,co.code,co.message,co.manage,re.createtime,re.air_id " +
            "FROM ex_fault_code co,p_project pr,t_equipment eq,ex_report re " +
            "WHERE re.code=co.code and pr.eq_id=eq.id and eq.uuid=re.uuid" +
            " and re.id=:Id",nativeQuery = true)
    public List<?> findColumns(@Param("Id")String id);



////    按项目名称查
//@Query(value = "select pr.pname,re.uuid,eq.name,co.rank,co.code,co.message,co.manage,re.createtime " +
//        "FROM ex_fault_code co,p_project pr,t_equipment eq," +
//        " ( SELECT ex.air_id,ex.code,ex.uuid,MAX(createtime) createtime FROM ex_report ex GROUP BY ex.air_id,ex.cod,ex.uuid) re WHERE re.code=co.code and pr.eq_id=eq.id and eq.uuid=re.uuid and pr.pname like%:pname%",nativeQuery = true)
//public List<?> findAllReportByPname(@Param("pname")String pname);
//
//    //    按设备id查
//    @Query(value = "select pr.pname,re.uuid,eq.name,co.rank,co.code,co.message,co.manage,re.createtime " +
//            "FROM ex_fault_code co,p_project pr,t_equipment eq," +
//            "(SELECT ex.air_id,ex.code,ex.uuid,MAX(createtime) createtime FROM ex_report ex GROUP BY ex.air_id,ex.cod,ex.uuid) re WHERE re.code=co.code and pr.eq_id=eq.id and eq.uuid=re.uuid and re.uuid=:uuid",nativeQuery = true)
//    public List<?> findAllReportByUuid(@Param("uuid")String uuid);
//
//    //    按时间区间查
//    @Query(value = "select pr.pname,re.uuid,eq.name,co.rank,co.code,co.message,co.manage,re.createtime " +
//            "FROM ex_fault_code co,p_project pr,t_equipment eq," +
//            "(SELECT ex.air_id,ex.code,ex.uuid,MAX(createtime) createtime FROM ex_report ex GROUP BY ex.air_id,ex.cod,ex.uuid) re WHERE re.code=co.code and pr.eq_id=eq.id and eq.uuid=re.uuid and re.createtime >=:startDate AND re.createtime<=:endDate",nativeQuery = true)
//    public List<?> findAllReportByDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

}
