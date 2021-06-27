package org.zero.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author Zero
 * @Description
 * @Date 2021/6/23 22:26
 * @Since 1.8
 **/
@Data
@TableName("sys_role")
public class SysRole {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String RoleName;

    private String RoleCode;

    private String description;
}
