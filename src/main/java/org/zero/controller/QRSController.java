package org.zero.controller;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.zxing.NotFoundException;
import com.sun.xml.internal.ws.api.pipe.ContentType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.zero.core.error.BusinessException;
import org.zero.core.error.EmBusinessError;
import org.zero.utils.QRCodeGeneratorUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author Zero
 * @Description
 * @Date 2021/6/25 0:38
 * @Since 1.8
 **/
@Validated
@RestController
@RequestMapping("/api/v1/qrs")
public class QRSController extends BaseController{

    @RequestMapping(value = "/aa")
    public void createQrCodeFile(@NotBlank(message = "二维码中信息不能为空") String content,HttpServletResponse response,
                             @RequestParam String filePath) throws Exception {
        String qrcode = null;
        qrcode = QRCodeGeneratorUtil.createORCode(content,filePath);
        response.setContentType("text/plain;charset=utf-8");
        response.setStatus(HttpStatus.CREATED.value());
        response.getWriter().write(qrcode);
    }
    @RequestMapping(value = "/bb")
    public void decodeQrCode(String url,HttpServletResponse response) throws Exception {
        String s = QRCodeGeneratorUtil.decoderQRCode(url);
        response.setContentType("text/plain;charset=utf-8");
        response.setStatus(HttpStatus.OK.value());
        response.getWriter().write(s);
    }
    @RequestMapping(value = "/cc")
    public void createQrCode(@NotBlank(message = "二维码中信息不能为空") @RequestParam(defaultValue = "https://open.weixin.qq.com/connect/qrconnect?appid=wxa8a11612e5d75210&redirect_uri=http://www.baidu.com&response_type=code&scope=snsapi_login&state=2014#wechat_redirect") String content,HttpServletResponse response,
                             @RequestParam String logPath) throws Exception {
        byte[] qrcode = null;
//        response_type=code&scope=snsapi_login&state=2014#wechat_redirect
        String url = "https://open.weixin.qq.com/connect/qrconnect";
        String redirect = URLEncoder.encode("http://zero.free.idcfengye.com/api/v1/qrs/callback", "UTF-8");
        String response_type = "code";
        String scope = "snsapi_login";
        String state = UUID.randomUUID().toString();
        String appid = "wxa8a11612e5d75210";
        String codeUrl = url + "?appid=" +appid  + "&response_type="
                + response_type + "&redirect_uri=" + redirect + "&scope=" + scope + "&state=" + state +"#wechat_redirect";
        System.out.println(codeUrl);

        qrcode = QRCodeGeneratorUtil.createORCodeLogo(codeUrl,logPath,true);
        System.out.println(qrcode.toString());
        response.setContentType("image/png;charset=utf-8");
        response.setStatus(HttpStatus.CREATED.value());
        response.getOutputStream().write(qrcode);
    }

    /*@RequestMapping("/ ")
    public void callback(HttpServletRequest request) throws BusinessException {
        String code = request.getParameter("code");
        //获取access_token
        String accessUrl = "https://api.weixin.qq.com/sns/oauth2/access_token";
        String appid = "";
        String secret = "";
        String getUrl = accessUrl +  "?appid=" + appid + "&secret=" + secret + "&code=" + code + "&grant_type=authorization_code";
        String s1 = HttpUtil.get(getUrl);
        JSONObject jsonObject = JSON.parseObject(s1);
        HashMap<String,String> hashMap = jsonObject.toJavaObject(HashMap.class);
        String accesstoken = hashMap.get("access_token");
        String openid = hashMap.get("openid");
        if( null == accesstoken || null == openid) {
            throw new BusinessException(EmBusinessError.PRIMARY_ERROR,s1);
        }
        String url = "https://api.weixin.qq.com/sns/userinfo";
        String userInfoUrl = url + "?access_token=" + accesstoken + "&openid=" + openid ;
        String s = HttpUtil.get(userInfoUrl);
        System.out.println(JSON.parseObject(s));

    }*/

}
