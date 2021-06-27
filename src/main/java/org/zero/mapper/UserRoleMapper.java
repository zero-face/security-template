package org.zero.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.zero.entity.UserRoleRelation;

/**
 * @Author Zero
 * @Description
 * @Date 2021/6/23 23:03
 * @Since 1.8
 **/
@Mapper
@Repository
public interface UserRoleMapper extends BaseMapper<UserRoleRelation> {
}
