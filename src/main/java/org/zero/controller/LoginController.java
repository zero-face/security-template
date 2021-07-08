package org.zero.controller;

import cn.hutool.http.HttpUtil;
import cn.hutool.jwt.JWTUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zero.entity.User;
import org.zero.service.UserService;
import org.zero.utils.JWTUtils;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Zero
 * @Description
 * @Date 2021/6/27 0:09
 * @Since 1.8
 **/
@RestController
@RequestMapping("api/v1/user")
public class LoginController extends BaseController{

}
