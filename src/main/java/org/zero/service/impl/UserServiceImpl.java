package org.zero.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zero.entity.User;
import org.zero.mapper.UserMapper;
import org.zero.service.UserService;

/**
 * @Author Zero
 * @Date 2021/6/3 22:56
 * @Since 1.8
 **/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserByOpenid(String openId) {
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("open_id", openId));
        return user;
    }
}
