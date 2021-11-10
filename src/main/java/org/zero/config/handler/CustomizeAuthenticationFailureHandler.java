package org.zero.config.handler;

import com.alibaba.fastjson.JSON;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.authentication.www.NonceExpiredException;
import org.springframework.stereotype.Component;
import org.zero.core.response.CommonReturnType;
import org.zero.validator.code.ValidateCodeException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: Hutengfei
 * @Description: 登录失败处理逻辑
 * @Date Create in 2019/9/3 15:52
 */
@Component
public class CustomizeAuthenticationFailureHandler implements AuthenticationFailureHandler {


    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        //返回json数据
        CommonReturnType result = null;
        if (e instanceof AccountExpiredException) {
            //账号过期
            result = CommonReturnType.fail("账号过期");
        } else if (e instanceof InternalAuthenticationServiceException) {
            //密码错误
            result = CommonReturnType.fail("用户不存在");
        } else if(e instanceof BadCredentialsException) {
            //用户不存在
            result = CommonReturnType.fail(e.getMessage());
        } else if (e instanceof CredentialsExpiredException) {
            //密码过期
            result = CommonReturnType.fail("密码过期");
        } else if (e instanceof DisabledException) {
            //账号不可用
            result = CommonReturnType.fail("账号被禁用");
        } else if (e instanceof LockedException) {
            //账号锁定
            result = CommonReturnType.fail("账号锁定");
        } else if(e instanceof NonceExpiredException) {
            //异地登录
            result = CommonReturnType.fail("异地登录");
        } else if(e instanceof SessionAuthenticationException) {
            //session异常
            result = CommonReturnType.fail("session错误");
        } else if(e instanceof ValidateCodeException) {
            //验证码异常
            result = CommonReturnType.fail(e.getMessage());
        } else {
            //其他未知异常
            result = CommonReturnType.fail(e.getMessage());
        }
        httpServletResponse.setContentType("text/json;charset=utf-8");
        httpServletResponse.getWriter().write(JSON.toJSONString(result));
    }
}
