package com.yhxc.service.unit;


import com.yhxc.entity.unit.Unit;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Author: 陈霖炎
 * @Date: 2019/7/5 10:30
 */
public interface UnitService {


    void save(Unit unit);
    void delete(String id);



    //查询平台单位或者项目机构的全部信息 分页
    public List<Unit> findAllListPage(String type, String name, int pageNum, int pageSize );

    //查询平台单位或者项目机构的全部信息 分页
    public int findAllListCount(String type, String name);

    //根据ID查询数据
    public Unit findOneById(String id);

    //查询单位名称是否存在
    public int findByName(String type, String name);

    //修改状态
    public void updateState(int state,String id);


    //查询所有的平台或者机构 ( 状态(state 0)为正常的)
    public JSONArray findNameList(String type);


    //平台单位和机构下拉联动 ( 状态(state 0)为正常的)
    public JSONArray findNameAll();

    //根据名称和类型查询
    public Unit findByNameType(String name,String type);





}
