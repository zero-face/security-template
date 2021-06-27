package org.zero.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author Zero
 * @Description
 * @Date 2021/6/23 22:31
 * @Since 1.8
 **/
@Data
@TableName("sys_user_role_relation")
public class UserRoleRelation {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer userId;

    private Integer RoleId;
}
