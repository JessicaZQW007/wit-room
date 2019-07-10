package com.yhxc.service.information.impl;

import com.yhxc.entity.information.Feedback;
import com.yhxc.entity.system.User;
import com.yhxc.repository.information.FeedbackRepository;
import com.yhxc.service.information.FeedbackService;
import com.yhxc.utils.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @Author: 赵贺飞
 * @Date: 2018/11/29 16:30
 */
@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Resource
    private FeedbackRepository feedbackRepository;

    @Override
    public List<Feedback> findAll() {
        return feedbackRepository.findAll();
    }

    @Override
    public List<Feedback> findAll(String var) {
        List<Feedback> list = feedbackRepository.findAll(new Specification<Feedback>() {
            @Override
            public Predicate toPredicate(Root<Feedback> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                /*if (StringUtil.isNotEmpty(var)) {
                    predicate.getExpressions().add(cb.like(root.get("companyId"), var));
                    predicate.getExpressions().add(cb.like(root.get("userName"), var));
                    predicate.getExpressions().add(cb.like(root.get("dealerId"), var));
                    predicate.getExpressions().add(cb.equal(root.get("userType"), var));
                    predicate.getExpressions().add(cb.like(root.get("organizationId"), var));
                }*/
                return predicate;
            }
        });
        return list;
    }

    @Override
    public List<Feedback> findAll(Feedback obj) {
        return feedbackRepository.findAll(new Specification<Feedback>() {
            @Override
            public Predicate toPredicate(Root<Feedback> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return serach(obj, cb, root);
            }
        });
    }

    @Override
    public Feedback findById(String id) {
        return feedbackRepository.findOne(id);
    }

    @Override
    public Feedback findById(Integer id) {
        return null;
    }

    @Override
    public Page<Feedback> listPage(Feedback object, Integer pageNum, Integer pageSize) {
        Pageable pageable = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "createTime");
        return feedbackRepository.findAll(new Specification<Feedback>() {
            @Override
            public Predicate toPredicate(Root<Feedback> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return serach(object, cb, root);
            }
        }, pageable);
    }

    public Predicate serach(Feedback object, CriteriaBuilder cb, Root<Feedback> root) {
        Predicate predicate = cb.conjunction();
        if (object != null) {
            if (StringUtil.isNotEmpty(object.getUserName())) {
                predicate.getExpressions().add(cb.equal(root.get("userName"), object.getUserName().trim()));
            }
            if (object.getUserType() != null) {
                predicate.getExpressions().add(cb.equal(root.get("userType"), object.getUserType()));
            }
            if (object.getType() != null) {
                predicate.getExpressions().add(cb.equal(root.get("type"), object.getType()));
            }
            if (object.getState() != null) {
                predicate.getExpressions().add(cb.equal(root.get("state"), object.getState()));
            }
            if (StringUtil.isNotEmpty(object.getCreateTime())) {
                predicate.getExpressions().add(cb.like(root.get("createTime"), "%"+object.getCreateTime()+"%"));
            }
        }
        return predicate;
    }


    @Override
    public void delete(String id) {
        feedbackRepository.delete(id);
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public void save(Feedback var) {
        feedbackRepository.save(var);
    }

    /**
     * 提交用户反馈
     *
     * @param uploadFile
     * @param feedback
     */
    @Override
    public void release(MultipartFile uploadFile, Feedback feedback) {
        User user = Jurisdiction.getCurrentUser();
        String userName = user.getUserName();
        feedback.setId(UuidUtil.get32UUID());
        feedback.setUserName(userName);

        String webpath = UploadUtil.uploadFile(uploadFile, "feedback");
        if (webpath != null) {
            feedback.setImg(webpath);
        }
        feedback.setState(0);
        feedback.setCreateTime(DateUtil.getTime());
        feedbackRepository.save(feedback);
    }


    @Override
    public void updDeal(Feedback feedback) {
        feedbackRepository.updDeal(feedback.getDealUserName(), feedback.getDealTime(), feedback.getDealRemark(), feedback.getId());
    }

    @Override
    public long count(Feedback feedback) {
        return feedbackRepository.count(new Specification<Feedback>() {
            @Override
            public Predicate toPredicate(Root<Feedback> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return serach(feedback, cb, root);
            }
        });
    }
}
