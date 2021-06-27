package org.zero.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.zero.core.error.BusinessException;
import org.zero.entity.UserRoleRelation;

/**
 * @Author Zero
 * @Description
 * @Date 2021/6/23 23:11
 * @Since 1.8
 **/
public interface UserRoleRelationService extends IService<UserRoleRelation> {
    Integer getRoleIdByUserId(Integer userId) throws BusinessException;
}
