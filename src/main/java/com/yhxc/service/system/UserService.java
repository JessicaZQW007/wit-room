package com.yhxc.service.system;

import java.util.List;
import java.util.Map;

import com.yhxc.common.ResultInfo;
import com.yhxc.entity.unit.Unit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import com.yhxc.entity.system.User;

/**
 * 用户Service接口
 * @author yhxc 赵贺飞
 *
 */
public interface UserService {


	public int updState(Integer state, Integer id);

	public ResultInfo search(User user, Integer pageNum, Integer pageSize);

	/**
	 * 根据用户名查找用户实体
	 * @param userName
	 * @return
	 */
	public User findByUserName(String userName);

	/**
	 * 根据手机号查找用户实体
	 * @param phone
	 * @return
	 */
	public User findByPhone(String phone);

	/**
	 * 使用用户名或手机号登录
	 * @param phoneOrUserName
	 * @return
	 */
	public User findByPhoneOrUserName(String phoneOrUserName);

	public User findById(Integer id);

	public void save(User user);

	public Page listPage(User user, Integer page, Integer size);

	public List<User> findAll();


	List<User> findAll(User user);

	public void delete(Integer id);

	//
	public long count(Integer type);

	public List<User> listOrganizationByDealer(String dealerId);

	public List<User> listZzByOrganization(String organizationId);

	public List<User> listZzByDealerId(String dealerId);

	public List<User> listDealerByDealer(String dealerId);

	public List<User> listOrganizationByOrganization(String organizationId);





	//多条件查询用户 分页
	public List<User> findAllListPage(String pId,String type, String userName, int pageNum, int pageSize );

	//多条件查询用户 数据数量
	public int findAllListCount(String pId,String type, String userName);



}
