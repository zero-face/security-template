package org.zero.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.zero.core.error.BusinessException;
import org.zero.entity.PathPermissionRelation;

import java.util.List;

/**
 * @Author Zero
 * @Description
 * @Date 2021/6/23 22:59
 * @Since 1.8
 **/
public interface PathPermissionRelationService extends IService<PathPermissionRelation> {
    List<Integer> getPermissionByPathId(Integer pathId) throws BusinessException;
}
