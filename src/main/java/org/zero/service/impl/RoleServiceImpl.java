package org.zero.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zero.entity.SysRole;
import org.zero.mapper.RoleMapper;
import org.zero.service.RoleService;

/**
 * @Author Zero
 * @Description
 * @Date 2021/6/23 23:08
 * @Since 1.8
 **/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, SysRole> implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Integer getPermissionCodeByRoleId(Integer roleId) {
        SysRole role = roleMapper.selectOne(new QueryWrapper<SysRole>().eq("id", roleId));
        return role.getId();
    }
}
