package com.yhxc.service.system.impl;

import com.yhxc.common.PageInfo;
import com.yhxc.common.ResultInfo;
import com.yhxc.common.StatusCode;
import com.yhxc.entity.system.User;
import com.yhxc.entity.unit.Unit;
import com.yhxc.repository.system.UserRepository;
import com.yhxc.service.system.UserService;
import com.yhxc.utils.StringUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户Service实现类
 *
 * @author yhxc 赵贺飞
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager em;

    @Override
    public ResultInfo search(User user, Integer pageNum, Integer pageSize) {
        List<User> userList = new ArrayList<User>();
        Integer total = 0;

        String dataSql = "SELECT u from User u where 1 = 1";
        if (user != null) {
            if (StringUtil.isNotEmpty(user.getUserName())) {
                dataSql += " and u.userName = :userName";
            }
        }
        Query dataQuery = em.createQuery(dataSql);
        if (user != null) {
            if (StringUtil.isNotEmpty(user.getUserName())) {
                dataQuery.setParameter("userName", user.getUserName());
            }
        }
        total = dataQuery.getResultList().size();
        userList = dataQuery.setFirstResult((pageNum - 1) * pageSize).
                setMaxResults(pageSize).getResultList();
        PageInfo pi = new PageInfo();
        pi.setPageNum(pageNum);
        pi.setPageSize(pageSize);
        pi.setTotal(total);
        pi.setSize(userList.size());
        return new ResultInfo(StatusCode.SUCCESS, "查询成功！", userList, pi);
    }

    @Override
    public int updState(Integer state, Integer id) {
        return userRepository.updState(state, id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public User findByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }

    @Override
    public User findByPhoneOrUserName(String phoneOrUserName) {
        User user = null;
        if (userRepository.findByUserName(phoneOrUserName) != null) {
            user = userRepository.findByUserName(phoneOrUserName);
        } else {
            user = userRepository.findByPhone(phoneOrUserName);
        }
        return user;
    }

    @Override
    public User findById(Integer id) {
        return userRepository.findOne(id);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    /**
     * 多条件不分页查询
     *
     * @param user
     * @return
     */
    @Override
    public List<User> findAll(User user) {
        return userRepository.findAll(new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                return param(user, predicate, cb, root);
            }
        });
    }

    /**
     * 多条件分页查询
     *
     * @param user
     * @return
     */
    @Override
    public Page listPage(User user, Integer page, Integer size) {
        Pageable pageable = new PageRequest(page - 1, size, Direction.DESC, "createTime");
        Page<User> pageUser = userRepository.findAll(new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                return param(user, predicate, cb, root);
            }
        }, pageable);
        return pageUser;
    }

    public Predicate param(User user, Predicate predicate, CriteriaBuilder cb, Root<User> root) {
        if (user != null) {
            if (StringUtil.isNotEmpty(user.getUserName())) {
                predicate.getExpressions().add(cb.like(root.get("userName"), "%" + user.getUserName().trim() + "%"));
            }
            if (StringUtil.isNotEmpty(user.getPhone())) {
                predicate.getExpressions().add(cb.like(root.get("phone"), "%" + user.getPhone().trim() + "%"));
            }
            if (StringUtil.isNotEmpty(user.getRealName())) {
                predicate.getExpressions().add(cb.like(root.get("realName"), "%" + user.getRealName().trim() + "%"));
            }
            if (StringUtil.isNotEmpty(user.getUnit())) {
                predicate.getExpressions().add(cb.like(root.get("unit"), "%" + user.getUnit().trim() + "%"));
            }
            if (user.getType() != null) {
                predicate.getExpressions().add(cb.equal(root.get("type"), user.getType()));
            }
        }
        return predicate;
    }

    @Override
    public void delete(Integer id) {
        userRepository.delete(id);
    }

    @Override
    public long count(Integer type) {
        long count = userRepository.count(new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (type != null) {
                    predicate.getExpressions().add(cb.equal(root.get("type"), type));
                }
                return predicate;
            }
        });
        return count;
    }

    @Override
    public List<User> listOrganizationByDealer(String dealerId) {
        return userRepository.listOrganizationByDealer(dealerId);
    }

    @Override
    public List<User> listZzByOrganization(String organizationId) {
        return userRepository.listZzByOrganization(organizationId);
    }

    @Override
    public List<User> listZzByDealerId(String dealerId) {
        return userRepository.listZzByDealerId(dealerId);
    }

    @Override
    public List<User> listDealerByDealer(String dealerId) {
        return userRepository.listDealerByDealer(dealerId);
    }

    @Override
    public List<User> listOrganizationByOrganization(String organizationId) {
        return userRepository.listOrganizationByOrganization(organizationId);
    }




    @Override
    public List<User> findAllListPage(String pId, String type,String userName, int pageNum, int pageSize ){
        List<User> userList=userRepository.findAllListPage(pId,type,userName,pageNum,pageSize);



        return userList;
    }



    @Override
    public int findAllListCount(String pId, String type,String userName ){
        int num=userRepository.findAllListCount(pId,type,userName);


        return num;
    }


    @Override
    public List<User> findAllListPageType(String pId, String type,String userName,String unitId, int pageNum, int pageSize ){
        List<User> userList=userRepository.findAllListPageType(pId,type,userName,unitId,pageNum,pageSize);



        return userList;
    }



    @Override
    public int findAllListCountType(String pId, String type,String userName,String unitId ){
        List<Integer> datas=userRepository.findAllListCountType(pId,type,userName,unitId);
        int num=0;
        for (int i=0;i>datas.size();i++){
            System.out.println(datas.get(i));
            int k=datas.get(i);
            num=datas.get(i)+num;
        }


        return num;
    }
}
