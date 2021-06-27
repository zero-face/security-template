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
@Controller
public class LoginController extends BaseController{

    /**
     * @Author panghl
     * @Date 2021/2/6 21:27
     * @Description TODO
     **/
    @CrossOrigin
//注意这里没有配置 @RestController
    @Controller
    @RequestMapping("/api/ucenter/wx/")
    @Api(tags = {"微信接口"})
    public class WxApiController {

        @Autowired
        private UserService memberService;

        @ApiOperation(value = "生成微信二维码")
        @GetMapping("login")
        public String getWxCode() {
//        String url= "https://open.weixin.qq.com/connect/qrconnect" +
//                "?appid="+ ConstantWxUtils.WX_OPEN_APP_ID+"&response_type=code";


            //微信开放平台授权baseUrl   %s相当于?代表占位符
            String baseUrl = "https://open.weixin.qq.com/connect/qrconnect";
            //对redirect_url 进行URLEncoder编码
            String redirect_url = "http://zero.free.idcfengye.com";
            try {
                redirect_url = URLEncoder.encode(redirect_url, "UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
            //给baseUrl %s 里面 设置值
            //重定向到请求微信地址里面请求微信地址
            return "redirect:" + baseUrl;
        }


        @ApiOperation(value = "获取扫描人信息，添加数据")
        @GetMapping("callback")
        public String callback(String code, String state) {
            //从redis中将state获取出来，和当前传入的state作比较
            //如果一致则放行，如果不一致则抛出异常：非法访问
            //向认证服务器发送请求换取access_token
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token";

            //请求这个拼接好的地址，得到返回两个值， access_token 和openid
            //使用httpclient发送请求，得到返回结果
            Map<String, Object> params = new HashMap<>();
            params.put("appid", "wxa8a11612e5d75210");
            params.put("secret", "42f540cf6550deb400e2cef767f7f1dd");
            params.put("code", code);
            params.put("grant_type", "authorization_code");

            String accessTokenInfo = HttpUtil.get(baseAccessTokenUrl, params);
            System.out.println(accessTokenInfo);
            //gson转换工具

            JSONObject jsonObject = JSON.parseObject(accessTokenInfo);
            HashMap<String, String> mapAccessToken = jsonObject.toJavaObject(HashMap.class);
            String accessToken = mapAccessToken.get("access_token");
            String openid = mapAccessToken.get("openid");
            //判断数据表里面是否有相应对的数据，根据openid判断
            User member = memberService.getUserByOpenid(openid);
            if (null != member) {
                return "redirect:http://localhost:3000?code=" + code + "&state=" + state;
            }
            //3 拿着得到access_token值 获取登录用户信息
            //访问微信的资源服务器，获取用户信息
            String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo";
            //拼接两个参数
            Map<String, Object> params1 = new HashMap<>();
            params1.put("accessToken", accessToken);
            params1.put("openid", openid);
            //发送请求
            String userInfo = HttpUtil.get(baseUserInfoUrl, params1);
            //获取返回userInfo字符串扫描人信息
            HashMap<String, String> userInfoMap = JSON.parseObject(userInfo).toJavaObject(HashMap.class);
            String nickname = userInfoMap.get("nickname");
            String headimgurl = userInfoMap.get("headimgurl");
            //把扫描人信息添加数据库里面
            User user = new User();
            user.setOpenId(openid);
            user.setUsername(nickname);
            memberService.save(user);
             HashMap<String, String> stringStringHashMap = new HashMap<>();
             stringStringHashMap.put("id", String.valueOf(user.getId()));
            //使用jwt根据member对象生成token字符串
            String jwtToken = JWTUtils.createToken(stringStringHashMap, "GHIODJGDJIGOERJGIERGMJ");
            //最后:返回首页面，通过路径传递token字符串
            return "redirect:http://localhost:3000?token=" + jwtToken;
        }

    }
}
