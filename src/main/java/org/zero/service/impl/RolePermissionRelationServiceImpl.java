package org.zero.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zero.entity.RolePermissionRelation;
import org.zero.mapper.RolePermissionRelationMapper;
import org.zero.service.RolePermissionRelationService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Zero
 * @Description
 * @Date 2021/6/23 23:10
 * @Since 1.8
 **/
@Service
public class RolePermissionRelationServiceImpl extends ServiceImpl<RolePermissionRelationMapper, RolePermissionRelation> implements RolePermissionRelationService {
    @Autowired
    private  RolePermissionRelationMapper rolePermissionRelationMapper;
    @Override
    public List<Integer> getPermissionsIdByRoleId(Integer roleId) {
        List<RolePermissionRelation> rolePermissionRelations = rolePermissionRelationMapper.selectList(new QueryWrapper<RolePermissionRelation>().eq("role_id", roleId));
        final List<Integer> integerList = rolePermissionRelations.stream().map(item -> {
            Integer permissionId = item.getPermissionId();
            return permissionId;
        }).collect(Collectors.toList());
        return integerList;
    }
}
