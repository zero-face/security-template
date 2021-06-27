package org.zero.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.zero.entity.User;

/**
 * @Author Zero
 * @Date 2021/6/3 22:57
 * @Since 1.8
 **/
@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {
}
