package org.zero.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import org.zero.config.handler.*;


import org.zero.config.service.UserNameDetailService;
import org.zero.config.session.CustomizeSessionInformationExpiredStrategy;
import org.zero.filter.UserNameAndJsonFilter;
import org.zero.validator.code.ValidateImageCodeFilter;
import org.zero.validator.smscode.SmsAuthenticationConfig;
import org.zero.validator.smscode.SmsFilter;


import javax.annotation.Resource;
import javax.sql.DataSource;


/**
 * @Author Zero
 * @Date 2021/6/3 23:06
 * @Since 1.8
 **/
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserNameDetailService userdetailservice;

    //未登录处理器（匿名访问无权限处理）
    @Autowired
    private CustomizeAuthenticationEntryPoint customizeAuthenticationEntryPoint;

    //会话过期策略处理器（异地登录）
    @Autowired
    private CustomizeSessionInformationExpiredStrategy customizeSessionInformationExpiredStrategy;

    //自定义用户登录方式过滤器
    @Autowired
    private UserNameAndJsonFilter userNameAndJsonFilter;

    //登录成功处理器
    @Autowired
    private CustomizeAuthenticationSuccessHandler customizeAuthenticationSuccessHandler;

    //登录失败处理器
    @Autowired
    private CustomizeAuthenticationFailureHandler customizeAuthenticationFailureHandler;

    //权限拒绝处理器
    @Autowired
    private CustomizeAccessDeniedHandler customizeAccessDeniedHandler;

    @Autowired
    private CustomizeLogoutSuccessHandler customizeLogoutSuccessHandler;

    @Autowired
    private ValidateImageCodeFilter validateImageCodeFilter;

    @Autowired
    private SmsFilter smsFilter;

    @Autowired
    private SmsAuthenticationConfig smsAuthenticationConfig;

    //访问决策管理器
  /*  @Autowired
    private CustomizeAccessDecisionManager customizeAccessDecisionManager;

    //安全源数据
    @Autowired
    private CustomizeFilterInvocationSecurityMetadataSource customizeFilterInvocationSecurityMetadataSource;

    //权限拦截器
    @Autowired
    private CustomizeAbstractSecurityInterceptor customizeAbstractSecurityInterceptor;*/

    /**
     * 自动登录配置
     */
    @Autowired
    private DataSource dataSource;
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        //jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }

    /**
     * 自定义数据库查寻认证
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userdetailservice).passwordEncoder(passwordEncoder());
    }

    /**
     * 设置加密方式
     * @return
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 重写验证管理器用于认证方式
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
    @Bean
    UserNameAndJsonFilter loginFilter() {
        UserNameAndJsonFilter userNameAndJsonFilter = new UserNameAndJsonFilter();
        userNameAndJsonFilter.setFilterProcessesUrl("/api/v1/user/login");
        userNameAndJsonFilter.setAuthenticationSuccessHandler(customizeAuthenticationSuccessHandler);
        userNameAndJsonFilter.setAuthenticationFailureHandler(customizeAuthenticationFailureHandler);
        return userNameAndJsonFilter;
    }

    /**
     * 配置登录
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //开启跨域以及关闭防护
        http.csrf().disable().cors();
        //注册自定义登录方式过滤器
        http.addFilterBefore(loginFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(validateImageCodeFilter, loginFilter().getClass());
        //短信验证顾虑器
        http.addFilterBefore(smsFilter, ValidateImageCodeFilter.class);
        //将短信验证码认证配置到spring security中
        http.apply(smsAuthenticationConfig);
        //更改未登录或者登录过期默认跳转
        http.exceptionHandling().authenticationEntryPoint(customizeAuthenticationEntryPoint);

        //路径权限
        http.authorizeRequests()
            .antMatchers("/api/v1/user/login","/doc.html"
                    ,"/aip/v1/qrs/cc","/api/v1/user/mobile"
                    ,"/api/v1/user/sms","/api/v1/user/image")
            .permitAll()
            .antMatchers("/usr/add").hasAnyAuthority("admin")
            .anyRequest().authenticated();
        /*http.authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                        //添加权限决策管理器
                        object.setAccessDecisionManager(customizeAccessDecisionManager);
                        //添加安全源数据
                        object.setSecurityMetadataSource(customizeFilterInvocationSecurityMetadataSource);
                        return object;
                    }
                });*/
//        http.addFilterBefore(customizeAbstractSecurityInterceptor, FilterSecurityInterceptor.class);

        //退出登录
        http.logout()
            .logoutUrl("/logout").logoutSuccessUrl("/test/hello").deleteCookies("JSESSIONID")
            .logoutSuccessHandler(customizeLogoutSuccessHandler) //登出成功逻辑处理
        .and()
            .formLogin()
            .successHandler(customizeAuthenticationSuccessHandler) //登录成功逻辑处理
            .failureHandler(customizeAuthenticationFailureHandler) //登录失败逻辑处理
        .and()
            .exceptionHandling()
            .accessDeniedHandler(customizeAccessDeniedHandler) //权限拒绝逻辑处理
            .authenticationEntryPoint(customizeAuthenticationEntryPoint) //匿名访问无权限访问资源异常处理
        //会话管理
        .and()
            .sessionManagement()
            .maximumSessions(1) //同一个用户最大的登录数量
            .expiredSessionStrategy(customizeSessionInformationExpiredStrategy); //异地登录（会话失效）处理逻辑

    }

    public SmsAuthenticationConfig getSmsAuthenticationConfig() {
        return smsAuthenticationConfig;
    }
}
