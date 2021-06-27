package org.zero.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.mysql.cj.log.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.zero.core.response.CommonReturnType;
import org.zero.entity.User;
import org.zero.service.UserService;
import org.zero.utils.CheckUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author Zero
 * @Date 2021/6/4 21:08
 * @Since 1.8
 **/
@Slf4j
@RestController
@RequestMapping("/api/v1/user")
@Api(tags = "用户获取模块")
@ApiSupport(author = "zerocl")
@Validated
public class UserController extends BaseController{
    @Autowired
    private UserService userService;

    @GetMapping("/getUser")
    @ApiOperation("获取用户")
    @ApiOperationSupport(author = "zero")
    @ApiImplicitParam(name = "username",value = "用户名",required = true,dataType = "String",paramType = "query")
    public CommonReturnType getUser(String username, HttpServletRequest request) {
        log.info("{}", username);
        final User one = userService.getOne(new QueryWrapper<User>().eq("username", username));
        log.info("{}",one);
        return CommonReturnType.success(one);
    }
    @PostMapping("/login")
    @ApiOperation("用户登录请求")
    @ApiImplicitParams({@ApiImplicitParam(name = "username",value = "用户名",required = true),@ApiImplicitParam(name = "password",value = "密码",required = true)})
    public CommonReturnType login(@RequestParam("username")@NotBlank String username,
                                  @RequestParam("password")@NotBlank String password) {
        return CommonReturnType.success("登录");
    }


    @RequestMapping("/wechat")
    public void ownerCheck(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println(111);
        boolean isGet = request.getMethod().toLowerCase().equals("get");
        PrintWriter print;
        if (isGet) {
            // 微信加密签名
            String signature = request.getParameter("signature");
            // 时间戳
            String timestamp = request.getParameter("timestamp");
            // 随机数
            String nonce = request.getParameter("nonce");
            // 随机字符串
            String echostr = request.getParameter("echostr");
            System.out.println(echostr);
            // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
            if (signature != null && CheckUtil.checkSignature(signature, timestamp, nonce)) {
                try {
                    print = response.getWriter();
                    print.write(echostr);
                    print.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
