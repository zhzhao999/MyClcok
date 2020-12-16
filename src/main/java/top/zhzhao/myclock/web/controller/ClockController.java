/*
 * https://www.zhzhao.top
 */
package top.zhzhao.myclock.web.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.zhzhao.myclock.exception.CustomException;
import top.zhzhao.myclock.service.ClockService;
import top.zhzhao.myclock.util.response.ResponseVO;
import top.zhzhao.myclock.util.response.ResponseVOUtils;
import top.zhzhao.myclock.web.vo.HxUser;
import top.zhzhao.myclock.web.vo.User;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *@author zhzhao
 *@version $ Id: ClockController.java,V 0.1 2020/1/9 10:55 zhzhao Exp $
 */
@RestController
@RequestMapping("/clock")
public class ClockController {

    private final ClockService clockService;
    @Autowired
    public ClockController(ClockService clockService) {
        this.clockService = clockService;
    }


    @PostMapping(path = "/auth")
    @ResponseBody
    public ResponseVO auth(@RequestParam String code){
        if (StringUtils.isBlank(code)){
            throw new CustomException("授权码不能为空!");
        }
        //获取用户详情
        clockService.auth(code);
        //返回
        return ResponseVOUtils.success();
    }

    @GetMapping(path = "/jbf/user")
    @ResponseBody
    public ResponseVO getClockUser(){
        //获取用户详情
        List<User> users = clockService.getUser();
        //筛选用户名
        ArrayList<String> result = new ArrayList<>();
        for (User user : users) {
            result.add(user.getName());
        }
        //返回
        return ResponseVOUtils.success(result);
    }

    @PostMapping(path = "/jbf/do")
    @ResponseBody
    public ResponseVO doJBFClock(@RequestParam String name){
        //获取用户详情
        String s = clockService.doJBFClock(name);
        //返回
        return ResponseVOUtils.success(s);
    }


    @GetMapping(path = "/hx/user")
    @ResponseBody
    public ResponseVO getHxUser(){
        //获取用户详情
        List<HxUser> users = clockService.getHxUser();
        //筛选用户名
        ArrayList<String> result = new ArrayList<>();
        for (HxUser user : users) {
            result.add(user.getName());
        }
        //返回
        return ResponseVOUtils.success(result);
    }

    @PostMapping(path = "/hx/do")
    @ResponseBody
    public ResponseVO doHxClock(@RequestParam String name){
        //获取用户详情
        String result = clockService.doHxClock(name);
        //返回
        return ResponseVOUtils.success(result);
    }
}
