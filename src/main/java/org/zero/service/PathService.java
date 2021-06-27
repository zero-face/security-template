package org.zero.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.zero.core.error.BusinessException;
import org.zero.entity.Path;

/**
 * @Author Zero
 * @Description
 * @Date 2021/6/23 23:04
 * @Since 1.8
 **/
public interface PathService extends IService<Path> {
    Integer getPathIdByName(String url) throws BusinessException;
}
