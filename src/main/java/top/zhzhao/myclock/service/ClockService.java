/*
 * https://www.zhzhao.top
 */
package top.zhzhao.myclock.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import top.zhzhao.myclock.dao.SysParamDAO;
import top.zhzhao.myclock.dao.dataobj.SysParamDO;
import top.zhzhao.myclock.exception.CustomException;
import top.zhzhao.myclock.util.HttpUtils;
import top.zhzhao.myclock.web.vo.Address;
import top.zhzhao.myclock.web.vo.User;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 *
 *@author zhzhao
 *@version $ Id: ClockService.java,V 0.1 2020/1/9 10:57 zhzhao Exp $
 */
@Service
@Slf4j
public class ClockService {

    private final SysParamDAO sysParamDAO;
    @Autowired
    public ClockService(SysParamDAO sysParamDAO){
        this.sysParamDAO = sysParamDAO;
    }


    public void auth(String code) {
        SysParamDO paramDO = sysParamDAO.findOneByCode("clock_auth");
        String value = paramDO.getValue();
        if (!code.equals(value)){
            throw new CustomException("授权码校验失败！");
        }
    }


    public List<User> getUser(){
        SysParamDO paramDO = sysParamDAO.findOneByCode("clock_users");
        String value = paramDO.getValue();
        Gson gson = new Gson();
        return gson.fromJson(value, new TypeToken<List<User>>(){}.getType());
    }

    public String doJBFClock(String name){
        //准备参数
        List<User> userList = getUser();
        User doUser = null;
        for (User user : userList) {
            if (name.equals(user.getName())){
                doUser = user;
            }
        }
        if (doUser == null){
            throw new CustomException("未查询到该用户");
        }
        Address address = getOneAddress();
        //调用接口
        clockJbf(doUser,address);

        return "打卡成功！";

    }

    private Address getOneAddress(){
        //查询全部
        SysParamDO paramDO = sysParamDAO.findOneByCode("clock_address");
        String value = paramDO.getValue();
        Gson gson = new Gson();
        List<Address> addressList = gson.fromJson(value, new TypeToken<List<Address>>(){}.getType());
        //随机获取一个
        Random random = new Random();
        int i = random.nextInt(addressList.size());
        //返回
        return addressList.get(i);
    }

    /**
     * 调用京北方接口打卡
     */
    private void clockJbf(User user,Address address){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://111.203.253.37:8201/nkcisp/mobile-base.action?to=cispInsertPunchCardInfoAction";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("name", user.getName());
        map.add("loginName", user.getLoginName());
        map.add("latitude", address.getLatitude());
        map.add("longitude", address.getLongitude());
        map.add("place", address.getPlace());
        map.add("flag", "1");
        map.add("groupId", "1");
        map.add("projectNo", "RD-19-0973-01");
        map.add("startTime", "09:00");
        map.add("endTime", "17:30");
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity( url, request , String.class );
        String body = response.getBody();
        log.info("接口返回--"+body);
        try {
            JSONObject jsonObject = JSON.parseObject(body);
            Object rsp_body = jsonObject.get("RSP_BODY");
            JSONObject jsonObject1 = JSON.parseObject(rsp_body.toString());
            Object replyInformation = jsonObject1.get("replyInformation");
            JSONObject object = JSON.parseObject(replyInformation.toString());
            Object responseMessage = object.get("responseMessage");
            if (!"操作成功！".equals(responseMessage.toString())){
                throw new CustomException(responseMessage.toString());
            }
        }catch (Exception e){
            throw new CustomException(e.getMessage());
        }

    }

    /**
     * 调用恒信接口打卡
     */
    public String hxDaka(){

        long timeMillis = System.currentTimeMillis();

        String url = "https://im.hfbank.com.cn:7778/webhtml/asi/kqrecordadd";
        HashMap<String,String> headers = new HashMap<>();
        headers.put("Host","im.hfbank.com.cn:7778");
        headers.put("Connection","keep-alive");
        headers.put("Pragma","no-cache");
        headers.put("Cache-Control","no-cache");
        headers.put("Origin","https://im.hfbank.com.cn:7778");
        headers.put("X-Requested-With","XMLHttpRequest");
        headers.put("Content-Type","application/json");
        String refStr = "https://im.hfbank.com.cn:7778/webhtml/kaoqing/attendance" +
                "?account=rxhf15223" +
                "&longitude=116.351957" +
                "&fdimension=39.926758" +
                "&altitude=0.0" +
                "&addrname=%E5%8C%97%E4%BA%AC%E5%B8%82%E8%A5%BF%E5%9F%8E%E5%8C%BA%E9%98%9C%E6%88%90%E9%97%A8%E5%8C%97%E8%90%A5%E9%97%A8%E4%B8%9C%E9%87%8C" +
                "&device_type=1&deviceuuid=&orgId=1&wifimac=02:00:00:00:00:00&wifissid=WIFI" +
                "&time=" + timeMillis +
                "&rxsig=86D6874763693B5A0BCAAF13DE7E55D8";

        headers.put("Referer",refStr);

        headers.put("Accept-Encoding","gzip, deflate");
        headers.put("Accept-Language","zh-CN,en-US;q=0.9");
        headers.put("Cookie","RX-UID=w_zhangzhao; PHPSESSID=eghajpecpr8u305nlf4lanmal4; RX-ASAPPID=kaoqing");



        HashMap<String, String> map= new HashMap<>();
        map.put("action", "asi/kqrecordadd");
        map.put("account", "rxhf15223");
        map.put("userid", "16203");
        map.put("addrname", "百万庄大街8号");
        map.put("longitude", "116.3565140");
        map.put("fdimension", "39.9334068");
        map.put("device_type", "1");
        map.put("deviceuuid", "");
        map.put("orgId", "1");
        map.put("wifimac", "02:00:00:00:00:00");
        map.put("wifissid", "WIFI");
        map.put("is_field", "0");

        try {
            Connection.Response post = HttpUtils.post(url,headers, JSON.toJSONString(map));
            return "恒信打卡成功!";
        } catch (IOException e) {
            throw new CustomException(e.getMessage());
        }
    }
}