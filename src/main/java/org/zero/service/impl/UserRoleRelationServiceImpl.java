package org.zero.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;
import org.zero.core.error.BusinessException;
import org.zero.core.error.EmBusinessError;
import org.zero.entity.UserRoleRelation;
import org.zero.mapper.UserRoleMapper;
import org.zero.service.UserRoleRelationService;

/**
 * @Author Zero
 * @Description
 * @Date 2021/6/23 23:12
 * @Since 1.8
 **/
@Service
public class UserRoleRelationServiceImpl extends ServiceImpl<UserRoleMapper, UserRoleRelation> implements UserRoleRelationService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public Integer getRoleIdByUserId(Integer userId) throws BusinessException {
        UserRoleRelation userRoleRelation = userRoleMapper.selectOne(new QueryWrapper<UserRoleRelation>().eq("user_id", userId));
        if(null == userRoleRelation) {
            throw new BusinessException(EmBusinessError.PRIMARY_ERROR, "该用户咩有角色");
        }
        return userRoleRelation.getRoleId();
    }
}
