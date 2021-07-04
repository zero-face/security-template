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
 * @Description TODO
 **/
@Slf4j
@Component
public class ValidateImageCodeFilter extends OncePerRequestFilter {

    @Autowired
    private CustomizeAuthenticationFailureHandler customizeAuthenticationFailureHandler;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (StringUtils.contains(request.getRequestURI(), "login")
                && StringUtils.equalsIgnoreCase(request.getMethod(), "post")) {
            try {
                validateCode(new ServletWebRequest(request));
            } catch (ValidateCodeException e) {
                customizeAuthenticationFailureHandler.onAuthenticationFailure(request, response, e);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
    private void validateCode(ServletWebRequest servletWebRequest) throws ServletRequestBindingException, ValidateCodeException {
        ImageCode codeInSession = (ImageCode) sessionStrategy.getAttribute(servletWebRequest,"SESSION_KEY_IMAGE_CODE");
        String codeInRequest = ServletRequestUtils.getStringParameter(servletWebRequest.getRequest(), "imageCode");

        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException("验证码不能为空 ");
        }
        if (codeInSession == null) {
            throw new ValidateCodeException("验证码不存在！");
        }
        if (codeInSession.isExpire()) {
            sessionStrategy.removeAttribute(servletWebRequest,"SESSION_KEY_IMAGE_CODE");
            throw new ValidateCodeException("验证码已过期！");
        }
        if (!StringUtils.equalsIgnoreCase(codeInSession.getCode(), codeInRequest)) {
            throw new ValidateCodeException("验证码不正确！");
        }
        sessionStrategy.removeAttribute(servletWebRequest,"SESSION_KEY_IMAGE_CODE");
    }

}
