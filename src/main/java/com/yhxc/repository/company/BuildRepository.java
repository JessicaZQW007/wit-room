package com.yhxc.repository.company;

import com.yhxc.entity.company.Build;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 建筑，房间 Repository
 * @author lwg
 *
 */
public interface BuildRepository extends JpaRepository<Build, String>,JpaSpecificationExecutor<Build>{


/*
根据企业ID删除所有的建筑
 */
@Modifying
@Transactional
    @Query(value = "delete from t_build where company_id=:companyId   ", nativeQuery = true)

    public void deletesRoom(@Param("companyId") String companyId);


    /*
    根据项目ID查询建筑
     */
    @Modifying
    @Transactional
    @Query(value = "SELECT * from  t_build where project_id=:projectId and pid='0'  ", nativeQuery = true)

    public List<Build> findBuilds(@Param("projectId") String projectId);


    /*
       根据父级查询房间号(单个建筑)
        */
    @Modifying
    @Transactional
    @Query(value = "SELECT * from  t_build where  pid=:pid  ", nativeQuery = true)

    public List<Build> findRooms(@Param("pid") String pid);



    /*
      根据父级查询房间号(多个建筑)
       */
    @Modifying
    @Transactional
    @Query(value = "SELECT * from  t_build where  find_in_set(pid,:pids)  ", nativeQuery = true)

    public List<Build> findRoomList(@Param("pids") String pids);



}
