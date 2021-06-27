package org.zero.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zero.core.error.BusinessException;
import org.zero.core.error.EmBusinessError;
import org.zero.entity.PathPermissionRelation;
import org.zero.mapper.PathPermissionRelationMapper;
import org.zero.service.PathPermissionRelationService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Zero
 * @Description
 * @Date 2021/6/23 22:59
 * @Since 1.8
 **/
@Service
public class PathPermissionRelationServiceImpl extends ServiceImpl<PathPermissionRelationMapper, PathPermissionRelation> implements PathPermissionRelationService {

    @Autowired
    private PathPermissionRelationMapper pathPermissionRelationMapper;

    @Override
    public List<Integer> getPermissionByPathId(Integer pathId) throws BusinessException {
        if(null == pathId) {
            return null;
        }
        List<PathPermissionRelation> pathPermissionRelations = pathPermissionRelationMapper.selectList(new QueryWrapper<PathPermissionRelation>().eq("url_id", pathId));
        if(null == pathPermissionRelations || pathPermissionRelations.isEmpty()) {
            throw new BusinessException(EmBusinessError.PRIMARY_ERROR, "该路径没有设置权限");
        }
        return pathPermissionRelations.stream().map(pathPermissionRelation -> pathPermissionRelation.getPermissionId()).collect(Collectors.toList());
    }
}
