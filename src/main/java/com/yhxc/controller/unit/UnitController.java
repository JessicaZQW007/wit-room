package com.yhxc.controller.unit;


import com.yhxc.common.ResultInfo;
import com.yhxc.common.StatusCode;
import com.yhxc.entity.system.Log;
import com.yhxc.entity.system.User;
import com.yhxc.entity.unit.Unit;
import com.yhxc.service.system.LogService;
import com.yhxc.service.unit.UnitService;
import com.yhxc.utils.DateUtil;
import com.yhxc.utils.Jurisdiction;
import com.yhxc.utils.StringUtil;
import com.yhxc.utils.UuidUtil;
import net.sf.json.JSONArray;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: 陈霖炎
 * @Date: 2019/7/5 10:50
 */
@Controller
@RequestMapping("/unit")
@Component
public class UnitController {

    @Resource
    private UnitService unitService;

    @Resource
    private LogService logService;




    /**
     * 分页查询全部平台单位或者项目机构
     *
     * @param type 1平台单位 2项目机构
     * @param name 单位名称
     * @return
     */
    @ResponseBody
    @RequestMapping("/findList")
    public ResultInfo findList(String type,String name,int pageNum, int pageSize) {
        pageNum=(pageNum - 1) * pageSize;
        User u = Jurisdiction.getCurrentUser();
        String pId="";

        if(u.getUserType()==1){
            //平台用户
            pId=u.getUnitId();

        }else if(u.getUserType()==2){
            //机构用户
            pId=u.getUnitId();
        }


        List<Unit> unitList=unitService.findAllListPage(type,name,pId,pageNum,pageSize);
        int num=unitService.findAllListCount(type,name,pId);

        return new ResultInfo(StatusCode.SUCCESS, "成功！", unitList,num);
    }


    /**
     * 判断平台单位或项目机构是否存在
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/findByName")
    public ResultInfo findByName(String type,String name,String id) {
        String info = "";




        if (StringUtil.isEmpty(id)) {
            if (type.equals("1")) {
                //添加的时候
                int num = unitService.findByName(type, name);
                if (num > 0) {
                    info = "平台单位已存在";
                }

            } else if (type.equals("2")) {
                int num = unitService.findByName(type, name);
                if (num > 0) {
                    info = "项目机构已存在";
                }
            }

        }else {
            //修改的时候
            Unit unit = unitService.findOneById(id);
            System.out.println("修改的时候");
            if (unit.getName().equals(name)) {
                System.out.println("相同");
                info = "";
            }else{

                if (type.equals("1")) {
                    //添加的时候
                    int num = unitService.findByName(type, name);
                    if (num > 0) {
                        info = "平台单位已存在";
                    }

                } else if (type.equals("2")) {
                    int num = unitService.findByName(type, name);
                    if (num > 0) {
                        info = "项目机构已存在";
                    }
                }
            }

        }

        return new ResultInfo(StatusCode.SUCCESS, "success", info);
    }



    /**
     * 添加修改平台单位或者项目机构
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/saveOrUpdate")
    public ResultInfo saveOrUpdate(Unit unit) {

        String info = "";


            System.out.println("unit.getID="+unit.getId());
            if(StringUtil.isEmpty(unit.getId())){
                System.out.println("进来了");
                //ID为空执行添加操作
                System.out.println("UuidUtil.get32UUID()="+UuidUtil.get32UUID());
                unit.setId(UuidUtil.get32UUID());

                if(unit.getType().equals("1")){
                    //平台机构
                    unit.setpId("-1");
                }

                unit.setCreatetime(DateUtil.getTime());
                unit.setState(0);
                unitService.save(unit);


                info = "新增成功";
                logService.save(new Log(Log.ADD_ACTION, info + "," + unit));


            }else{
                //ID不为空执行修改操作
                Unit u=unitService.findOneById(unit.getId());

                unit.setCreatetime(u.getCreatetime());//时间
                unit.setState(u.getState());//状态
                unitService.save(unit);

                info = "修改成功";
                logService.save(new Log(Log.ADD_ACTION, info + "," + unit));

            }







        return new ResultInfo(StatusCode.SUCCESS, "success",info);
    }


    /**
     * 添加修改平台单位或者项目机构
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateState")
    public ResultInfo updateState(int state,String id) {
        if(state==0){
            state=1;
        }else if(state==1){
            state=0;
        }


        unitService.updateState(state,id);

        String info = "修改成功";
        logService.save(new Log(Log.ADD_ACTION, info + "," + state+id));

        return new ResultInfo(StatusCode.SUCCESS, "success",info);
    }




    /**
     * 删除用户信息--批量
     *
     * @param ids
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/dels")
    public ResultInfo deletes(String ids) throws Exception {
        if (StringUtil.isNotEmpty(ids)) {
            String idStr[] = ids.split(",");
            for (int i = 0; i < idStr.length; i++) {
                Unit uu=unitService.findOneById(idStr[i]);
                logService.save(new Log(Log.DELETE_ACTION, "删除用户信息" + uu));
                unitService.delete(idStr[i]);//删除单位或者机构
            }
        }
        return new ResultInfo(StatusCode.SUCCESS, "删除成功！");
    }




    /**
     * 查询所有平台单位或者项目机构 ( 状态(state 0)为正常的)
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/findNameList")
    public ResultInfo findNameList(String type) {
        User u = Jurisdiction.getCurrentUser();

        String pId="";
        String unitId="";
        if(u.getUserType()==1){
            //平台用户
            if(type.equals("2")){
                pId=u.getUnitId();
            }


            if(type.equals("1")){
                unitId=u.getUnitId();
            }

        }else if(u.getUserType()==2){
            //机构用户
            if(type.equals("2")){
                unitId=u.getUnitId();
            }else if(type.equals("3")){
                pId=u.getUnitId();
            }
        }

        return new ResultInfo(StatusCode.SUCCESS, "成功！", unitService.findNameList(type,pId,unitId));
    }






    /**
     * 下拉联动 ( 状态(state 0)为正常的)
     *
     * @return
     */

    @ResponseBody
    @RequestMapping("/findNameAll")
    public ResultInfo findNameAll() {


        return new ResultInfo(StatusCode.SUCCESS, "成功！", unitService.findNameAll());
    }



    /**
     * 根据ID查询数据
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/findById")
    public ResultInfo finById(String pId) {


        return new ResultInfo(StatusCode.SUCCESS, "成功！", unitService.findOneById(pId));
    }











}
