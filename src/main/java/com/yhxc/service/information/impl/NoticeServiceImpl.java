package com.yhxc.service.information.impl;

import com.yhxc.entity.information.Notice;
import com.yhxc.entity.system.User;
import com.yhxc.repository.information.NoticeRepository;
import com.yhxc.service.information.NoticeService;
import com.yhxc.service.system.UserService;
import com.yhxc.utils.Jurisdiction;
import com.yhxc.utils.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * 公告Service实现类
 *
 * @author 李文光
 */

@Service("noticeService")
public class NoticeServiceImpl implements NoticeService {

    @Resource
    private NoticeRepository noticeRepository;

    @Resource
    private UserService userService;

    /**
     * 新增或者修改公告信息
     *
     * @param notice
     */
    @Override
    public void save(Notice notice) {
        noticeRepository.save(notice);

    }

    @Override
    public void editFlag(String flag, String Id) {
        noticeRepository.editFlag(flag, Id);
    }

    @Override
    public void editState(Integer state, String Id) {
        noticeRepository.editState(state, Id);
    }


    @Override
    public void deleteById(String id) {
        noticeRepository.delete(id);
    }

    /**
     * 根据id查询公告
     *
     * @param id
     * @return
     */
    @Override
    public Notice findById(String id) {
        return noticeRepository.findOne(id);
    }

    @Override
    public JSONArray listZzTree() {
        User user = Jurisdiction.getCurrentUser();

        JSONArray jsonArray = new JSONArray();
        if (user.getType() == 4) {
            String organizationId = user.getOrganizationId();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", "");
            jsonObject.put("label", "全选");

            List<User> listZZ = userService.listZzByOrganization(organizationId);
            JSONArray array = new JSONArray();
            JSONObject obj = null;
            for (User zz : listZZ) {
                obj = new JSONObject();
                obj.put("id", zz.getUserName());
                obj.put("label", zz.getRealName());
                obj.put("children", null);
                array.add(obj);
            }
            jsonObject.put("children", array);
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }


    /**
     * 查询所有的公告信息
     *
     * @return
     */
    @Override
    public List<Notice> findAllList() {
        return noticeRepository.findAll();
    }

    /**
     * 根据条件分页查询公告信息
     *
     * @return Page<Notice>
     */
    public Page<Notice> listAll(Notice notice, Integer page, Integer pageSize, Sort.Direction direction, String... properties) {
        notice.setUserName(Jurisdiction.getUserName());
        Pageable pageable = new PageRequest(page - 1, pageSize, direction, properties);
        return noticeRepository.findAll(new Specification<Notice>() {
            @Override
            public Predicate toPredicate(Root<Notice> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return serach(notice, cb, root);
            }
        }, pageable);
    }

    /**
     * 封装查询条件
     *
     * @param cb
     * @param root
     * @return
     */
    public Predicate serach(Notice notice, CriteriaBuilder cb, Root<Notice> root) {
        Predicate predicate = cb.conjunction();
        if (notice != null) {
            if (StringUtil.isNotEmpty(notice.getTitle())) {
                predicate.getExpressions().add(cb.like(root.get("title"), "%" + notice.getTitle().trim() + "%"));
            }
            if (StringUtil.isNotEmpty(notice.getUserName())) {
                predicate.getExpressions().add(cb.like(root.get("userName"), "%" + notice.getUserName().trim() + "%"));
            }
            if (notice.getStartTime() != null) {
                predicate.getExpressions().add(cb.equal(root.get("startTime"), notice.getStartTime()));
            }
            if (notice.getStopTime() != null) {
                predicate.getExpressions().add(cb.equal(root.get("stopTime"), notice.getStopTime()));
            }

        }
        return predicate;
    }

    /**
     * 不带查询条件分页查询公告信息
     *
     * @param page(页数)
     * @param pageSize(每页显示的条数)
     * @return Page<Notice>
     */

    @Override
    public Page<Notice> findListPageNoif(Integer page, Integer pageSize) {
        Pageable pageable = new PageRequest(page, pageSize, Sort.Direction.ASC, "id");
        return noticeRepository.findAll(pageable);
    }

    /**
     * 批量删除公告信息
     *
     * @param notice
     */
    @Override
    public void deleteAll(Notice notice) {
        noticeRepository.deleteAll();
    }

    /**
     * 查询最新公告信息
     */
    @Override
    public List<Notice> findNewList() {
        return noticeRepository.findNewList();
    }


}
