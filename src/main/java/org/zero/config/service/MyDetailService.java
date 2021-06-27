package org.zero.config.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.zero.entity.SysPermission;
import org.zero.mapper.PermissionMapper;
import org.zero.mapper.UserMapper;
import org.zero.service.RolePermissionRelationService;
import org.zero.service.RoleService;
import org.zero.service.SysPermissionService;
import org.zero.service.UserRoleRelationService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Zero
 * @Date 2021/6/3 22:58
 * @Since 1.8
 **/
@Service("userdetailservice")
public class MyDetailService implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleRelationService userRoleRelationService;

    @Autowired
    private RolePermissionRelationService rolePermissionRelationService;

    @Autowired
    private SysPermissionService sysPermissionService;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(null == username|| "".equals(username)) {
            throw new UsernameNotFoundException("用户名不能为空");
        }
        QueryWrapper<org.zero.entity.User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        org.zero.entity.User user = userMapper.selectOne(wrapper);
        if(null == user) {
            throw new UsernameNotFoundException("用户不存在");
        }
        Integer roleId = userRoleRelationService.getRoleIdByUserId(user.getId());
        List<Integer> permissionsIds = rolePermissionRelationService.getPermissionsIdByRoleId(roleId);
        List<SysPermission> permissions = sysPermissionService.getPermissionByPermissionId(permissionsIds);
        StringBuffer stringBuffer = new StringBuffer();
        for (SysPermission s:permissions
             ) {
            stringBuffer.append(s.getPermissionCode()+ ",");
        }
        String s1  = stringBuffer.substring(0,stringBuffer.length()-2);
        System.out.println(s1);
        //这里通过查找获取权限
        List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList(s1);

        return new User(user.getUsername(), new BCryptPasswordEncoder().encode(user.getPassword()), user.getEnabled(),user.getAccountNotExpired(),user.getCredentialsNotExpired(),user.getAccountNotLocked(),auths);
    }
}
