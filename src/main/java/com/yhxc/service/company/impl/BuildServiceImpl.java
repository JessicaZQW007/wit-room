package com.yhxc.service.company.impl;


import com.yhxc.entity.company.Build;
import com.yhxc.entity.project.Project;
import com.yhxc.entity.system.User;
import com.yhxc.repository.company.BuildRepository;
import com.yhxc.repository.equipment.EquipmentRepository;
import com.yhxc.service.company.BuildService;
import com.yhxc.service.project.ProjectService;
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
 * 建筑，房间 ServiceImpl实现类
 *
 * @author lwg
 */

@Service
public class BuildServiceImpl implements BuildService {

    @Resource
    private BuildRepository buildRepository;
    @Resource
    private ProjectService projectService;
    @Resource
    private EquipmentRepository equipmentRepository;

    @PersistenceContext
    private EntityManager em;

    @Override
    public void save(Build build) {
        buildRepository.save(build);
    }

    @Override
    public void delete(String id) {
        buildRepository.delete(id);

    }

    @Override
    public void deletesRoom(String companyId) {
        buildRepository.deletesRoom(companyId);
    }


    /**根据项目查询
     * 建筑-房间号-uuid 树形图
     */
    @Override
    public JSONArray buildTree(String projectId) {
        JSONArray jsonArray1 = new JSONArray();
        List<Build> datas1 = buildRepository.findBuilds(projectId);
        for (int i = 0; i < datas1.size(); i++) {
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("value", datas1.get(i).getId());
            jsonObject1.put("label", datas1.get(i).getName());
            List<Build> data2 = buildRepository.findRooms(datas1.get(i).getId());//查询房间号
            JSONArray jsonArray2 = new JSONArray();
            for (Build companyBuild2 : data2) {
                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put("value", companyBuild2.getId());
                jsonObject2.put("label", companyBuild2.getName());
                List<?> data3=equipmentRepository.findByRoomId(companyBuild2.getId());
                JSONArray jsonArray3 = new JSONArray();
                for (int k= 0; k < data3.size(); k++) {
                    JSONObject jsonObject3 = new JSONObject();
                    Object[] objects = (Object[]) data3.get(k);
                    jsonObject3.put("value",objects[7]);
                    jsonObject3.put("label",objects[0]);
                    jsonArray3.add(jsonObject3);
                }

                jsonObject2.put("children", jsonArray3);
                jsonArray2.add(jsonObject2);
            }
            jsonObject1.put("children", jsonArray2);
            jsonArray1.add(jsonObject1);
        }
        //js.put("children", jsonArray1);
        //array.add(js);
        //}
        return jsonArray1;
    }

    @Override
    public JSONArray buildTreeByProject() {
        User u = Jurisdiction.getCurrentUser();
        Integer type = u.getType();
        List<Project> lis = new ArrayList<>();
       /* if (type == 3) {
            lis = projectService.findByDealerId(u.getDealerId());
        } else if(type == 4) {
            Project p = new Project();
            p.setOrganizationId(u.getOrganizationId());
            lis = projectService.findAll(p);
        }*/

        JSONArray array = new JSONArray();
        for (Project p : lis) {
            JSONObject js = new JSONObject();
            js.put("value", p.getId());
            js.put("label", p.getPname());
            JSONArray jsonArray1 = new JSONArray();
            List<Build> datas1 = buildRepository.findBuilds(p.getId());
            for (int i = 0; i < datas1.size(); i++) {
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("value", datas1.get(i).getId());
                jsonObject1.put("label", datas1.get(i).getName());
                List<Build> data2 = buildRepository.findRooms(datas1.get(i).getId());//查询房间号
                JSONArray jsonArray2 = new JSONArray();
                for (Build companyBuild2 : data2) {
                    JSONObject jsonObject2 = new JSONObject();
                    jsonObject2.put("value", companyBuild2.getId());
                    jsonObject2.put("label", companyBuild2.getName());
                    jsonArray2.add(jsonObject2);
                }
                jsonObject1.put("children", jsonArray2);
                jsonArray1.add(jsonObject1);
            }
            js.put("children", jsonArray1);
            array.add(js);
        }
        return array;
    }

    @Override
    public List<Build> buildTreeList(String pids) {
        return buildRepository.findRoomList(pids);
    }


    @Override
    public JSONArray buildAllList(String name, String projectId) {
        String dataSql = "SELECT id,name,pid,project_id,createtime FROM t_build WHERE   pid='0' ";
        if (StringUtil.isNotEmpty(name)) {
            dataSql += " and name=:name ";

        }
        if (StringUtil.isNotEmpty(projectId)) {
            dataSql += " and project_id=:projectId ";

        }
        dataSql += "   ORDER BY createtime DESC  ";
        // setFirstResults:数据从第几个开始显示（currentPage-1）*PageSize
        // setMaxResults：每页显示的数据数量PageSize
        Query dataQuery = em.createNativeQuery(dataSql);

        if (StringUtil.isNotEmpty(name)) {
            dataQuery.setParameter("name", name);
        }
        if (StringUtil.isNotEmpty(projectId)) {
            dataQuery.setParameter("projectId", projectId);
        }

        List<?> datas = dataQuery.getResultList();

        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < datas.size(); i++) {
            Object[] objects = (Object[]) datas.get(i);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", objects[0]);
            jsonObject.put("name", objects[1]);
            jsonObject.put("pid", objects[2]);
            jsonObject.put("project_id", objects[3]);
            jsonObject.put("createtime", objects[4]);
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }


    @Override
    public JSONArray roomAllList(String name, String projectId, String pid) {
        String dataSql = "SELECT id,name,pid,project_id ,createtime FROM t_build WHERE   1=1 ";
        if (StringUtil.isNotEmpty(name)) {
            dataSql += " and name=:name ";

        }
        if (StringUtil.isNotEmpty(projectId)) {
            dataSql += " and project_id=:projectId ";

        }
        if (StringUtil.isNotEmpty(pid)) {
            dataSql += " and pid=:pid ";

        }
        dataSql += "  ORDER BY createtime DESC  ";

        Query dataQuery = em.createNativeQuery(dataSql);

        if (StringUtil.isNotEmpty(name)) {
            dataQuery.setParameter("name", name);
        }
        if (StringUtil.isNotEmpty(projectId)) {
            dataQuery.setParameter("projectId", projectId);
        }
        if (StringUtil.isNotEmpty(pid)) {
            dataQuery.setParameter("pid", pid);
        }
        List<?> datas = dataQuery.getResultList();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < datas.size(); i++) {
            Object[] objects = (Object[]) datas.get(i);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", objects[0]);
            jsonObject.put("name", objects[1]);
            jsonObject.put("pid", objects[2]);
            jsonObject.put("project_id", objects[3]);
            jsonObject.put("createtime", objects[4]);
            jsonArray.add(jsonObject);
        }
        return jsonArray;

    }

    @Override
    public List<Build> findRooms(String pid) {
        return buildRepository.findRooms(pid);
    }


    @Override
    public Build findById(String id) {
        return buildRepository.findOne(id);
    }

    @Override
    public Page<Build> listPage(Integer Page, Integer PageSize, Build build) {
        Pageable pageable = new PageRequest(Page - 1, PageSize, Sort.Direction.ASC, "name");
        Page<Build> pageData = buildRepository.findAll(new Specification<Build>() {
            @Override
            public Predicate toPredicate(Root<Build> root, CriteriaQuery<?> query,
                                         CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                if (build != null) {
                    if (StringUtil.isNotEmpty(build.getName())) {

                        predicates.add(cb.like(root.get("name"), "%" + build.getName().trim() + "%"));
                    }
                }
                query.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
        return pageData;
    }


}
