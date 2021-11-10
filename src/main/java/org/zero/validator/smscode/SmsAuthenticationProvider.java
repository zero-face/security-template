package org.zero.validator.smscode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.zero.config.service.MobileDetailService;

/**
 * @Author Zero
 * @Date 2021/7/4 15:16
 * @Since 1.8
 * @Description  开始进行验证
 **/

public class SmsAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private MobileDetailService mobileDetailService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsAuthenticationToken authenticationToken = (SmsAuthenticationToken) authentication;
        UserDetails userDetails = mobileDetailService.loadUserByUsername((String) authenticationToken.getPrincipal());

        if (null == userDetails) {
            throw new InternalAuthenticationServiceException("未找到与该手机号对应的用户");
        }
        //标记这个验证结果为已验证
        SmsAuthenticationToken authenticationResult = new SmsAuthenticationToken(userDetails, userDetails.getAuthorities());

        authenticationResult.setDetails(authenticationToken.getDetails());

        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return SmsAuthenticationToken.class.isAssignableFrom(aClass);
    }

    public UserDetailsService getUserDetailService() {
        return mobileDetailService;
    }

    public void setUserDetailService(MobileDetailService mobileDetailService) {
        this.mobileDetailService =  mobileDetailService;
    }
}
