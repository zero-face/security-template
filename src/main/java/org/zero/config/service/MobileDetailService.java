package org.zero.config.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.zero.entity.User;
import org.zero.mapper.UserMapper;

import java.util.List;

/**
 * @Author Zero
 * @Date 2021/7/4 17:41
 * @Since 1.8
 * @Description TODO
 **/
@Service
public class MobileDetailService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(null == username|| "".equals(username)) {
            throw new UsernameNotFoundException("手机号不能为空");
        }
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("phone", username);
        User user = userMapper.selectOne(wrapper);
        if(null == user) {
            throw new UsernameNotFoundException("用户不存在");
        }
        List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList("admin");
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getEnabled(), user.getAccountNotExpired(), user.getCredentialsNotExpired(), user.getAccountNotLocked(),auths);
    }
}
