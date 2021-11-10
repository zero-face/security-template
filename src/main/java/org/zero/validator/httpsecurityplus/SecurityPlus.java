package org.zero.validator.httpsecurityplus;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.AbstractConfiguredSecurityBuilder;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Zero
 * @Date 2021/7/4 18:49
 * @Since 1.8
 * @Description TODO
 **/
public final class SecurityPlus<FilterComparator> extends
        AbstractConfiguredSecurityBuilder<DefaultSecurityFilterChain, HttpSecurity>
        implements SecurityBuilder<DefaultSecurityFilterChain>,
        HttpSecurityBuilder<HttpSecurity> {

    private final HttpSecurity.RequestMatcherConfigurer requestMatcherConfigurer;
    private List<Filter> filters = new ArrayList<>();
    private RequestMatcher requestMatcher = AnyRequestMatcher.INSTANCE;
//    private FilterComparator comparator = new FilterComparator();


    protected SecurityPlus(ObjectPostProcessor<Object> objectPostProcessor, HttpSecurity.RequestMatcherConfigurer requestMatcherConfigurer) {
        super(objectPostProcessor);
        this.requestMatcherConfigurer = requestMatcherConfigurer;
    }

    @Override
    protected DefaultSecurityFilterChain performBuild() throws Exception {
        return null;
    }

    @Override
    public HttpSecurity authenticationProvider(AuthenticationProvider authenticationProvider) {
        return null;
    }

    @Override
    public HttpSecurity userDetailsService(UserDetailsService userDetailsService) throws Exception {
        return null;
    }

    @Override
    public HttpSecurity addFilterAfter(Filter filter, Class<? extends Filter> afterFilter) {
        return null;
    }

    @Override
    public HttpSecurity addFilterBefore(Filter filter, Class<? extends Filter> beforeFilter) {
        return null;
    }

    @Override
    public HttpSecurity addFilter(Filter filter) {
        return null;
    }
}
