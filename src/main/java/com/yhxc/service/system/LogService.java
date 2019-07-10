package com.yhxc.service.system;

import java.util.List;

import com.yhxc.common.ResultInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import com.yhxc.entity.system.Log;

/**
 * 系统日志Service接口
 * @author yhxc 赵贺飞
 *
 */
public interface LogService {

	public Log findById(Integer id);

	/**
	 * 修改或者修改日志信息
	 * @param log
	 */
	public void save(Log log);
	
	/**
	 * 根据条件分页查询日志信息
	 * @param log
	 * @return
	 */
	public Page<Log> list(Log log, Integer page, Integer pageSize, Direction direction, String... properties);


	/**
	 * @param id
	 */
	public void delete(Integer id);
}
