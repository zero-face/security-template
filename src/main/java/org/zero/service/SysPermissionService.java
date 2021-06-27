package org.zero.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zero.core.error.BusinessException;
import org.zero.entity.SysPermission;
import org.zero.mapper.UserMapper;

import java.util.List;

/**
 * @Author Zero
 * @Description
 * @Date 2021/6/23 22:17
 * @Since 1.8
 **/
@Service
public interface SysPermissionService extends IService<SysPermission> {
    SysPermission getPermissionByPermissionId(Integer permissionId) throws BusinessException;

    List<SysPermission> getPermissionByPermissionId(List<Integer> permissionId) throws BusinessException;
}
