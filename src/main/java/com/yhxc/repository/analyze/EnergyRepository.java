package com.yhxc.repository.analyze;


import com.yhxc.entity.count.MonthCountElectric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 能耗统计 Repository接口
 * @author lwg
 *
 */

public interface EnergyRepository extends JpaRepository<MonthCountElectric, String>,JpaSpecificationExecutor<MonthCountElectric> {

    /**
     * 项目的用电量（根据月份查询）
     * @author lwg
     *
     */
    @Modifying
    @Transactional
    @Query(value = "SELECT substr(substr(i.date,1,10),9,10) ,sum(i.elequantity) from c_month_count_electric i ,t_equipment e,t_project p where i.uuid=e.uuid and e.project_id=p.id \n" +
            " and p.pname=:pname and substr(i.date,1,7)=:date  and e.deleted=0   GROUP BY substr(i.date,1,10)", nativeQuery = true)

    public List<?> findBydate(@Param("pname") String pname,@Param("date") String date);


    /**
     * 项目的用电量（根据时间范围查询）
     * @author lwg
     *
     */
    @Modifying
    @Transactional
    @Query(value = "SELECT substr(i.date,1,10) ,sum(i.elequantity) from c_month_count_electric i ,t_equipment e,t_project p where i.uuid=e.uuid and e.project_id=p.id \n" +
            " and p.pname=:pname and  substr(i.date,1,10) BETWEEN :stateDate and :endDate    and e.deleted=0   GROUP BY substr(i.date,1,10)", nativeQuery = true)

    public List<?> findBydateScope(@Param("pname") String pname,@Param("stateDate") String stateDate,@Param("endDate") String endDate);



    /**
     * 项目的用电量（根据年查询）
     * @author lwg
     *
     */
    @Modifying
    @Transactional
    @Query(value = "SELECT substr(substr(i.date,1,7),6,7) ,sum(i.elequantity) from c_year_count_electric i ,t_equipment e,t_project p where i.uuid=e.uuid and e.project_id=p.id \n" +
            "and p.pname=:pname and  substr(i.date,1,4)=:date     and e.deleted=0   GROUP BY substr(i.date,1,7)", nativeQuery = true)

    public List<?> findBydateYear(@Param("pname") String pname,@Param("date") String date);


    /**
     *  所有建筑物的用电量（根据年查询）
     * @author lwg
     *
     */
    @Modifying
    @Transactional
    @Query(value = "SELECT b.`name` ,sum(i.elequantity) from c_year_count_electric i ,t_equipment e,t_project p ,t_build b  where i.uuid=e.uuid and e.project_id=p.id  and b.id=e.build_id\n" +
            "and p.pname=:pname     and  substr(i.date,1,4)=:date     and e.deleted=0   GROUP BY e.build_id", nativeQuery = true)

    public List<?> findbuildYear(@Param("pname") String pname,@Param("date") String date);

    /**
     *  所有建筑物的用电量（根据月查询）
     * @author lwg
     *
     */
    @Modifying
    @Transactional
    @Query(value = "SELECT b.`name` ,sum(i.elequantity) from c_month_count_electric i ,t_equipment e,t_project p ,t_build b  where i.uuid=e.uuid and e.project_id=p.id  and b.id=e.build_id\n" +
            "and p.pname=:pname     and  substr(i.date,1,7)=:date     and e.deleted=0   GROUP BY e.build_id", nativeQuery = true)

    public List<?> findbuildmonth(@Param("pname") String pname,@Param("date") String date);


    /**
     *  建筑物的用电量（根据时间范围查询，建筑物查询）
     * @author lwg
     *
     */
    @Modifying
    @Transactional
    @Query(value = "SELECT b.`name` ,sum(i.elequantity) from c_month_count_electric i ,t_equipment e,t_project p ,t_build b  where i.uuid=e.uuid and e.project_id=p.id  and b.id=e.build_id\n" +
            " and p.pname=:pname   " +
            " and if(:stateDate !='', substr(i.date,1,10) BETWEEN :stateDate and :endDate, 1 = 1)" +
            " and if(:uuid !='',i.uuid=:uuid, 1 = 1) " +
            " and if(:buildIds !='',  find_in_set(e.build_id,:buildIds ), 1 = 1)" +
            "  and e.deleted=0   GROUP BY e.build_id", nativeQuery = true)
    public List<?> findbuildScope(@Param("pname") String pname,@Param("stateDate") String stateDate,@Param("endDate") String endDate,@Param("buildIds") String buildIds,@Param("uuid") String uuid);


    /**
     *  所有房间号的用电量（根据时间范围查询，房间号查询）
     * @author lwg
     *
     */
    @Modifying
    @Transactional
    @Query(value = "SELECT b.`name` ,sum(i.elequantity) from c_month_count_electric  i ,t_equipment e,t_project p ,t_build b  where i.uuid=e.uuid and e.project_id=p.id  and b.id=e.build_num\n" +
            " and p.pname=:pname   " +
            " and if(:stateDate !='', substr(i.date,1,10) BETWEEN :stateDate and :endDate, 1 = 1)" +
            " and if(:build_nums !='', find_in_set(e.build_num,:build_num ), 1 = 1)" +
            " and if(:uuid !='',i.uuid=:uuid, 1 = 1) " +
            "  and e.deleted=0   GROUP BY e.build_num", nativeQuery = true)
    public List<?> findRoomScope(@Param("pname") String pname,@Param("stateDate") String stateDate,@Param("endDate") String endDate,@Param("build_nums") String build_nums,@Param("uuid") String uuid);





    /**
     *  建筑物的用电量（根据时间查询，建筑物查询）同比比较
     * @author lwg
     *
     */
    @Modifying
    @Transactional
    @Query(value = "SELECT b.`name` ,sum(i.elequantity) ,e.build_id from c_month_count_electric i ,t_equipment e,t_project p ,t_build b  where i.uuid=e.uuid and e.project_id=p.id  and b.id=e.build_id\n" +
            "and p.pname=:pname   " +
            "and substr(i.date,1,7) =:date " +
            "and if(:buildIds !='', find_in_set(e.build_id,:buildIds ), 1 = 1)" +
            "  and e.deleted=0   GROUP BY e.build_id", nativeQuery = true)
    public List<?> findbuildCompare(@Param("pname") String pname,@Param("date") String date,@Param("buildIds") String buildIds);



    /**
     *  所有房间号的用电量（根据时间查询，房间号查询）同比比较  （月）
     * @author lwg
     *
     */
    @Modifying
    @Transactional
    @Query(value = "SELECT b.`name` ,sum(i.elequantity),(select name from t_build where id=b.pid),e.build_num from c_month_count_electric  i ,t_equipment e,t_project p ,t_build b  where i.uuid=e.uuid and e.project_id=p.id  and b.id=e.build_num\n" +
            "and p.pname=:pname   " +
            "and substr(i.date,1,7) =:date " +
            " and if(:build_nums !='', find_in_set(e.build_num,:build_nums ), 1 = 1)" +
            "  and e.deleted=0   GROUP BY e.build_num", nativeQuery = true)
    public List<?> findRoomCompare(@Param("pname") String pname,@Param("date") String date,@Param("build_nums") String build_nums);




    /**
     *  建筑物的用电量（根据时间查询，建筑物查询）同比比较
     * @author lwg
     *
     */
    @Modifying
    @Transactional
    @Query(value = "SELECT b.`name` ,sum(i.elequantity),e.build_id from c_month_count_electric i ,t_equipment e,t_project p ,t_build b  where i.uuid=e.uuid and e.project_id=p.id  and b.id=e.build_id\n" +
            "and p.pname=:pname   " +
            "and    QUARTER(i.date)=:quarter  and year(i.date)=:year " +
            "and if(:buildIds !='', find_in_set(e.build_id,:buildIds ), 1 = 1)" +
            "  and e.deleted=0   GROUP BY e.build_id", nativeQuery = true)
    public List<?> findbuildCompareQuarter(@Param("pname") String pname,@Param("buildIds") String buildIds,@Param("quarter") String quarter,@Param("year") String year);



    /**
     *  所有房间号的用电量（根据时间查询，房间号查询）(季度)
     * @author lwg
     *
     */
    @Modifying
    @Transactional
    @Query(value = "SELECT b.`name` ,sum(i.elequantity),(select name from t_build where id=b.pid),e.build_num from c_month_count_electric  i ,t_equipment e,t_project p ,t_build b  where i.uuid=e.uuid and e.project_id=p.id  and b.id=e.build_num \n" +
            "and p.pname=:pname   " +
            "and    QUARTER(i.date)=:quarter  and year(i.date)=:year " +
            " and if(:build_nums !='', find_in_set(e.build_num,:build_nums ), 1 = 1)" +
            "  and e.deleted=0   GROUP BY e.build_num", nativeQuery = true)
    public List<?> findRoomCompareQuarter(@Param("pname") String pname,@Param("build_nums") String build_nums,@Param("quarter") String quarter,@Param("year") String year);

}
