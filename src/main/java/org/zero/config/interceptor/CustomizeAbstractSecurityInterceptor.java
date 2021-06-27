package org.zero.config.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.zero.config.handler.CustomizeAccessDecisionManager;
import javax.servlet.*;
import java.io.IOException;

/**
 * @Author Zero
 * @Description 权限拦截器
 * @Date 2021/6/23 21:44
 * @Since 1.8
 **/

@Component
public class CustomizeAbstractSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {

    //注入一个安全数据源
    @Autowired
    private FilterInvocationSecurityMetadataSource CustomizeFilterInvocationSecurityMetadataSource;

    //设置自己写的决策访问管理器
    @Autowired
    public void setMyAccessDecisionManager(CustomizeAccessDecisionManager accessDecisionManager) {
        super.setAccessDecisionManager(accessDecisionManager);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        FilterInvocation fi = new FilterInvocation(request, response, chain);
        invoke(fi);
    }

    private void invoke(FilterInvocation fi) throws IOException, ServletException {
        /*fi里面有一个被拦截的url
        里面调用MyInvocationSecurityMetadataSource的getAttributes(Object object)这个方法获取fi对应的所有权限
        再调用MyAccessDecisionManager的decide方法来校验用户的权限是否足够*/
        InterceptorStatusToken token = super.beforeInvocation(fi);
        try {
            //执行下一个拦截器
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        }finally {
            super.afterInvocation(token, null);
        }
    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    //获取注入的安全数据源
    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.CustomizeFilterInvocationSecurityMetadataSource;
    }

}
