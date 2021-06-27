package org.zero.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.zero.entity.User;


/**
 * @Author Zero
 * @Date 2021/6/3 22:54
 * @Since 1.8
 **/

public interface UserService extends IService<User> {
    User getUserByOpenid(String openId);
}
