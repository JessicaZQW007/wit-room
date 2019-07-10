package com.yhxc.service.company;

import com.yhxc.entity.company.Dealer;
import net.sf.json.JSONArray;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @Author: 赵贺飞
 * @Date: 2018/11/23 11:38
 */

public interface DealerService {
    List<Dealer> findAll(Dealer dealer);



    /**
     * 地方——经销商 树形图
     * @param companyId
     */
    public JSONArray dealerTree(String companyId);

    /**
     * 新增或者修改
     * @param dealer
     */
    public void save(Dealer dealer);



    /**
     * 根据id查询
     * @param id
     */
    public Dealer findById(String id);

    /**根据id删除
     * @param id
     */
    public void delete(String id);

    /**  @param Page(页数)    PageSize（每页显示的条数） Dealer（实体类）
     * 根据条件分页查询经销商信息
     * @param dealer
     * @return
     */
    public Page<Dealer> listPage(Integer Page, Integer PageSize, Dealer dealer);
}
