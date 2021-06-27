package org.zero.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zero.core.error.BusinessException;
import org.zero.core.error.EmBusinessError;
import org.zero.entity.SysPermission;
import org.zero.mapper.PermissionMapper;
import org.zero.service.SysPermissionService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Zero
 * @Description
 * @Date 2021/6/23 22:51
 * @Since 1.8
 **/
@Service
public class SysPermissionServiceImpl extends ServiceImpl<PermissionMapper, SysPermission> implements SysPermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public SysPermission getPermissionByPermissionId(Integer permissionId) throws BusinessException {
        SysPermission sysPermission = permissionMapper.selectById(permissionId);
        if(null == sysPermission) {
            throw new BusinessException(EmBusinessError.PRIMARY_ERROR,"没有该权限");
        }
        return sysPermission;
    }

    @Override
    public List<SysPermission> getPermissionByPermissionId(List<Integer> permissionId) throws BusinessException {
        final List<SysPermission> sysPermissions = permissionId.stream().map(item -> {
            SysPermission sysPermission = permissionMapper.selectById(item);
            return sysPermission;
        }).collect(Collectors.toList());
        return sysPermissions;
    }
}
