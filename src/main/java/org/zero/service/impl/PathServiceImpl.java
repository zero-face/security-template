package org.zero.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.zero.core.error.BusinessException;
import org.zero.core.error.EmBusinessError;
import org.zero.entity.Path;
import org.zero.mapper.PathMapper;
import org.zero.service.PathService;

/**
 * @Author Zero
 * @Description
 * @Date 2021/6/23 23:05
 * @Since 1.8
 **/
@Service
public class PathServiceImpl extends ServiceImpl<PathMapper, Path> implements PathService {

    @Autowired
    private PathMapper pathMapper;

    @Override
    public Integer getPathIdByName(String url) throws BusinessException {
        Path path = pathMapper.selectOne(new QueryWrapper<Path>().eq("url", url));
        if(null == path) {
            //表明数据库没有配置该路径，所以任意访问
            return null;
        }
        return path.getId();
    }

}
