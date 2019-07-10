package com.yhxc.service.company;

import com.yhxc.entity.company.Company;
import com.yhxc.entity.company.CompanyType;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 企业类型Service接口
 * @author lwg
 *
 */
public interface CompanyTypeService {


	/**
	 * 新增或者修改企业类型信息
	 * @param companyType
	 */
	public void save(CompanyType companyType);



	public List<CompanyType> findAll();


	public void delete(String id);
}
