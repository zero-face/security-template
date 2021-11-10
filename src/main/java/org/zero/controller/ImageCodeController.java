package org.zero.controller;

import org.springframework.http.HttpStatus;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.zero.core.response.CommonReturnType;
import org.zero.utils.ImageCodeUtil;
import org.zero.utils.RandomSmsUtil;
import org.zero.validator.code.ImageCode;
import org.zero.validator.smscode.SmsCode;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author Zero
 * @Date 2021/7/3 0:23
 * @Since 1.8
 * @Description TODO
 **/
@RestController
@RequestMapping("/api/v1/user")
public class ImageCodeController extends BaseController {

    @RequestMapping("/image")
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ImageCode imageCode = ImageCodeUtil.createImageCode();
        ImageCode codeInRedis = new ImageCode(null,imageCode.getCode(),imageCode.getExpireTime());
        new HttpSessionSessionStrategy().setAttribute(new ServletWebRequest(request), "SESSION_KEY_IMAGE_CODE", codeInRedis);
        response.setContentType("image/jpeg;charset=utf-8");
        response.setStatus(HttpStatus.OK.value());
        ImageIO.write(imageCode.getImage(), "jpeg", response.getOutputStream());
    }
    @RequestMapping("/sms")
    public void createSms(HttpServletRequest request,HttpServletResponse response,String mobile) throws IOException {
        SmsCode smsCode = RandomSmsUtil.createSMSCode();
        new HttpSessionSessionStrategy().setAttribute(new ServletWebRequest(request),"SESSION_KEY_SMS_CODE" + mobile,smsCode);
        response.getWriter().write(smsCode.getCode());
        System.out.println("您的验证码信息为：" + smsCode.getCode() + "有效时间为：" + smsCode.getExpireTime());
    }
    /*@RequestMapping("/mobile")
    public void mobileLogin(HttpServletRequest request,HttpServletResponse response,String mobile,String ) {

    }*/

}
