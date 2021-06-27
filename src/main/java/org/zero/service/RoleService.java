package org.zero.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.zero.entity.SysRole;

/**
 * @Author Zero
 * @Description
 * @Date 2021/6/23 23:07
 * @Since 1.8
 **/
public interface RoleService extends IService<SysRole> {
    Integer getPermissionCodeByRoleId(Integer roleId);
}
