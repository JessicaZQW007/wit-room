package com.yhxc.service.fault.impl;

import com.yhxc.entity.fault.Fault;
import com.yhxc.entity.fault.FaultSet;
import com.yhxc.entity.fault.Report;
import com.yhxc.entity.send.ReceiveData;
import com.yhxc.repository.fault.FaultRepository;
import com.yhxc.repository.fault.FaultSetRepository;
import com.yhxc.repository.fault.ReportRepository;
import com.yhxc.repository.project.ProjectRepository;
import com.yhxc.service.fault.FaultReportService;
import com.yhxc.service.fault.FaultService;
import com.yhxc.service.fault.FaultSetService;
import com.yhxc.utils.DateCont;
import com.yhxc.utils.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: 刘俊涛
 * @Date: 2019/3/23 10:51
 */
@Service
public class FaultSetServiceImpl implements FaultSetService {

    @Resource
    FaultSetRepository faultsetRepository;
    @Resource
    FaultRepository faultRepository;
    @Resource
    FaultReportService faultReportService;

    @Resource
    ProjectRepository projectRepository;
    @Override
    public void save(FaultSet faultSet) {
        faultsetRepository.save(faultSet);
    }

    @Override
    public FaultSet findByUuid(String uuid) {
        return faultsetRepository.findByUuid(uuid);
    }

    @Override
    public void saveFalutReport(String date) {
       List<?>  datas=faultsetRepository.findAllList(date);
       if(datas!=null) {
           for (int k = 0; k < datas.size(); k++) {
               Object[] object1 = (Object[]) datas.get(k);
               String uuid=object1[1].toString();
               String errordatas = object1[2].toString();
               String createtime = object1[0].toString();
               if (errordatas != null) {
                   String[] dataArray = errordatas.split(",");
                   for (int i = 0; i < dataArray.length; i++) {
                       String intNumber = dataArray[i].substring(dataArray[i].indexOf("=") + 1, dataArray[i].length());
                       if (intNumber.equals("1")) {
                           String message = dataArray[i].substring(0, dataArray[i].indexOf("="));
                           String code="";
                           String airId="";
                           if(message.equals("EPPRAM故障")){
                               code="F08";
                               airId="0";
                               Report report = new Report();
                               report.setAirId(airId);
                               report.setCode(code);
                               report.setUuid(uuid);
                               report.setCreatetime(createtime);

                               String id=faultRepository.findreport(uuid,airId,code);
                               if(id!=null){
                                   int id1=Integer.parseInt(id);
                                   faultRepository.updateTime(createtime,id1);
                               }else{
                                   faultReportService.save(report);
                               }

                           }else if(message.equals("SHT2x传感器故障")){
                               code="F09";
                               airId="0";
                               Report report = new Report();
                               report.setAirId(airId);
                               report.setCode(code);
                               report.setUuid(uuid);
                               report.setCreatetime(createtime);

                               String id=faultRepository.findreport(uuid,airId,code);
                               if(id!=null){
                                   int id1=Integer.parseInt(id);
                                   faultRepository.updateTime(createtime,id1);
                               }else{
                                   faultReportService.save(report);
                               }

                           }
                           else if(message.equals("空调1冷媒报警")){
                               code="F10";
                               airId="1";
                               Report report = new Report();
                               report.setAirId(airId);
                               report.setCode(code);
                               report.setUuid(uuid);
                               report.setCreatetime(createtime);

                               String id=faultRepository.findreport(uuid,airId,code);
                               if(id!=null){
                                   int id1=Integer.parseInt(id);
                                   faultRepository.updateTime(createtime,id1);
                               }else{
                                   faultReportService.save(report);
                               }

                           }
                           else if(message.equals("空调2冷媒报警")){
                               code="F11";
                               airId="2";
                               Report report = new Report();
                               report.setAirId(airId);
                               report.setCode(code);
                               report.setUuid(uuid);
                               report.setCreatetime(createtime);

                               String id=faultRepository.findreport(uuid,airId,code);
                               if(id!=null){
                                   int id1=Integer.parseInt(id);
                                   faultRepository.updateTime(createtime,id1);
                               }else{
                                   faultReportService.save(report);
                               }

                           }
                           else if(message.equals("空调1电流报警")){
                               code="F12";
                               airId="1";
                               Report report = new Report();
                               report.setAirId(airId);
                               report.setCode(code);
                               report.setUuid(uuid);
                               report.setCreatetime(createtime);

                               String id=faultRepository.findreport(uuid,airId,code);
                               if(id!=null){
                                   int id1=Integer.parseInt(id);
                                   faultRepository.updateTime(createtime,id1);
                               }else{
                                   faultReportService.save(report);
                               }

                           }
                           else if(message.equals("空调2电流报警")){
                               code="F13";
                               airId="2";
                               Report report = new Report();
                               report.setAirId(airId);
                               report.setCode(code);
                               report.setUuid(uuid);
                               report.setCreatetime(createtime);
                               String id=faultRepository.findreport(uuid,airId,code);
                               if(id!=null){
                                   int id1=Integer.parseInt(id);
                                   faultRepository.updateTime(createtime,id1);
                               }else{
                                   faultReportService.save(report);
                               }
                           }
                           else if(message.equals("空调1压缩机报警")){
                               code="F14";
                               airId="1";
                               Report report = new Report();
                               report.setAirId(airId);
                               report.setCode(code);
                               report.setUuid(uuid);
                               report.setCreatetime(createtime);
                               String id=faultRepository.findreport(uuid,airId,code);
                               if(id!=null){
                                   int id1=Integer.parseInt(id);
                                   faultRepository.updateTime(createtime,id1);
                               }else{
                                   faultReportService.save(report);
                               }
                           }
                           else if(message.equals("空调2压缩机报警")){
                               code="F15";
                               airId="2";
                               Report report = new Report();
                               report.setAirId(airId);
                               report.setCode(code);
                               report.setUuid(uuid);
                               report.setCreatetime(createtime);

                               String id=faultRepository.findreport(uuid,airId,code);
                               if(id!=null){
                                   int id1=Integer.parseInt(id);
                                   faultRepository.updateTime(createtime,id1);
                               }else{
                                   faultReportService.save(report);
                               }
                           }


                       }
                   }
               }
           }
       }
    }

    @Override
    public void saveReport(String date) {
        List<?>  datas=faultsetRepository.findAllList(date);
        if(datas!=null) {
            for (int k = 0; k < datas.size(); k++) {
                Object[] object1 = (Object[]) datas.get(k);
                String uuid=object1[1].toString();
                String projectId = object1[8].toString();
                String createtime = object1[0].toString();
                Double airCurrent1=0.0;
                Double airCurrent2=0.0;
                String st1= faultsetRepository.findcurrent(projectId,1);//空调1额定电流
                if(StringUtil.isEmpty(st1)){
                    airCurrent1=0.0;
                }else {
                    airCurrent1=Double.valueOf(st1);
                }
               String st2= faultsetRepository.findcurrent(projectId,2);//空调2额定电流
               if(StringUtil.isEmpty(st2)){
                    airCurrent2=0.0;
               }else {
                    airCurrent2=Double.valueOf(st2);
               }
                String ktccurrent1 = object1[3].toString();//空调1温度
                String ktccurrent2 = object1[4].toString();//空调2温度
                String sfktemp1 = object1[5].toString();//空调1回风温度
                String sfktemp2 = object1[6].toString();//空调2回风温度
                String hjtemp = object1[7].toString();//环境温度
                String code="";
                String airId="";
           int  airNum=projectRepository.findProjectAirNumbyId(projectId);//空调数量
                if(airNum==1){
                    if(Double.valueOf(ktccurrent1)<0){
                        code="F03";
                        airId="1";
                        Report report = new Report();
                        report.setAirId(airId);
                        report.setCode(code);
                        report.setUuid(uuid);
                        report.setCreatetime(createtime);

                        String id=faultRepository.findreport(uuid,airId,code);
                        if(id!=null){
                            int id1=Integer.parseInt(id);
                            faultRepository.updateTime(createtime,id1);
                        }else{
                            faultReportService.save(report);
                        }

                    } else if(Double.valueOf(ktccurrent1)>airCurrent1){
                        code="F05";
                        airId="1";
                        Report report = new Report();
                        report.setAirId(airId);
                        report.setCode(code);
                        report.setUuid(uuid);
                        report.setCreatetime(createtime);

                        String id=faultRepository.findreport(uuid,airId,code);
                        if(id!=null){
                            int id1=Integer.parseInt(id);
                            faultRepository.updateTime(createtime,id1);
                        }else{
                            faultReportService.save(report);
                        }

                    } else if(Double.valueOf(sfktemp1)==-40){
                        code="F01";
                        airId="1";
                        Report report = new Report();
                        report.setAirId(airId);
                        report.setCode(code);
                        report.setUuid(uuid);
                        report.setCreatetime(createtime);

                        String id=faultRepository.findreport(uuid,airId,code);
                        if(id!=null){
                            int id1=Integer.parseInt(id);
                            faultRepository.updateTime(createtime,id1);
                        }else{
                            faultReportService.save(report);
                        }

                    } else if(Double.valueOf(hjtemp)==-40){
                        code="F07";
                        airId="0";
                        Report report = new Report();
                        report.setAirId(airId);
                        report.setCode(code);
                        report.setUuid(uuid);
                        report.setCreatetime(createtime);

                        String id=faultRepository.findreport(uuid,airId,code);
                        if(id!=null){
                            int id1=Integer.parseInt(id);
                            faultRepository.updateTime(createtime,id1);
                        }else{
                            faultReportService.save(report);
                        }

                    }
                }else if(airNum==2){
                    if (StringUtil.isNotEmpty(ktccurrent1)||StringUtil.isNotEmpty(ktccurrent2)) {
                        if(Double.valueOf(ktccurrent1)<0){
                            code="F03";
                            airId="1";
                            Report report = new Report();
                            report.setAirId(airId);
                            report.setCode(code);
                            report.setUuid(uuid);
                            report.setCreatetime(createtime);

                            String id=faultRepository.findreport(uuid,airId,code);
                            if(id!=null){
                                int id1=Integer.parseInt(id);
                                faultRepository.updateTime(createtime,id1);
                            }else{
                                faultReportService.save(report);
                            }

                        }else if(Double.valueOf(ktccurrent2)<0){
                            code="F04";
                            airId="2";
                            Report report = new Report();
                            report.setAirId(airId);
                            report.setCode(code);
                            report.setUuid(uuid);
                            report.setCreatetime(createtime);

                            String id=faultRepository.findreport(uuid,airId,code);
                            if(id!=null){
                                int id1=Integer.parseInt(id);
                                faultRepository.updateTime(createtime,id1);
                            }else{
                                faultReportService.save(report);
                            }
                        }
                        else if(Double.valueOf(ktccurrent1)>airCurrent1){
                            code="F05";
                            airId="1";
                            Report report = new Report();
                            report.setAirId(airId);
                            report.setCode(code);
                            report.setUuid(uuid);
                            report.setCreatetime(createtime);
                            String id=faultRepository.findreport(uuid,airId,code);
                            if(id!=null){
                                int id1=Integer.parseInt(id);
                                faultRepository.updateTime(createtime,id1);
                            }else{
                                faultReportService.save(report);
                            }
                        }
                        else if(Double.valueOf(ktccurrent2)>airCurrent2){
                            code="F06";
                            airId="2";
                            Report report = new Report();
                            report.setAirId(airId);
                            report.setCode(code);
                            report.setUuid(uuid);
                            report.setCreatetime(createtime);
                            String id=faultRepository.findreport(uuid,airId,code);
                            if(id!=null){
                                int id1=Integer.parseInt(id);
                                faultRepository.updateTime(createtime,id1);
                            }else{
                                faultReportService.save(report);
                            }
                        }
                        else if(Double.valueOf(sfktemp1)==-40){
                            code="F01";
                            airId="1";
                            Report report = new Report();
                            report.setAirId(airId);
                            report.setCode(code);
                            report.setUuid(uuid);
                            report.setCreatetime(createtime);
                            String id=faultRepository.findreport(uuid,airId,code);
                            if(id!=null){
                                int id1=Integer.parseInt(id);
                                faultRepository.updateTime(createtime,id1);
                            }else{
                                faultReportService.save(report);
                            }
                        }
                        else if(Double.valueOf(sfktemp2)==-40){
                            code="F02";
                            airId="2";
                            Report report = new Report();
                            report.setAirId(airId);
                            report.setCode(code);
                            report.setUuid(uuid);
                            report.setCreatetime(createtime);
                            String id=faultRepository.findreport(uuid,airId,code);
                            if(id!=null){
                                int id1=Integer.parseInt(id);
                                faultRepository.updateTime(createtime,id1);
                            }else{
                                faultReportService.save(report);
                            }
                        }
                        else if(Double.valueOf(hjtemp)==-40){
                            code="F07";
                            airId="0";
                            Report report = new Report();
                            report.setAirId(airId);
                            report.setCode(code);
                            report.setUuid(uuid);
                            report.setCreatetime(createtime);
                            String id=faultRepository.findreport(uuid,airId,code);
                            if(id!=null){
                                int id1=Integer.parseInt(id);
                                faultRepository.updateTime(createtime,id1);
                            }else{
                                faultReportService.save(report);
                            }
                        }
                    }
                }

                        }

                }
            }




}
