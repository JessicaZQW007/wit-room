package com.yhxc.service.company.impl;

import com.yhxc.entity.company.Company;
import com.yhxc.entity.company.Dealer;
import com.yhxc.repository.company.DealerRepository;
import com.yhxc.service.company.DealerService;
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
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 赵贺飞
 * @Date: 2018/11/23 11:40
 */

@Service
public class DealerServiceImpl implements DealerService {

    @Resource
    private DealerRepository dealerRepository;

    @Override
    public List<Dealer> findAll(Dealer dealer) {
        return dealerRepository.findAll(new Specification<Dealer>() {
            @Override
            public Predicate toPredicate(Root<Dealer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (dealer != null) {
                    if (StringUtil.isNotEmpty(dealer.getCompanyId())) {
                        predicate.getExpressions().add(cb.equal(root.get("companyId"), dealer.getCompanyId()));
                    }
                    if (StringUtil.isNotEmpty(dealer.getAddress())) {
                        predicate.getExpressions().add(cb.like(root.get("address"), "%" + dealer.getAddress().trim() + "%"));
                    }
                    if (StringUtil.isNotEmpty(dealer.getCreateTime())) {
                        String  allDate=dealer.getCreateTime();
                        String startDate = allDate.substring(0, 10);
                        String endDate = allDate.substring(13, 23);
                        predicate.getExpressions().add(cb.between(root.get("createtime"),
                                startDate,
                                endDate));

                    }

                }
                return predicate;
            }
        });
    }

    @Override
    public JSONArray dealerTree(String companyId) {
        JSONArray jsonArray=new JSONArray();
        JSONObject jsonObject = new JSONObject();
       List<String> data1=dealerRepository.findAddress(companyId);
        JSONArray jsonArray1=new JSONArray();
        for(String address : data1) {
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("value", address);
            jsonObject1.put("label", address);
            List<String> data2=dealerRepository.findDealerName(companyId,address);
            JSONArray jsonArray2=new JSONArray();
            for(String name : data2) {
                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put("value", name);
                jsonObject2.put("label", name);
                jsonArray2.add(jsonObject2);

            }
            jsonObject1.put("children", jsonArray2);
            jsonArray1.add(jsonObject1);

        }
        jsonObject.put("id", "0");
        jsonObject.put("label", "选择经销商");
        jsonObject.put("children", jsonArray1);
        jsonArray.add(jsonObject);
        return jsonArray;
    }

    @Override
    public void save(Dealer dealer) {
        dealerRepository.save(dealer);
    }

    @Override
    public Dealer findById(String id) {
        return dealerRepository.findOne(id);
    }

    @Override
    public void delete(String id) {
        dealerRepository.delete(id);

    }

    @Override
    public Page<Dealer> listPage(Integer Page, Integer PageSize, Dealer dealer) {
        Pageable pageable = new PageRequest(Page - 1, PageSize, Sort.Direction.DESC, "createTime");
        Page<Dealer> pageData = dealerRepository.findAll(new Specification<Dealer>() {
            @Override
            public Predicate toPredicate(Root<Dealer> root, CriteriaQuery<?> query,
                                         CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                if (dealer != null) {
                    if (StringUtil.isNotEmpty(dealer.getName())) {
                        predicates.add(cb.like(root.get("name"), "%" + dealer.getName().trim() + "%"));
                    }
                    if (StringUtil.isNotEmpty(dealer.getCompanyId())) {
                        predicates.add(cb.like(root.get("companyId"), "%" + dealer.getCompanyId().trim() + "%"));
                    }
                    if (StringUtil.isNotEmpty(dealer.getAddress())) {
                        predicates.add(cb.like(root.get("address"), "%" + dealer.getAddress().trim() + "%"));
                    }
                    if (StringUtil.isNotEmpty(dealer.getCreateTime())) {
                        String  allDate=dealer.getCreateTime();
                        String startDate = allDate.substring(0, 10);
                        String endDate = allDate.substring(11, 21);
                        predicates.add(cb.between(root.get("createTime"),
                                startDate,
                                endDate));

                    }
                }
                query.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
        return pageData;
    }
}
