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
import top.zhzhao.myclock.util.UnicodeUtils;
import top.zhzhao.myclock.web.vo.Address;
import top.zhzhao.myclock.web.vo.HxUser;
import top.zhzhao.myclock.web.vo.User;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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

        return "JBF打卡成功！";

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
        log.info(user.getName() + " JBF接口返回--"+body);
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



    public List<HxUser> getHxUser(){
        SysParamDO paramDO = sysParamDAO.findOneByCode("clock_hx_users");
        String value = paramDO.getValue();
        Gson gson = new Gson();
        return gson.fromJson(value, new TypeToken<List<HxUser>>(){}.getType());
    }

    public String doHxClock(String name){
        //准备参数
        List<HxUser> userList = getHxUser();
        HxUser doUser = null;
        for (HxUser user : userList) {
            if (name.equals(user.getName())){
                doUser = user;
            }
        }
        if (doUser == null){
            throw new CustomException("未查询到该用户!");
        }
        Address address = getOneAddress();
        //调用接口
        clockHx(doUser,address);

        return "HX打卡成功！";

    }

    private void clockHx(HxUser user,Address address){

        long timeMillis = System.currentTimeMillis();
        String encodeAddr;
        try {
            encodeAddr = URLEncoder.encode(address.getPlace(),"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new CustomException("地址URL编码失败:"+e.getMessage());
        }

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
                "?account=" + user.getAccount() +
                "&longitude=" + address.getLongitude() +
                "&fdimension=" + address.getLatitude() +
                "&altitude=0.0" +
                "&addrname="+ encodeAddr +
                "&device_type=1&deviceuuid=&orgId=1&wifimac=02:00:00:00:00:00&wifissid=WIFI" +
                "&time=" + timeMillis +
                "&rxsig=" + user.getRxSig();

        headers.put("Referer",refStr);
        headers.put("Accept-Encoding","gzip, deflate");
        headers.put("Accept-Language","zh-CN,en-US;q=0.9");
        headers.put("Cookie","RX-UID=" + user.getRxUid() + "; PHPSESSID=" + user.getPhpSessId() + "; RX-ASAPPID=kaoqing");

        HashMap<String, String> param= new HashMap<>();
        param.put("action", "asi/kqrecordadd");
        param.put("account", user.getAccount());
        param.put("userid", user.getUserId());
        param.put("addrname", address.getPlace());
        param.put("longitude", address.getLongitude());
        param.put("fdimension", address.getLatitude());
        param.put("device_type", "1");
        param.put("deviceuuid", "");
        param.put("orgId", "1");
        param.put("wifimac", "02:00:00:00:00:00");
        param.put("wifissid", "WIFI");
        param.put("is_field", "0");

        try {
            Connection.Response post = HttpUtils.post(url,headers, JSON.toJSONString(param));
            String body = post.body();
            log.info(user.getName() + " HX接口返回--"+body);
            JSONObject jsonObject = JSON.parseObject(body);
            String descStr = jsonObject.get("desc").toString();
            if (!"成功".equals(descStr)){
                throw new CustomException("打卡失败:" + body);
            }

        } catch (IOException e) {
            throw new CustomException("接口请求失败:" + e.getMessage());
        }
    }
}