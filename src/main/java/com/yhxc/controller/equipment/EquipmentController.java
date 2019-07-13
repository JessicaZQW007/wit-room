package com.yhxc.controller.equipment;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhxc.common.ResultInfo;
import com.yhxc.common.StatusCode;
import com.yhxc.entity.equipment.Equipment;
import com.yhxc.entity.equipment.EquipmentType;
import com.yhxc.entity.system.Log;
import com.yhxc.entity.system.User;
import com.yhxc.entity.unit.Unit;
import com.yhxc.service.aftersale.WarrantyService;
import com.yhxc.service.equipment.EquipmentService;
import com.yhxc.service.equipment.EquipmentTypeService;
import com.yhxc.service.system.LogService;
import com.yhxc.service.unit.UnitService;
import com.yhxc.utils.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * @Author: 赵贺飞
 * @Date: 2018/10/31 15:09
 */

@RequestMapping("/equipment")
@Controller
public class EquipmentController {
    @Resource
    private EquipmentService equipmentService;
    @Resource
    private LogService logService;
    @Resource
    private WarrantyService warrantyService;
    @Resource
    private EquipmentTypeService equipmentTypeService;
    @Resource
    private UnitService unitService;

    /**
     * 设备列表，分页
     *
     * @param equipment
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public ResultInfo list(Equipment equipment, Integer pageNum, Integer pageSize) {
        User u = Jurisdiction.getCurrentUser();

        if (u.getUserType()==1){
            //平台用户
            equipment.setUnitPid(u.getUnitId());

        }else if(u.getUserType()==2){
            //机构用户
            equipment.setUnitId(u.getUnitId());
        }


        PageHelper.startPage(pageNum, pageSize);
        List<Equipment> list = equipmentService.listEquipmentResPage(equipment, pageNum, pageSize);
        PageInfo<Equipment> page = new PageInfo<Equipment>(list);
        com.yhxc.common.PageInfo pi = new com.yhxc.common.PageInfo();
        pi.setPageNum(page.getPageNum());
        pi.setPageSize(page.getPageSize());
        pi.setTotal(page.getTotal());
        return new ResultInfo(StatusCode.SUCCESS, "SUCCESS", page.getList(), pi);
    }


    /**
     * 查询所有设备，不分页
     *
     * @return
     */
    @RequestMapping("/findAll")
    @ResponseBody
    public ResultInfo findAll(Equipment equipment) {
        return new ResultInfo(StatusCode.SUCCESS, "SUCCESS", equipmentService.listEquipmentRes(equipment));
    }



    /**
     * 查询所有的设备UUid和设备名称
     *
     * @return
     */
    @RequestMapping("/findUuidNameList")
    @ResponseBody
    public ResultInfo findUuidNameList(String  ename) {
        return new ResultInfo(StatusCode.SUCCESS, "SUCCESS", equipmentService.findUUidNameList(ename));
    }


    /**
     * 查询所有未绑定设备
     *
     * @return
     */
    @RequestMapping("/findweiBanding")
    @ResponseBody
    public ResultInfo findweiBanding(String uuid) {
        return new ResultInfo(StatusCode.SUCCESS, "SUCCESS", equipmentService.findweiBanding(uuid));
    }


    /**
     * 查看设备详情
     *
     * @param id
     * @return
     */
    @RequestMapping("/findId")
    @ResponseBody
    public ResultInfo findId(String id) {
        Equipment e = new Equipment();
        e.setId(id);
        return new ResultInfo(StatusCode.SUCCESS, "SUCCESS", equipmentService.listEquipmentRes(e));
    }

    /**
     * 根据设备UUID查询
     *
     * @param uuid
     * @return
     */
    @RequestMapping("/findByUUid")
    @ResponseBody
    public ResultInfo findByUUid(String uuid) {
        Equipment e = new Equipment();
        e.setUuid(uuid);
        return new ResultInfo(StatusCode.SUCCESS, "SUCCESS", equipmentService.findAll(e));
    }

    /**
     * 设备的新增和修改
     *
     * @param equipment
     * @return
     */
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public ResultInfo save(Equipment equipment) {
        String info="";
        if (StringUtil.isEmpty(equipment.getId())) {
            equipment.setId(UuidUtil.get32UUID());
            String uuid = equipment.getUuid();
            if (equipmentService.findByUUID(uuid) != null) {
                return new ResultInfo(StatusCode.FAIL, "设备编号已存在！");
            }

            equipment.setDeleted(0);
            equipment.setGroupName("交替运行");
            equipment.setCreateTime(DateUtil.getTime());
            logService.save(new Log(Log.ADD_ACTION, "新增设备" + "," + equipment));
            info="新增成功";
            //新增设备的同时，新增此设备的保修数据
            //warrantyService.save(new Warranty(uuid));
        } else {
            Equipment e = equipmentService.findById(equipment.getId());
            equipment.setDeleted(e.getDeleted());
            equipment.setCreateTime(e.getCreateTime());

            logService.save(new Log(Log.UPDATE_ACTION, "修改设备" + "," + equipment));
            info="修改成功";
        }
        equipmentService.saveOrUpdate(equipment);
        return new ResultInfo(StatusCode.SUCCESS, info);
    }






    /**
     * 修改设备名称
     *
     * @param newName 新的设备名称
     * @param name 设备名称
     * @return
     */
    @RequestMapping("/updateName")
    @ResponseBody
    public ResultInfo findById(String newName,String name) {
        equipmentService.updateName(newName,name);

        return new ResultInfo(StatusCode.SUCCESS, "修改成功");
    }



    /**
     * 根据设备表主键查询
     *
     * @param id
     * @return
     */
    @RequestMapping("/findById")
    @ResponseBody
    public ResultInfo findById(String id) {
        return new ResultInfo(StatusCode.SUCCESS, "SUCCESS", equipmentService.findById(id));
    }





    /**
     * 逻辑删除
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/deleted")
    public ResultInfo deleted(String id) {
        equipmentService.deleted(id);
        logService.save(new Log(Log.DELETE_ACTION, "删除设备" + "," + equipmentService.findById(id)));
        return new ResultInfo(StatusCode.SUCCESS, "删除成功！");
    }

    /**
     * 批量删除
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/deletedAll")
    public ResultInfo deletedAll(String ids) {
        if (StringUtil.isNotEmpty(ids)) {
            String[] idStr = ids.split(",");
            for (String id : idStr) {
                equipmentService.deleted(id);
                logService.save(new Log(Log.DELETE_ACTION, "删除设备" + "," + equipmentService.findById(id)));
            }
        }
        return new ResultInfo(StatusCode.SUCCESS, "删除成功！");
    }


    /**
     * 撤销删除
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/reduction")
    public ResultInfo reduction(String id) {
        equipmentService.reduction(id);
        return new ResultInfo(StatusCode.SUCCESS, "还原成功！");
    }


    /**
     * 设备分布统计(1:省，2是市 3：是区)
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/equipmentDis")
    public ResultInfo equipmentDis(int num) {
        List<Map> datas = null;
        if (num == 1) {

            datas = equipmentService.equipmentsheng();
        } else if (num == 2) {
            datas = equipmentService.equipmentshi();
        } else if (num == 3) {
            datas = equipmentService.equipmentqu();
        }


        return new ResultInfo(StatusCode.SUCCESS, "成功！", datas);
    }







    /**
     * 下载EXCEL表格
     *
     * @param response
     */
    @RequestMapping("/downExcel")
    public void downExecl(HttpServletResponse response) {
        Date date = new Date();
        String title = Tools.date2Str(date, "yyyyMMddHHmmss") + ".xls";// 导出文件的标题
        // 设置表格标题行
        String[] headers = new String[]{"序号", "设备编号（必填）","设备协议（必填）",  "设备名称（必填）", "设备型号（必填）", "设备品牌（必填）",  "生产时间（必填）","机构类型（必填）" ,"所属机构（必填）" ,"设备状态（必填）","备注"};
        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objects = {"1", "8687440313053832","COP协议",  "星辰智控器", "XC-RTU-JZJF", "星辰",  "2019-01-01 15:08:55","平台单位", "项目机构名称","启用","参考格式"};
        Object[] object2 = {"2", "8687440313053821","TCP协议", "星辰温控设备", "XC-RTU-JZJF", "星辰", "2019-01-01 15:08:55","项目机构", "项目机构名称","停用","参考格式"};
        dataList.add(0, objects);
        dataList.add(1, object2);
        OutputStream out = null;
        try {
            // 防止中文乱码
            String headStr = "attachment; filename=\"" + new String(title.getBytes("gb2312"), "ISO8859-1") + "\"";
            response.setContentType("octets/stream");
            response.setContentType("APPLICATION/OCTET-STREAM");
            response.setHeader("Content-Disposition", headStr);
            out = response.getOutputStream();
            ExportExcelSeedBack ex = new ExportExcelSeedBack(title, headers, dataList); // 没有标题
            ex.export(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }






    /**
     * 从EXCEL导入到数据库
     *
     * @param file
     * @return
     * @throws Exception
     */
    @RequestMapping("/importExcel")
    @ResponseBody
    public ResultInfo importExcel(@RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
        String webpath = null;
        webpath = UploadUtil.uploadLocal(file, "notice");
        File files = new File(webpath);
        FileInputStream fis = new FileInputStream(files);
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, String> m = new HashMap<String, String>();
        m.put("序号", "id");
        m.put("设备编号（必填）", "uuid");
        m.put("设备协议（必填）", "protocol");
        m.put("设备名称（必填）", "name");
        m.put("设备型号（必填）", "model");
        m.put("设备品牌（必填）", "brand");
        m.put("生产时间（必填）", "productionDate");
        m.put("机构类型（必填）", "unitType");
        m.put("所属机构（必填）", "unitName");
        m.put("设备状态（必填）", "state");
        m.put("备注", "remark");
        ImportExcel importExcelUti = new ImportExcel();
        List<Map<String, Object>> ls = importExcelUti.parseExcel(fis, files.getName(), m);
        String info = "";
        String message = "";
        String uuid = null;
        String exceprionUUid = "";
        int success = 0;
        String brand="";
        String model="";
        String ename="";
        String unitType="";
        String unitName="";
        int s=0;
        int total = ls.size();  // 要导入的条数
        int errorNum = 0;    // 要导入的条数
        if (ls != null) {
            for (int i = 0; i < ls.size(); i++) {
                uuid = ls.get(i).get("uuid").toString();
                if (StringUtil.isNotEmpty(uuid)) {
                    Equipment datas = equipmentService.findByUUID(uuid);
                    if (datas == null) {
                        Equipment equidMent = new Equipment();
                        equidMent.setId(UuidUtil.get32UUID());
                        equidMent.setUuid(uuid);

                        String protocol=(String)ls.get(i).get("protocol");
                        if(protocol.equals("COP协议")||protocol.equals("TCP协议")){
                            equidMent.setProtocol((String)ls.get(i).get("protocol"));
                        }else{
                            info="10";
                            break;
                        }


                        /*equidMent.setTransrate(Integer.valueOf((String) ls.get(i).get("transrate")));
                        equidMent.setVoltage(Integer.valueOf((String) ls.get(i).get("voltage")));*/


                        brand = (String) ls.get(i).get("brand");

                        model = (String) ls.get(i).get("model");

                        ename = (String) ls.get(i).get("name");


                        String brandid = equipmentTypeService.findBrandId(brand, model,ename);


                        String modelId = equipmentTypeService.findBrandId(brand, model,"");



                        JSONArray jsonArray1=equipmentTypeService.findEname(ename);
                        String enameId=jsonArray1.getJSONObject(0).getString("id");


                        if (StringUtil.isNotEmpty(brandid)) {
                            equidMent.setBrandId(brandid);
                        } else {

                            info = "3";
                            break;
                        }

                        if (StringUtil.isNotEmpty(modelId)) {
                            equidMent.setEqTypeId(modelId);
                        } else {

                            info = "3";
                            break;
                        }

                        if (StringUtil.isNotEmpty(enameId)) {
                            equidMent.setEnameId(enameId);
                        } else {

                            info = "3";
                            break;
                        }


                        unitType = (String) ls.get(i).get("unitType");

                        unitName = (String) ls.get(i).get("unitName");


                        if(unitType.equals("平台单位")){
                            unitType="1";
                            equidMent.setUnitType("1");
                        }else if(unitType.equals("项目机构")){
                            unitType="2";
                            equidMent.setUnitType("2");
                        }else{
                            info="11";
                            break;
                        }

                        Unit unit =unitService.findByNameType(unitName,unitType);
                        if(unit!=null) {
                            equidMent.setUnitId(unit.getId());
                        }else{
                            info="12";
                            break;
                        }
                        String state=(String) ls.get(i).get("state");
                        System.out.println("state="+state);

                        if(state.equals("启用")){
                            s=1;
                        }else if(state.equals("停用")){
                            s=2;
                        }else{
                            info="13";
                            break;
                        }


                        equidMent.setState(s);

                        /*equidMent.setNbCard((String) ls.get(i).get("nbCard"));*/
                        equidMent.setName((String) ls.get(i).get("name"));
                        equidMent.setProductionDate((String) ls.get(i).get("productionDate"));
                        equidMent.setRemark((String) ls.get(i).get("remark"));
                        equidMent.setDeleted(0);
                        equidMent.setGroupName("交替运行");
                        equidMent.setCreateTime(DateUtil.getTime());
                        equipmentService.saveOrUpdate(equidMent);
                        success++;
                        info = "1";
                    } else {
                        exceprionUUid += uuid + ",";
                        errorNum++;
                        info = "2";
                    }
                }
            }
        }
        resultMap.put("total", total);      // 导入的总条数
        resultMap.put("success", success);  // 导入成功的数量
        resultMap.put("errorNum", errorNum);     // 已存在的设备数量
        resultMap.put("error", "已存在的设备编号：" + exceprionUUid);//已存在的设备
        if (info.equals("3")) {
            message = "不存在这个"+ename+"/"+model+"/"+brand+"型号,导入失败!!!";
        }else if(info.equals("10")){
            message = "设备协议只能是“COP协议”和“TCP协议,导入失败！！！”";
        }else if(info.equals("11")){
            message = "机构类型只能是“平台单位”和“项目机构”，导入失败！！！";
        }else if(info.equals("12")){
            message = "不存在这个"+"("+unitName+")"+"机构,导入失败!!!";
        }else if(info.equals("13")){
            message = "设备状态只能是“启用”和“停用”，导入失败！！！";
        } else {
            if (errorNum > 0 && success > 0) {
                message = "导入成功!<br/>导入条数" + total + "条,成功" + success + "条,失败" + errorNum + "条。<br/>原因："
                        + "已存在的设备编号：" + exceprionUUid.substring(0, exceprionUUid.length() - 1);
            } else if (errorNum > 0) {
                message = "导入失败!已存在的设备编号：" + exceprionUUid.substring(0, exceprionUUid.length() - 1);
            } else {
                message = "导入成功!";
            }
        }
        FileUtil.delFile(webpath);// 删除文件
        return new ResultInfo(StatusCode.SUCCESS, info, message);
    }


    /**
     * 导出Excel 设备
     *
     * @author 李文光
     */
    @RequestMapping("/exportExcel")
    @ResponseBody
    public ModelAndView exportExcel1(Equipment equipment) throws Exception {
        ModelAndView mv = new ModelAndView();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        List<String> titles = new ArrayList<String>();
        titles.add("设备编号"); // 1
        titles.add("设备协议"); // 2
        titles.add("设备名称"); // 3
        titles.add("设备品牌"); // 4
        titles.add("设备型号"); // 5
        titles.add("生产时间"); // 6
        titles.add("机构类型"); // 7
        titles.add("所属机构"); // 8
        titles.add("设备状态"); // 9
        titles.add("备注"); // 10
        dataMap.put("titles", titles);
        List<Equipment> datas = null;
        datas = equipmentService.listEquipmentRes(equipment);
        List<PageData> varList = new ArrayList<PageData>();
        for (int i = 0; i < datas.size(); i++) {
            PageData map = new PageData();
            map.put("var1", datas.get(i).getUuid());
            map.put("var2", datas.get(i).getProtocol());
            map.put("var3", datas.get(i).getName());
            String eqId = datas.get(i).getBrandId();
            EquipmentType equipmentType = equipmentTypeService.findById(eqId);
            map.put("var4", equipmentType.getBrand());
            map.put("var5", equipmentType.getModel());
            map.put("var6", String.valueOf(datas.get(i).getProductionDate()));

            String unitId=datas.get(i).getUnitId();
            Unit unit=unitService.findOneById(unitId);
            String unittype=unit.getType();
            if(unittype.equals("1")){
                unittype="平台单位";
            }else if(unittype.equals("2")){
                unittype="项目机构";
            }


            map.put("var7", unittype);


            map.put("var8", unit.getName());
            int state=datas.get(i).getState();
            String stateName="";
            if(state==1){
                stateName="启用";
            }else if(state==2){
                stateName="停用";
            }

            map.put("var9", stateName);
            map.put("var10", datas.get(i).getRemark());
            varList.add(map);
        }
        dataMap.put("varList", varList);
        ExportExcel erv = new ExportExcel();
        mv = new ModelAndView(erv, dataMap);
        return mv;

    }

    /**
     * 分页查询所有项目 的状态
     *
     * @param address  地址
     * @param pname    项目名
     * @param allDate  创建时间
     * @param pageNum  当前页
     * @param pageSize 当前数量
     * @return
     */
    @ResponseBody
    @RequestMapping("/pageList")
    public ResultInfo pageList(String projectType, String address, String pname, String allDate, String runStatus, int pageNum, int pageSize) {
        JSONObject datas = equipmentService.pageList(projectType, address, pname, allDate, runStatus, pageNum, pageSize);
        return new ResultInfo(StatusCode.SUCCESS, "成功！", datas);
    }
    /**
     * 绑定定时任务
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateGroup_name")
    public ResultInfo updateGroup_name(String groupName, String uuid) {
         equipmentService.updateGroup_name(groupName,uuid);
        return new ResultInfo(StatusCode.SUCCESS, "成功！" );
    }

    /**
     * 根据uuid查询定时任务组
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/findByuuidGroup")
    public ResultInfo findByuuidGroup( String uuid) {

        return new ResultInfo(StatusCode.SUCCESS, "成功！" , equipmentService.findByuuidGroup(uuid));
    }


    /**
     * 根据机构ID（UnitId）查询未绑定的设备
     *
     * @return
     */
    @RequestMapping("/findByUnitId")
    @ResponseBody
    public ResultInfo findByUnitId(String unitId,String uuid) {
        return new ResultInfo(StatusCode.SUCCESS, "SUCCESS", equipmentService.findByUnitId(unitId,uuid));
    }







}
