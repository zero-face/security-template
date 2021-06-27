package org.zero.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @Author Zero
 * @Date 2021/6/3 22:56
 * @Since 1.8
 **/
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class User {
    @TableId(type = IdType.AUTO)
    private int id;

    private String username;

    private String password;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long lastLoginTime;

    private Boolean enabled;

    private String openId;

    private Boolean accountNotExpired;

    private Boolean accountNotLocked;

    private Boolean credentialsNotExpired;

    @TableField(fill = FieldFill.INSERT)
    private Long createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateTime;

    private Integer createUser;

    private Integer updateUser;
}
