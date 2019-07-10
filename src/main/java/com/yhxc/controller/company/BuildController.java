package com.yhxc.controller.company;

import com.yhxc.common.ResultInfo;
import com.yhxc.common.StatusCode;
import com.yhxc.entity.company.Build;
import com.yhxc.entity.system.Log;
import com.yhxc.service.company.BuildService;
import com.yhxc.service.equipment.EquipmentService;
import com.yhxc.service.system.LogService;
import com.yhxc.utils.DateUtil;
import com.yhxc.utils.Jurisdiction;
import com.yhxc.utils.StringUtil;
import com.yhxc.utils.UuidUtil;
import net.sf.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * 建筑，房间 Controller
 *
 * @Author:李文光
 */
@RequestMapping("/build")
@Controller
public class BuildController {

    @Resource
    private BuildService buildService;
    @Resource
    private LogService logService;
    @Resource
    private EquipmentService equipmentService;
    /**
     * 新增或者修改建筑
     *
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("/savebuild")
    public ResultInfo savebuild(Build build) throws IOException {
        String info = "";
        if (StringUtil.isEmpty(build.getId())) {
            build.setId(UuidUtil.get32UUID());
            build.setCreatetime(DateUtil.getTime());
            info = "新增成功";
            logService.save(new Log(Log.ADD_ACTION, "新增:" + build));
        } else {

            info = "更新成功！";
            logService.save(new Log(Log.UPDATE_ACTION, "更新:" + build));
        }
        buildService.save(build);
        return new ResultInfo(StatusCode.SUCCESS, info);
    }


    /**
     * 根据项目id，建筑名 查询建筑信息
     *
     * @param name
     * @return
     */
    @ResponseBody
    @RequestMapping("/buildAllList")
    public ResultInfo buildAllList(String name, String projectId) {
        JSONArray datas = buildService.buildAllList(name, projectId);
        return new ResultInfo(StatusCode.SUCCESS, "成功！", datas);
    }


    /**
     * 根据项目id，建筑名 父级建筑ID   查询建筑房间号
     *
     * @param name      房间号
     * @param projectId 项目id
     * @param pid       父级建筑ID
     * @return
     */
    @ResponseBody
    @RequestMapping("/roomAllList")
    public ResultInfo roomAllList(String name, String projectId, String pid) {
        JSONArray datas = buildService.roomAllList(name, projectId, pid);
        return new ResultInfo(StatusCode.SUCCESS, "成功！", datas);
    }


    /**
     * 删除房间
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/deleteRoom")
    public ResultInfo deleteRoom(String id) {
        String info="";
        JSONArray datas = equipmentService.findByRoomId(id);
        if(datas.size()>0){
            info="该房间绑定有设备，不能删除！";
            return new ResultInfo(StatusCode.SUCCESS, info);
        }else{
            buildService.delete(id);
            info="删除成功!";
            return new ResultInfo(StatusCode.SUCCESS, info);
        }


    }
    /**
     * 删除建筑
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/deleteBuild")
    public ResultInfo deleteBuild(String id) {
        String info="";
        List<Build> datas = buildService.findRooms(id);
        if(datas.size()>0){
            info="该建筑有房间，不能删除！";
            return new ResultInfo(StatusCode.SUCCESS, info);
        }else{
            buildService.delete(id);
            info="删除成功!";
            return new ResultInfo(StatusCode.SUCCESS, info);
        }


    }



    /**
     * 批量删除房间号
     *
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping("/deleteRoomMany")
    public ResultInfo deleteRoomMany(String ids) {
        String info="";
        String falseData="";
        String TrueData="";
        int count=0;
        if (StringUtil.isNotEmpty(ids)) {
            String idStr[] = ids.split(",");
            for (int i = 0; i < idStr.length; i++) {
                JSONArray datas = equipmentService.findByRoomId(idStr[i]);
                if(datas.size()>0){
                    Build build=buildService.findById(idStr[i]);
                    falseData+=build.getName()+",";

                    count++;
                   continue;
                }else{
                    Build build=buildService.findById(idStr[i]);
                    TrueData+=build.getName()+",";
                    buildService.delete(idStr[i]);
                }
            }
        }
        if(count==0){
            return new ResultInfo(StatusCode.SUCCESS, "1",TrueData);
        }else{
            return new ResultInfo(StatusCode.SUCCESS, "0",falseData,TrueData);
        }

    }

    /**
     * 批量删除建筑
     *
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping("/deleteBuildMany")
    public ResultInfo deleteBuildMany(String ids) {
        String falseData="";
        int count=0;
        String TrueData="";
        if (StringUtil.isNotEmpty(ids)) {
            String idStr[] = ids.split(",");
            for (int i = 0; i < idStr.length; i++) {
                List<Build> datas = buildService.findRooms(idStr[i]);
                if(datas.size()>0){
                    Build build=buildService.findById(idStr[i]);
                    falseData+=build.getName()+",";
                    count++;
                    continue;
                }else{
                    Build build=buildService.findById(idStr[i]);
                    TrueData+=build.getName()+",";
                    buildService.delete(idStr[i]);
                }
            }
        }
        if(count==0){
            return new ResultInfo(StatusCode.SUCCESS, "1",TrueData);
        }else{
            return new ResultInfo(StatusCode.SUCCESS, "0",falseData,TrueData);
        }

    }


    /**
     * 建筑树形图（单个建筑）
     *@param projectId 根据项目查询
     * @return
     */
    @ResponseBody
    @RequestMapping("/buildTree")
    public ResultInfo buildTree(String projectId) {
        JSONArray datas = buildService.buildTree(projectId);
        return new ResultInfo(StatusCode.SUCCESS, "成功！", datas, null);
    }


    @ResponseBody
    @RequestMapping("/buildTreeByProject")
    public ResultInfo buildTreeByProject() {
        return new ResultInfo(StatusCode.SUCCESS, "成功！", buildService.buildTreeByProject());
    }


    /**
     * 多个建筑查询房间号
     *
     * @param ids 选择的建筑多ID
     */
    @ResponseBody
    @RequestMapping("/buildTreeList")
    public ResultInfo buildTreeList(String ids) {

        List<Build> datas = buildService.buildTreeList(ids);
        return new ResultInfo(StatusCode.SUCCESS, "成功！", datas, null);
    }

}
