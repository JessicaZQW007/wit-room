package com.yhxc.repository.information;

import com.yhxc.entity.information.Instruct;
import com.yhxc.entity.information.jobGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InstructRepository extends JpaRepository<Instruct, Integer>, JpaSpecificationExecutor<Instruct> {



    @Query(value = "SELECT * FROM instruct WHERE password=:password",nativeQuery = true)
    public Instruct findByPassword(@Param("password")String password);




}
