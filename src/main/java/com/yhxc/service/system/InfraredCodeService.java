package com.yhxc.service.system;

import com.yhxc.entity.system.InfraredCode;
import com.yhxc.entity.system.Log;
import net.sf.json.JSONArray;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 红外代码Service接口
 * @author yhxc 赵贺飞
 *
 */
public interface InfraredCodeService {


	/**
	 * 红外代码新增修改
	 * @param infraredCode
	 */
	public void save(InfraredCode infraredCode);



	public InfraredCode findById(Integer id);

	/**
	 * 根据条件分页查询红外代码
	 * @param infraredCode
	 * @return
	 */
	public Page<InfraredCode> list(InfraredCode infraredCode, Integer page, Integer pageSize, Direction direction, String... properties);


	/**
	 * @param id
	 */
	public void delete(Integer id);


	public List<InfraredCode> findByBrand(String brand);




}
