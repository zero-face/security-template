package org.zero.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.zero.entity.RolePermissionRelation;

import java.util.List;

/**
 * @Author Zero
 * @Description
 * @Date 2021/6/23 23:09
 * @Since 1.8
 **/
public interface RolePermissionRelationService extends IService<RolePermissionRelation> {
    List<Integer> getPermissionsIdByRoleId(Integer roleId);
}
