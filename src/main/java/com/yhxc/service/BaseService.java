package com.yhxc.service;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @Author: 赵贺飞
 * @Date: 2018/11/29 11:27
 * service 接口继承该接口
 */
public interface BaseService<T> {

    /**
     * 查询全部
     *
     * @return
     */
    List<T> findAll();


    /**
     * 根据某个参数查询List列表
     *
     * @param var
     * @return
     */
    List<T> findAll(String var);


    /**
     * 根据对象查询List列表
     * @param obj
     * @return
     */
    List<T> findAll(T obj);

    /**
     * 根据String类型的ID查询
     *
     * @param id
     * @return
     */
    T findById(String id);

    /**
     * 根据Integer类型的ID查询
     *
     * @param id
     * @return
     */
    T findById(Integer id);

    /**
     * 分页查询
     *
     * @param object
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<T> listPage(T object, Integer pageNum, Integer pageSize);

    /**
     * 根据String 类型的主键 删除
     *
     * @param id
     */
    void delete(String id);

    /**
     * 根据Integer类型的主键删除
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * 新增和修改方法
     *
     * @param var
     */
    void save(T var);
}
