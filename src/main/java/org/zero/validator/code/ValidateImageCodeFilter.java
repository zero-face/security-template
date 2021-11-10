package org.zero.validator.code;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;
import org.zero.config.handler.CustomizeAuthenticationFailureHandler;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author Zero
 * @Date 2021/7/2 0:34
 * @Since 1.8
 * @Description 图片验证码过滤器
 **/
@Slf4j
@Component
public class ValidateImageCodeFilter extends OncePerRequestFilter {

    @Autowired
    //自定义验证失败处理器
    private CustomizeAuthenticationFailureHandler customizeAuthenticationFailureHandler;

    //这里我选择将验证码存放在HttpSessionSessionStrategy中（可以使用redis等进行存储）
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //请求路径中是否包含login这个关键词 && 发送的请求必须是post
        if (StringUtils.contains(request.getRequestURI(), "login")
                && StringUtils.equalsIgnoreCase(request.getMethod(), "post")) {
            try {
                //开始验证
                validateCode(new ServletWebRequest(request));
            } catch (ValidateCodeException e) {
                //如果验证失败，就使用自定义验证处理器
                customizeAuthenticationFailureHandler.onAuthenticationFailure(request, response, e);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
    //验证实现
    private void validateCode(ServletWebRequest servletWebRequest) throws ServletRequestBindingException, ValidateCodeException {
        //从SessionStrategy中拿出验证码
        ImageCode codeInSession = (ImageCode) sessionStrategy.getAttribute(servletWebRequest,"SESSION_KEY_IMAGE_CODE");
        //从请求路径中拿出验证码
        String codeInRequest = ServletRequestUtils.getStringParameter(servletWebRequest.getRequest(), "imageCode");
        //验证码判空
        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException("验证码不能为空 ");
        }
        //验证码颁发方验证
        if (codeInSession == null) {
            throw new ValidateCodeException("验证码不存在！");
        }
        //验证码是否过期
        if (codeInSession.isExpire()) {
            sessionStrategy.removeAttribute(servletWebRequest,"SESSION_KEY_IMAGE_CODE");
            throw new ValidateCodeException("验证码已过期！");
        }
        //验证码正确性
        if (!StringUtils.equalsIgnoreCase(codeInSession.getCode(), codeInRequest)) {
            throw new ValidateCodeException("验证码不正确！");
        }
        //移除服务端的验证码存储
        sessionStrategy.removeAttribute(servletWebRequest,"SESSION_KEY_IMAGE_CODE");
    }

}
