package com.yhxc.utils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;



import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AddressLngLatExchange {

    //写到配置文件中
    private static final String KEY = "8f7e1fb73a319ef47fee87131858ab90";
    private static final String OUTPUT = "JSON";
    private static final String GET_LNG_LAT_URL = "http://restapi.amap.com/v3/geocode/geo";
    private static final String GET_ADDR_FROM_LNG_LAT = "http://restapi.amap.com/v3/geocode/regeo";
    private static final String EXTENSIONS_ALL = "all";

    private static final Logger LOGGER = Logger.getLogger(AddressLngLatExchange.class);
    /**
     *
     * @description 根据传进来的一个地址，查询对应的经纬度
     * @param
     * @return Pair<BigDecimal,BigDecimal> 左节点值为经度，右节点值为纬度
     * @author jxp
     * @date 2017年7月12日
     */
    public static String getLngLatFromOneAddr(String address) {

        if (StringUtils.isBlank(address)) {
            LOGGER.error("地址（" + address + "）为null或者空");
            return null;
        }

        Map<String, String> params = new HashMap<String, String>();
        params.put("address", address);
        params.put("output", OUTPUT);
        params.put("key", KEY);

        String result = HttpclientUtil.doPost(GET_LNG_LAT_URL,params);
        Pair<BigDecimal, BigDecimal> pair = null;

        // 解析返回的xml格式的字符串result，从中拿到经纬度
        // 调用高德API，拿到json格式的字符串结果
        JSONObject jsonObject = JSONObject.fromObject(result);
        // 拿到返回报文的status值，高德的该接口返回值有两个：0-请求失败，1-请求成功；
        int status = Integer.valueOf(jsonObject.getString("status"));
        String resPair="";
        if (status == 1) {
            JSONArray jsonArray = jsonObject.getJSONArray("geocodes");
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                String lngLat = json.getString("location");
                String[] lngLatArr = lngLat.split(",");
                // 经度
                BigDecimal longitude = new BigDecimal(lngLatArr[0]);
               System.out.println("经度" + longitude);
                // 纬度
                BigDecimal latitude = new BigDecimal(lngLatArr[1]);
                System.out.println("纬度" + latitude);
                resPair = longitude+","+ latitude;
            }

        } else {
            String errorMsg = jsonObject.getString("info");
            LOGGER.error("地址（" + address + "）" + errorMsg);
        }

        return resPair;
    }


    /**
     *
     * @description 根据经纬度查地址
     * @param lng：经度，lat：纬度
     * @return 地址
     * @author jxp
     * @date 2017年7月12日
     */
    public static String getAddrFromLngLat(String lng, String lat) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("location", lng + "," + lat);
        params.put("output", OUTPUT);
        params.put("key", KEY);
        params.put("extensions", EXTENSIONS_ALL);
        String result = HttpclientUtil.doPost(GET_ADDR_FROM_LNG_LAT,params);
        JSONObject json = JSONObject.fromObject(result);
        String status = json.getString("status");
        String address = null;
        if ("1".equals(status)) {
            JSONObject regeocode = JSONObject.fromObject(json.get("regeocode"));
            address = regeocode.getString("formatted_address");
        }
        return address;
    }


    public static void main(String[] args) {
        System.out.println(AddressLngLatExchange. getLngLatFromOneAddr("广州"));
    }

}
