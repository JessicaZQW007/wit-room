package com.yhxc.service.information;

import com.yhxc.entity.information.Notice;
import net.sf.json.JSONArray;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;


/**
 * 公告 Service接口
 * 
 * @author 李文光
 *
 */
public interface NoticeService {

	/**
	 * 新增或者修改公告信息
	 * 
	 * @param notice
	 */
	public void save(Notice notice);

	/**
	 * 根据id查询公告
	 * 
	 * @param id
	 * @return
	 */
	public Notice findById(String id);

	/**
	 * 根据条件分页查询公告信息
	 * 
	 * @param page(页数)
	 * @param pageSize(每页显示的条数)
	 * @return Page<Notice>
	 */
	public Page<Notice> listAll(Notice notice, Integer page, Integer pageSize, Sort.Direction direction, String... properties);

	/**
	 * 不带查询条件分页查询公告信息
	 * 
	 * @param page(页数)
	 * @param pageSize(每页显示的条数)
	 * @return list<Notice>
	 */
	
	Page<Notice> findListPageNoif(Integer page, Integer pageSize);
	
	/**
	 * 根据id删除公告
	 * 
	 * @param noticeId
	 */
	public void deleteById(String id);

	/**
	 * 根据id修改flag
	 * 
	 * @param flag
	 * @param Id
	 */
	public void editFlag(String flag, String Id);

	/**
	 * 将磁条数据变为发布装填
	 *
	 * @param flag
	 * @param Id
	 */
	public void editState(Integer flag, String Id);

	/**
	 * 查询所有的公告信息
	 * @return
	 */
	
	public List<Notice> findAllList();
	
	/**批量删除公告信息
	 * 
	 * @param notice
	 */
	public void deleteAll(Notice notice);
	
	/**查询最新公告
	 * 
	 * @param
	 */
	public List<Notice>  findNewList();



	public JSONArray listZzTree();
}
