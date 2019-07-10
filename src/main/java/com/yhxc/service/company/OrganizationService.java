package com.yhxc.service.company;

import com.yhxc.entity.company.Organization;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @Author: 赵贺飞
 * @Date: 2018/11/23 11:39
 */
public interface OrganizationService  {

    public List<Organization> findAll(Organization organization);

    /**  @param Page(页数)    PageSize（每页显示的条数） Dealer（实体类）
     * 根据条件分页查询机构用户信息
     * @param organization
     * @return
     */
    public Page<Organization> listPage(Integer Page, Integer PageSize, Organization organization);


    /**
     * 新增或者修改
     * @param organization
     */
    public void save(Organization organization);


    /**
     * 根据id查询
     * @param id
     */
    public Organization findById(String id);

    /**根据id删除
     * @param id
     */
    public void delete(String id);



}
