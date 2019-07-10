package com.yhxc.service.company;

import com.yhxc.entity.company.Company;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 企业信息Service接口
 * @author lwg
 *
 */
public interface CompanyService {

	/**
	 * 根据id查询
	 * @param id
	 */
	public Company findById(String id);

	/**
	 * 新增或者修改企业信息
	 * @param company
	 */
	public void save(Company company);
	
	/**  @param Page(页数)    PageSize（每页显示的条数） Company（实体类）
	 * 根据条件分页查询企业信息
	 * @param company
	 * @return
	 */
	public	Page<Company> listPage(Integer Page, Integer PageSize, Company company);


	/**根据id删除
	 * @param id
	 */
	public void delete(String id);


	public List<Company> findAll();


	/**根据更改状态
	 * @param id
	 */
	public void updateStatus( String id);


}
