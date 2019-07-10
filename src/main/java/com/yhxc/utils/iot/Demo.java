
package com.yhxc.utils.iot;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * HttpClientTest.java
 * @author 
 * @email 
 * @date 2017年11月30日 上午11:46:15
 * @version 1.0
 */
public class Demo {

	public static void main(String[] args) {
		//鉴权登录，调用其他接口前请先登录以获得accessToken
		//login();
		String zzz=login();
		System.out.println("登陆的密码为："+zzz);
		/*String command="123456";

		commandControl(command);
		*/
		//IOT接入点列表
		//getIOTServers();
		
		//IOT业务模式列表
//		getServiceModes();
		
		//单个设备注册
		//registerDev();
		
		//批量注册
		//registerDevBatch();
		
		//更新设备
		//updateDev();
		
		//查询设备
		//queryDev();
		
		//删除设备
		//deleteDev();
		
		//获取设备类型(产品型号)列表
		//getDeviceTypes();
		
		//查询设备类型(产品型号)
		//getDeviceType();
		
		//获取设备列表
		//getDevices();
		
		//注册订阅地址
		subscribe();
		
		//取消订阅
		//unsubscribe();
		
		//查询订阅地址
		querySubscribe();
		
		//查询设备业务数据历史记录
		//deviceHistoryData();
	}

	/**
	 * 登录验证
	 */
	public static String  login(){
		String charset="utf-8";
		String contentType="application/json";
		//请求参数json字符串
		String reqBody=null;
		//请求地址
		String url="https://www.easy-iot.cn/idev/3rdcap/server/login";
		//参数serverId
		String serverId="gzxcdev01";
		//参数password
		String password="zn2kXMtI";
		String aaa="";
		
		//1、使用其他JSON工具，如：com.alibaba.fastjson.JSON，version=1.2.7
		JSONObject json = new JSONObject();
		json.put("serverId", serverId);
		json.put("password", password);
		reqBody=json.toJSONString();
		//2、自己拼装json结构字符串
		//reqBody="{\"serverId\":\""+serverId+"\",\"password\":\""+password+"\"}";
		
		//使用HttpClient访问接口
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		StringEntity bodyEntity = new StringEntity(reqBody, charset);
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Content-type", contentType);
		httpPost.setEntity(bodyEntity);
		try {
			CloseableHttpResponse resp = httpClient.execute(httpPost);
			HttpEntity entity = resp.getEntity();
			if (resp.getStatusLine().getStatusCode()==200) {
				String responseData = EntityUtils.toString(entity);
				JSONObject respJson = JSONObject.parseObject(responseData);
				if ("0".equals(respJson.getString("optResult"))) {
					System.out.println("登录验证成功，accessToken="+respJson.getString("accessToken"));
					aaa=respJson.getString("accessToken");
				}else{
					System.out.println("登录验证失败，返回码="+respJson.getString("optResult"));
				}
			}else{
				System.out.println("访问接口失败，状态码="+resp.getStatusLine().getStatusCode());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return aaa;
	}
	
	/**
	 * 查询IoT连接平台列表
	 */
	public static void getIOTServers(){
		String contentType="application/json";
		//请求地址
		String url="https://www.easy-iot.cn/idev/3rdcap/service-config/get-iotservers";
		//消息头参数serverID
		String serverID="gzxcdev01";
		//消息头参数accessToken
		String accessToken="4a2d1ccdbde30dd0446349492f1813ab";
		
		//使用HttpClient访问接口
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("Content-type", contentType);
		httpGet.setHeader("serverID", serverID);
		httpGet.setHeader("accessToken", accessToken);
		try {
			CloseableHttpResponse resp = httpClient.execute(httpGet);
			HttpEntity entity = resp.getEntity();
			if (resp.getStatusLine().getStatusCode()==200) {
				String responseData = EntityUtils.toString(entity);
				JSONObject respJson = JSONObject.parseObject(responseData);
				if ("0".equals(respJson.getString("optResult"))) {
					System.out.println("IOT连接平台列表如下：\n"+respJson.getJSONArray("iotserverList"));
				}else{
					System.out.println("获取IOT连接平台列表失败，返回码="+respJson.getString("optResult"));
				}
			}else{
				System.out.println("访问接口失败，状态码="+resp.getStatusLine().getStatusCode());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 根据IOT平台连接点查询业务模式
	 */
	public static void getServiceModes(){
		String contentType="application/json";
		//请求地址
		String url="https://www.easy-iot.cn/idev/3rdcap/service-config/get-iotservicemode";
		//消息头参数serverID
		String serverID="请填写用户名";
		//消息头参数accessToken
		String accessToken="请填写成功登陆后的accessToken";
		//消息头参数accessToken
		String iotserverId="请填写IOT平台连接点";
		
		//使用HttpClient访问接口
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		HttpGet httpGet = new HttpGet(url+"?iotserverId="+iotserverId);
		httpGet.setHeader("Content-type", contentType);
		httpGet.setHeader("serverID", serverID);
		httpGet.setHeader("accessToken", accessToken);
		try {
			CloseableHttpResponse resp = httpClient.execute(httpGet);
			HttpEntity entity = resp.getEntity();
			if (resp.getStatusLine().getStatusCode()==200) {
				String responseData = EntityUtils.toString(entity);
				JSONObject respJson = JSONObject.parseObject(responseData);
				if ("0".equals(respJson.getString("optResult"))) {
					System.out.println("查询业务模式列表如下：\n"+respJson.getJSONArray("serviceModeList"));
				}else{
					System.out.println("查询业务模式列表失败，返回码="+respJson.getString("optResult"));
				}
			}else{
				System.out.println("访问接口失败，状态码="+resp.getStatusLine().getStatusCode());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 单设备注册
	 */
	public static void registerDev(){
		
		String charset="utf-8";
		String contentType="application/json";
		
		//请求地址
		String url="https://www.easy-iot.cn/idev/3rdcap/devices/reg-device";
		//消息头参数serverID
		String serverID="gzxcdev01";
		//消息头参数accessToken
		String accessToken="4a2d1ccdbde30dd0446349492f1813ab";
		//设备序列号
		String devSerial="865233030112859";
		//设备名称
		String name="NB空调";
		//设备类型(产品型号)
		String deviceType="YHXC-KT";
		//设备接入点
		String connectPointId="ctc-nanjing-iot-137";
		
		//封装请求消息体
		JSONObject reqBody = new JSONObject();
		reqBody.put("devSerial", devSerial);
		reqBody.put("name", name);
		reqBody.put("deviceType", deviceType);
		reqBody.put("connectPointId", connectPointId);
		//忽略非必选的参数...
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		HttpPost httpPost = new HttpPost(url);
		//设置消息头
		httpPost.setHeader("Content-type", contentType);
		httpPost.setHeader("serverID", serverID);
		httpPost.setHeader("accessToken", accessToken);
		httpPost.setEntity(new StringEntity(reqBody.toJSONString(), charset));
		try {
			CloseableHttpResponse resp = httpClient.execute(httpPost);
			HttpEntity entity = resp.getEntity();
			if (resp.getStatusLine().getStatusCode()==200) {
				String responseData = EntityUtils.toString(entity);
				JSONObject respJson = JSONObject.parseObject(responseData);
				if ("0".equals(respJson.getString("optResult"))) {
					System.out.println("注册设备成功");
				}else{
					System.out.println("注册设备失败，返回码="+respJson.getString("optResult"));
				}
			}else{
				System.out.println("访问接口失败，状态码="+resp.getStatusLine().getStatusCode());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 批量设备注册
	 */
	public static void registerDevBatch(){
		
		String charset="utf-8";
		String contentType="application/json";
		
		//请求地址
		String url="https://www.easy-iot.cn/idev/3rdcap/devices/reg-device-batch";
		//消息头参数serverID
		String serverID="请填写用户名";
		//消息头参数accessToken
		String accessToken="请填写成功登陆后的accessToken";
		//请求体
		JSONObject reqBody = new JSONObject();
		//请求参数devices
		JSONArray devices=new JSONArray();
		
		//设备1
		JSONObject dev1 = new JSONObject();
		dev1.put("devSerial", "请填写设备1序列号");
		dev1.put("name", "请填写设备1名称");
		dev1.put("deviceType", "请填写设备1设备类型(产品型号)");
		dev1.put("connectPointId", "请填写设备1接入点");
		//设备2
		JSONObject dev2 = new JSONObject();
		dev2.put("devSerial", "请填写设备2序列号");     
		dev2.put("name", "请填写设备2名称");           
		dev2.put("deviceType", "请填写设备2设备类型(产品型号)");   
		dev2.put("connectPointId", "请填写设备2接入点");
		
		devices.add(dev1);
		devices.add(dev2);
		reqBody.put("devices", devices);
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		HttpPost httpPost = new HttpPost(url);
		//设置消息头
		httpPost.setHeader("Content-type", contentType);
		httpPost.setHeader("serverID", serverID);
		httpPost.setHeader("accessToken", accessToken);
		httpPost.setEntity(new StringEntity(reqBody.toJSONString(), charset));
		try {
			CloseableHttpResponse resp = httpClient.execute(httpPost);
			HttpEntity entity = resp.getEntity();
			if (resp.getStatusLine().getStatusCode()==200) {
				String responseData = EntityUtils.toString(entity);
				JSONObject respJson = JSONObject.parseObject(responseData);
				if ("0".equals(respJson.getString("optResult"))) {
					System.out.println("批量注册设备成功");
				}else{
					System.out.println("批量注册设备失败，返回码="+respJson.getString("optResult"));
				}
			}else{
				System.out.println("访问接口失败，状态码="+resp.getStatusLine().getStatusCode());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 更新设备
	 */
	public static void updateDev(){
		
		String charset="utf-8";
		String contentType="application/json";
		
		//请求地址
		String url="https://www.easy-iot.cn/idev/3rdcap/devices/update-device";
		//消息头参数serverID
		String serverID="请填写用户名";
		//消息头参数accessToken
		String accessToken="请填写成功登陆后的accessToken";
		//设备序列号
		String devSerial="请填写要更新的设备序列号";
		//设备名称，可选
		String name="请填写新的设备名称";
		//经度，可选
		String longitude="请填写新的经度";
		//纬度，可选
		String latitude="请填写新的纬度";
		
		
		//封装请求消息体
		JSONObject reqBody = new JSONObject();
		reqBody.put("devSerial", devSerial);
		reqBody.put("name", name);
		reqBody.put("longitude", longitude);
		reqBody.put("latitude", latitude);
		//忽略非必选的参数...
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		HttpPut httpPut = new HttpPut(url);
		//设置消息头
		httpPut.setHeader("Content-type", contentType);
		httpPut.setHeader("serverID", serverID);
		httpPut.setHeader("accessToken", accessToken);
		httpPut.setEntity(new StringEntity(reqBody.toJSONString(), charset));
		try {
			CloseableHttpResponse resp = httpClient.execute(httpPut);
			HttpEntity entity = resp.getEntity();
			if (resp.getStatusLine().getStatusCode()==200) {
				String responseData = EntityUtils.toString(entity);
				JSONObject respJson = JSONObject.parseObject(responseData);
				if ("0".equals(respJson.getString("optResult"))) {
					System.out.println("更新设备成功");
				}else{
					System.out.println("更新设备失败，返回码="+respJson.getString("optResult"));
				}
			}else{
				System.out.println("访问接口失败，状态码="+resp.getStatusLine().getStatusCode());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 删除设备
	 */
	public static void deleteDev(){
		String contentType="application/json";
		//请求地址
		String url="https://www.easy-iot.cn/idev/3rdcap/devices/del-device";
		//消息头参数serverID
		String serverID="请填写用户名";
		//消息头参数accessToken
		String accessToken="请填写成功登陆后的accessToken";
		//设备序列号
		String devSerial="TEST$_1130_1";
		
		//使用HttpClient访问接口
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		HttpDelete httpDelete = new HttpDelete(url+"?devSerial="+devSerial);
		httpDelete.setHeader("Content-type", contentType);
		httpDelete.setHeader("serverID", serverID);
		httpDelete.setHeader("accessToken", accessToken);
		try {
			CloseableHttpResponse resp = httpClient.execute(httpDelete);
			HttpEntity entity = resp.getEntity();
			if (resp.getStatusLine().getStatusCode()==200) {
				String responseData = EntityUtils.toString(entity);
				JSONObject respJson = JSONObject.parseObject(responseData);
				if ("0".equals(respJson.getString("optResult"))) {
					System.out.println("删除设备成功");
				}else{
					System.out.println("删除设备失败，返回码="+respJson.getString("optResult"));
				}
			}else{
				System.out.println("访问接口失败，状态码="+resp.getStatusLine().getStatusCode());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 查询设备信息
	 */
	public static void queryDev(){
		String contentType="application/json";
		//请求地址
		String url="https://www.easy-iot.cn/idev/3rdcap/devices/query-device-allinfo";
		//消息头参数serverID
		String serverID="gzxcdev01";
		//消息头参数accessToken
		String accessToken="4a2d1ccdbde30dd0446349492f1813ab";
		//设备序列号
		String devSerial="863703037907719";
		
		//使用HttpClient访问接口
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		HttpGet httpGet = new HttpGet(url+"?devSerial="+devSerial);
		httpGet.setHeader("Content-type", contentType);
		httpGet.setHeader("serverID", serverID);
		httpGet.setHeader("accessToken", accessToken);
		try {
			CloseableHttpResponse resp = httpClient.execute(httpGet);
			HttpEntity entity = resp.getEntity();
			if (resp.getStatusLine().getStatusCode()==200) {
				String responseData = EntityUtils.toString(entity);
				JSONObject respJson = JSONObject.parseObject(responseData);
				if ("0".equals(respJson.getString("optResult"))) {
					System.out.println("查询设备信息如下：\n"+respJson.toJSONString());
				}else{
					System.out.println("查询设备信息失败，返回码="+respJson.getString("optResult"));
				}
			}else{
				System.out.println("访问接口失败，状态码="+resp.getStatusLine().getStatusCode());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 查询一个平台帐号下所有可用于设备注册的设备类型(产品型号)
	 */
	public static void getDeviceTypes(){
		String contentType="application/json";
		//请求地址
		String url="https://www.easy-iot.cn/idev/3rdcap/devices/list-devtypes";
		//消息头与消息体参数serverID
		String serverID="gzxcdev01";
		//消息头参数accessToken
		String accessToken="617de6d4e47323177d8dc9ff09e2e094";
		
		//使用HttpClient访问接口
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		HttpGet httpGet = new HttpGet(url+"?serverID="+serverID);
		httpGet.setHeader("Content-type", contentType);
		httpGet.setHeader("serverID", serverID);
		httpGet.setHeader("accessToken", accessToken);
		try {
			CloseableHttpResponse resp = httpClient.execute(httpGet);
			HttpEntity entity = resp.getEntity();
			if (resp.getStatusLine().getStatusCode()==200) {
				String responseData = EntityUtils.toString(entity);
				JSONObject respJson = JSONObject.parseObject(responseData);
				if ("0".equals(respJson.getString("optResult"))) {
					System.out.println("设备类型(产品型号)列表如下：\n"+respJson.getJSONArray("devTypes").toJSONString());
				}else{
					System.out.println("查询设备类型(产品型号)列表失败，返回码="+respJson.getString("optResult"));
				}
			}else{
				System.out.println("访问接口失败，状态码="+resp.getStatusLine().getStatusCode());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 查询设备类型(产品型号)信息接口
	 */
	public static void getDeviceType(){
		String contentType="application/json";
		//请求地址
		String url="https://www.easy-iot.cn/idev/3rdcap/dev-manage/query-devType";
		//消息头与消息体参数serverID
		String serverID="gzxcdev01";
		//消息头参数accessToken
		String accessToken="4a2d1ccdbde30dd0446349492f1813ab";
		//指定设备类型(产品型号)
		String devType="";
		
		//使用HttpClient访问接口
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		HttpGet httpGet = new HttpGet(url+"?devType="+devType);
		httpGet.setHeader("Content-type", contentType);
		httpGet.setHeader("serverID", serverID);
		httpGet.setHeader("accessToken", accessToken);
		try {
			CloseableHttpResponse resp = httpClient.execute(httpGet);
			HttpEntity entity = resp.getEntity();
			if (resp.getStatusLine().getStatusCode()==200) {
				String responseData = EntityUtils.toString(entity);
				JSONObject respJson = JSONObject.parseObject(responseData);
				if ("0".equals(respJson.getString("optResult"))) {
					System.out.println("设备类型(产品型号)信息如下：\n"+respJson.toJSONString());
				}else{
					System.out.println("查询设备类型(产品型号)信息失败，返回码="+respJson.getString("optResult"));
				}
			}else{
				System.out.println("查询设备类型(产品型号)信息失败，状态码="+resp.getStatusLine().getStatusCode());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 查询一个平台帐号下注册的所有设备
	 */
	public static void getDevices(){
		String contentType="application/json";
		//请求地址
		String url="https://www.easy-iot.cn/idev/3rdcap/devices/list-devices";
		//消息头与消息体参数serverID
		String serverID="请填写用户名";
		//消息头参数accessToken
		String accessToken="请填写成功登陆后的accessToken";
		
		//使用HttpClient访问接口
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		HttpGet httpGet = new HttpGet(url+"?serverID="+serverID);
		httpGet.setHeader("Content-type", contentType);
		httpGet.setHeader("serverID", serverID);
		httpGet.setHeader("accessToken", accessToken);
		try {
			CloseableHttpResponse resp = httpClient.execute(httpGet);
			HttpEntity entity = resp.getEntity();
			if (resp.getStatusLine().getStatusCode()==200) {
				String responseData = EntityUtils.toString(entity);
				JSONObject respJson = JSONObject.parseObject(responseData);
				if ("0".equals(respJson.getString("optResult"))) {
					System.out.println("设备列表如下：\n"+respJson.getJSONArray("devices").toJSONString());
				}else{
					System.out.println("查询设备列表失败，返回码="+respJson.getString("optResult"));
				}
			}else{
				System.out.println("访问接口失败，状态码="+resp.getStatusLine().getStatusCode());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 设备控制
	 */
	public static String commandControl(String command,String uuid,String tocken){
		
		String charset="utf-8";
		String contentType="application/json";

		//请求返回的参数
		String message="";
		//请求地址
		String url="https://www.easy-iot.cn/idev/3rdcap/dev-control/urt-command";
		//消息头参数serverID
		String serverID="gzxcdev01";
		//消息头参数accessToken
		String accessToken=tocken;
		//设备序列号
		String devSerial=uuid;
		//控制命令id
		String method="command3";
		//控制命令参数
		JSONObject params=new JSONObject();
		//在此添加设备控制命令，params.put();
		System.out.println(command);
		params.put("zqw",command);
		System.out.println(command);
		//封装请求消息体
		JSONObject reqBody = new JSONObject();
		reqBody.put("devSerial", devSerial);
		reqBody.put("method", method);
		reqBody.put("params", params);
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		HttpPost httpPost = new HttpPost(url);
		//设置消息头
		httpPost.setHeader("Content-type", contentType);
		httpPost.setHeader("serverID", serverID);
		httpPost.setHeader("accessToken", accessToken);
		httpPost.setEntity(new StringEntity(reqBody.toJSONString(), charset));
		System.out.println(reqBody.toString());
		try {
			CloseableHttpResponse resp = httpClient.execute(httpPost);
			HttpEntity entity = resp.getEntity();
			if (resp.getStatusLine().getStatusCode()==200) {
				String responseData = EntityUtils.toString(entity);
				JSONObject respJson = JSONObject.parseObject(responseData);
				if ("0".equals(respJson.getString("optResult"))) {
					message= ("设备控制指令已下发，commandId="+respJson.getString("commandId"));
				}else{
					message=("设备控制指令下发失败，返回码="+respJson.getString("optResult"));
				}
			}else{
				message=("访问接口失败，状态码="+resp.getStatusLine().getStatusCode());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return message;
	}
	
	/**
	 * 注册订阅地址
	 */
	public static void subscribe(){
		
		String charset="utf-8";
		String contentType="application/json";
		
		//请求地址
		String url="https://www.easy-iot.cn/idev/3rdcap/subscribe-service-address";
		//消息头参数serverID
		String serverID="gzxcdev01";
		//消息头参数accessToken
		String accessToken="548f3c659365f401268988cd9fc2ea12";
		//用于接收后向订阅消息的地址
		String callbackUrl="http://120.78.93.40:80/reception/";
		
		//封装请求消息体
		JSONObject reqBody = new JSONObject();
		reqBody.put("callbackUrl", callbackUrl);
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		HttpPost httpPost = new HttpPost(url);
		//设置消息头
		httpPost.setHeader("Content-type", contentType);
		httpPost.setHeader("serverID", serverID);
		httpPost.setHeader("accessToken", accessToken);
		httpPost.setEntity(new StringEntity(reqBody.toJSONString(), charset));
		try {
			CloseableHttpResponse resp = httpClient.execute(httpPost);
			HttpEntity entity = resp.getEntity();
			if (resp.getStatusLine().getStatusCode()==200) {
				String responseData = EntityUtils.toString(entity);
				JSONObject respJson = JSONObject.parseObject(responseData);
				if ("0".equals(respJson.getString("optResult"))) {
					System.out.println("注册订阅成功");
				}else{
					System.out.println("注册订阅失败，返回码="+respJson.getString("optResult"));
				}
			}else{
				System.out.println("访问接口失败，状态码="+resp.getStatusLine().getStatusCode());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 取消订阅地址
	 */
	public static void unsubscribe(){
		
		String contentType="application/json";
		
		//请求地址
		String url="https://www.easy-iot.cn/idev/3rdcap/unsubscribe-service-address";
		//消息头参数serverID
		String serverID="gzxcdev01";
		//消息头参数accessToken
		String accessToken="9d534cd58e375b7859ea18e831c4dc29";
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		HttpDelete httpDelete = new HttpDelete(url);
		//设置消息头
		httpDelete.setHeader("Content-type", contentType);
		httpDelete.setHeader("serverID", serverID);
		httpDelete.setHeader("accessToken", accessToken);
		try {
			CloseableHttpResponse resp = httpClient.execute(httpDelete);
			HttpEntity entity = resp.getEntity();
			if (resp.getStatusLine().getStatusCode()==200) {
				String responseData = EntityUtils.toString(entity);
				JSONObject respJson = JSONObject.parseObject(responseData);
				if ("0".equals(respJson.getString("optResult"))) {
					System.out.println("取消订阅成功");
				}else{
					System.out.println("取消订阅失败，返回码="+respJson.getString("optResult"));
				}
			}else{
				System.out.println("访问接口失败，状态码="+resp.getStatusLine().getStatusCode());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 查询订阅地址
	 */
	public static void querySubscribe(){
		
		String contentType="application/json";
		
		//请求地址
		String url="https://www.easy-iot.cn/idev/3rdcap/query-subscribe-service-address";
		//消息头参数serverID
		String serverID="gzxcdev01";
		//消息头参数accessToken
		String accessToken="548f3c659365f401268988cd9fc2ea12";
		//用于接收后向订阅消息的地址
		String callbackUrl="https://120.78.93.40:8083/type/iot";
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		HttpGet httpGet = new HttpGet(url+"?callbackUrl="+callbackUrl);
		//设置消息头
		httpGet.setHeader("Content-type", contentType);
		httpGet.setHeader("serverID", serverID);
		httpGet.setHeader("accessToken", accessToken);
		try {
			CloseableHttpResponse resp = httpClient.execute(httpGet);
			HttpEntity entity = resp.getEntity();
			if (resp.getStatusLine().getStatusCode()==200) {
				String responseData = EntityUtils.toString(entity);
				JSONObject respJson = JSONObject.parseObject(responseData);
				if ("0".equals(respJson.getString("optResult"))) {
					System.out.println("订阅地址如下：\n"+respJson.getString("callbackUrl"));
				}else{
					System.out.println("获取订阅地址失败，返回码="+respJson.getString("optResult"));
				}
			}else{
				System.out.println("访问接口失败，状态码="+resp.getStatusLine().getStatusCode());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 查询设备业务数据历史记录
	 */
	public static void deviceHistoryData(){
		
		String contentType="application/json";
		
		//请求地址
		String url="https://www.easy-iot.cn/idev/3rdcap/device-history-data";
		//消息头参数serverID
		String serverID="gzxcdev01";
		//消息头参数accessToken
		String accessToken="9d534cd58e375b7859ea18e831c4dc29";
		//参数：设备序列号，其他可选参数忽略不演示
		String devSerial = "863703037907719";
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		HttpGet httpGet = new HttpGet(url+"?devSerial="+devSerial);
		//设置消息头
		httpGet.setHeader("Content-type", contentType);
		httpGet.setHeader("serverID", serverID);
		httpGet.setHeader("accessToken", accessToken);
		try {
			CloseableHttpResponse resp = httpClient.execute(httpGet);
			HttpEntity entity = resp.getEntity();
			if (resp.getStatusLine().getStatusCode()==200) {
				String responseData = EntityUtils.toString(entity);
				JSONObject respJson = JSONObject.parseObject(responseData);
				if ("0".equals(respJson.getString("optResult"))) {
					System.out.println("查询设备业务数据历史记录结果如下：\n"+respJson.toJSONString());
				}else{
					System.out.println("查询设备业务数据历史记录失败，返回码="+respJson.getString("optResult"));
				}
			}else{
				System.out.println("访问接口失败，状态码="+resp.getStatusLine().getStatusCode());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
