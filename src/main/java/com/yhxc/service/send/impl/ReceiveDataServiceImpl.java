package com.yhxc.service.send.impl;

import com.yhxc.entity.equipment.Equipment;
import com.yhxc.entity.send.ReceiveData;
import com.yhxc.mapper.send.ReceiveDataMapper;
import com.yhxc.repository.equipment.EquipmentRepository;
import com.yhxc.repository.send.ReceiveDataRepository;
import com.yhxc.service.send.ReceiveDataService;
import com.yhxc.utils.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: 刘俊涛
 * @Date: 2019/3/20 14:37
 *  分体空调接收数据实现类
 */
@Service("ReceiveDataService")
public class ReceiveDataServiceImpl implements ReceiveDataService {

    @Resource
    ReceiveDataMapper receiveDataMapper;

    @Resource
    EquipmentRepository equipmentRepository;
    @Resource
    ReceiveDataRepository receiveDataRepository;

    @PersistenceContext
    private EntityManager em;
    /**
     *  @Cacheable 可以标记在一个方法上，也可以标记在一个类上，当标记在一个方法上时表示该方法是支持缓存的，
     * 当标记在一个类上时则表示该类所有的方法都是支持缓存的
     * @Cacheable 可以指定三个属性，value、key和condition。
     * value属性指定cache的名称（即选择ehcache.xml中哪种缓存方式存储）
     * key属性是用来指定Spring缓存方法的返回结果时对应的key的。该属性支持SpringEL表达式。当我们没有指定该属性时，
     * Spring将使用默认策略生成key。我们也直接使用“#参数名”或者“#p参数index”
     *
     * @CacheEvict 是用来标注在需要清除缓存元素的方法或类上的。当标记在一个类上时表示其中所有的方法的执行都会触发缓存的清除操作。
     * @CacheEvict 可以指定的属性有value、key、condition、allEntries和beforeInvocation。
     * 其中value、key和condition的语义与@Cacheable对应的属性类似；allEntries是boolean类型，表示是否需要清除缓存中的所有元素。默认为false，表示不需要。
     * 当指定了allEntries为true时，Spring Cache将忽略指定的key。有的时候我们需要Cache一下清除所有的元素，这比一个一个清除元素更有效率。
     */
    @Override
//    @Cacheable(value = "SystemCache",key = "0")
    public ReceiveData findAllList(String uuid) {
        return receiveDataMapper.findAllList(uuid);
    }

    @Override
//    @Cacheable(value = "SystemCache",key = "2")
    public ReceiveData findNnm1(String uuid) {
        return receiveDataMapper.findNnm1(uuid);
    }

    @Override
//    @Cacheable(value = "SystemCache",key = "3")
    public ReceiveData findNnm2(String uuid) {
        return receiveDataMapper.findNnm2(uuid);
    }

    @Override
    public JSONArray findTemps(String someDate,String uuid,int num) {
        JSONArray jsonArray = new JSONArray();
        List<?> datas=null;
        if (num==1){
            datas = receiveDataRepository.findTempsNum1(someDate,uuid);
            for (int i = 0;i<datas.size();i++){
                Object[] objects = (Object[]) datas.get(i);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("hjtemp",objects[0]);
                jsonObject.put("swtemp",objects[1]);
                jsonObject.put("swhumi",objects[2]);
                jsonObject.put("sfktemp",objects[3]);
                jsonObject.put("receive_date",objects[4]);
                jsonArray.add(jsonObject);
            }
            return jsonArray;
        }else if (num==2){
            datas = receiveDataRepository.findTempsNum2(someDate,uuid);
            for (int i = 0;i<datas.size();i++){
                Object[] objects = (Object[]) datas.get(i);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("hjtemp",objects[0]);
                jsonObject.put("swtemp",objects[1]);
                jsonObject.put("swhumi",objects[2]);
                jsonObject.put("sfktemp",objects[3]);
                jsonObject.put("receive_date",objects[4]);
                jsonArray.add(jsonObject);
            }
            return jsonArray;
        }
            return jsonArray;
    }

    @Override
    public JSONArray findIndoorTemps(String someDate, String uuid, int num) {
        JSONArray jsonArray = new JSONArray();
        //airnum 第几台空调
        String dataSql="SELECT re.hjtemp,re.sfktemp" +num+
                ", substr(re.receive_date,12,8) receive_date FROM s_receive_data re WHERE   " ;

        if(StringUtil.isNotEmpty(someDate)) {
            dataSql += "  substr(re.receive_date,1,10) =:someDate  ";
        }
        if(StringUtil.isNotEmpty(uuid)) {
            dataSql += " AND uuid=:uuid ";
        }

        Query dataQuery = em.createNativeQuery(dataSql);
        if(StringUtil.isNotEmpty(uuid)) {
            dataQuery.setParameter("uuid", uuid);
        }
        if(StringUtil.isNotEmpty(someDate)) {
            dataQuery.setParameter("someDate", someDate);
        }
        List<?> datas = dataQuery.getResultList();
            for (int i = 0;i<datas.size();i++){
                Object[] objects = (Object[]) datas.get(i);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("hjtemp",objects[0]);
                jsonObject.put("sfktemp",objects[1]);
                jsonObject.put("receive_date",objects[2]);
                jsonArray.add(jsonObject);
            }
            return jsonArray;

    }

    @Override
    public JSONArray findOutdoorTempAndHumidity(String someDate, String uuid) {
        JSONArray jsonArray = new JSONArray();
        List<?> datas = receiveDataRepository.findOutdoorTempAndHumidity(someDate,uuid);
            for (int i = 0;i<datas.size();i++){
                Object[] objects = (Object[]) datas.get(i);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("swtemp",objects[0]);
                jsonObject.put("swhumi",objects[1]);
                jsonObject.put("receive_date",objects[2]);
                jsonArray.add(jsonObject);
            }
            return jsonArray;
    }

    @Override
    public JSONArray findDoorTempAndHumidity(String someDate, String uuid) {
        JSONArray jsonArray = new JSONArray();
        List<?> datas = receiveDataRepository.findDoorTempAndHumidity(someDate,uuid);
        for (int i = 0;i<datas.size();i++){
            Object[] objects = (Object[]) datas.get(i);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("hjtemp",objects[0]);
            jsonObject.put("swtemp",objects[1]);
            jsonObject.put("swhumi",objects[2]);
            jsonObject.put("receive_date",objects[3]);
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    @Override
    public JSONArray findTempsNum(String someDate, String uuid, int num) {
        JSONArray jsonArray = new JSONArray();
        //airnum 第几台空调
        String dataSql="SELECT re.sfktemp" +num+
                ", substr(re.receive_date,12,8) receive_date FROM s_receive_data re WHERE   " ;

        if(StringUtil.isNotEmpty(someDate)) {
            dataSql += "  substr(re.receive_date,1,10) =:someDate  ";
        }
        if(StringUtil.isNotEmpty(uuid)) {
            dataSql += " AND uuid=:uuid ";
        }

        Query dataQuery = em.createNativeQuery(dataSql);
        if(StringUtil.isNotEmpty(uuid)) {
            dataQuery.setParameter("uuid", uuid);
        }
        if(StringUtil.isNotEmpty(someDate)) {
            dataQuery.setParameter("someDate", someDate);
        }
        List<?> datas = dataQuery.getResultList();
            for (int i = 0;i<datas.size();i++){
                Object[] objects = (Object[]) datas.get(i);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("sfktemp",objects[0]);
                jsonObject.put("receive_date",objects[1]);
                jsonArray.add(jsonObject);
            }
            return jsonArray;

    }

    @Override
    public Double findTotalpower(String uuid) {
          int  transrate= equipmentRepository.findtransrate(uuid);
        String Totalpower=receiveDataRepository.findTotalpower(uuid);
        Double electricityNum=0.0;
        if(StringUtil.isNotEmpty(Totalpower)){
            electricityNum=Double.valueOf(Totalpower)*Double.valueOf(transrate);
            BigDecimal b = new BigDecimal(electricityNum);
            electricityNum = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
            System.out.println(electricityNum);
            return electricityNum;
        }else {
            return electricityNum;
        }

    }

    @Override
    public JSONArray findAirApp(String uuid,String project_id,int airnum) {
        JSONArray jsonArray = new JSONArray();
                //airnum 第几台空调
        String dataSql="SELECT s.id," +
                "s.sfktemp" +airnum+
                ",s.kt_run_model" +airnum+
                ",s.kt_set_fs" +airnum+
                ",s.kt_set_fx" +airnum+
                ",s.kt_switch" +airnum+
                ",s.ktccurrent" +airnum+
                ",s.kt_set_temp" +airnum+
                ", s.sys_lock,s.xin_fen,s.board_data_report_interval, " +
                "  s.hjtemp,s.swhumi,s.swtemp,ifnull(b.img,''),b.brand,b.model,s.currenta,s.currentb,s.currentc,s.voltagea,s.voltageb,s.voltagec,s.relaycontrolmark ,s.run_rule,s.hight_temp,s.lower_temp,s.receive_date from s_receive_data  s, p_air p,p_ari_brand b WHERE  " +
                "   p.brand_id=b.id  and p.air_name="+ airnum ;

        if(StringUtil.isNotEmpty(uuid)) {
            dataSql += "  and  s.id=(SELECT MAX(id) from s_receive_data WHERE uuid = :uuid )  ";
        }
        if(StringUtil.isNotEmpty(project_id)) {
            dataSql += " and p.project_id = :project_id  ";
        }

        // setFirstResults:数据从第几个开始显示（currentPage-1）*PageSize
        // setMaxResults：每页显示的数据数量PageSize
        Query dataQuery = em.createNativeQuery(dataSql);

        if(StringUtil.isNotEmpty(uuid)) {
            dataQuery.setParameter("uuid", uuid);
        }
        if(StringUtil.isNotEmpty(project_id)) {
            dataQuery.setParameter("project_id", project_id);
        }
        List<?> datas = dataQuery.getResultList();
        for (int i = 0;i<datas.size();i++){
            Object[] objects = (Object[]) datas.get(i);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",objects[0]);
            jsonObject.put("sfktemp",objects[1]);
            jsonObject.put("kt_run_model",objects[2]);
            jsonObject.put("kt_set_fs",objects[3]);
            jsonObject.put("kt_set_fx",objects[4]);
            jsonObject.put("kt_switch",objects[5]);
            jsonObject.put("ktccurrent",objects[6]);
            jsonObject.put("kt_set_temp",objects[7]);
            jsonObject.put("sys_lock",objects[8]);
            jsonObject.put("xin_fen",objects[9]);
            jsonObject.put("board_data_report_interval",objects[10]);
            jsonObject.put("hjtemp",objects[11]);
            jsonObject.put("swhumi",objects[12]);
            jsonObject.put("swtemp",objects[13]);
            jsonObject.put("img",objects[14]);
            jsonObject.put("brand",objects[15]);
            jsonObject.put("model",objects[16]);
            jsonObject.put("currenta",objects[17]);
            jsonObject.put("currentb",objects[18]);
            jsonObject.put("currentc",objects[19]);
            jsonObject.put("voltagea",objects[20]);
            jsonObject.put("voltageb",objects[21]);
            jsonObject.put("voltagec",objects[22]);
            jsonObject.put("relaycontrolmark",objects[23]);
            jsonObject.put("run_rule",objects[24]);
            jsonObject.put("hight_temp",objects[25]);
            jsonObject.put("lower_temp",objects[26]);
            jsonObject.put("receive_date",objects[27]);
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }


    @Override
    public JSONArray findcurrent(String uuid, String date) {
        JSONArray jsonArray = new JSONArray();
        List<?> datas = receiveDataRepository.findcurrent(uuid, date);
        for (int i = 0; i < datas.size(); i++) {
            Object[] objects = (Object[]) datas.get(i);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("currenta", objects[0]);
            jsonObject.put("currentb", objects[1]);
            jsonObject.put("currentc", objects[2]);
            jsonObject.put("receive_date", objects[3]);
            jsonArray.add(jsonObject);
        }
        return jsonArray;

    }
}
