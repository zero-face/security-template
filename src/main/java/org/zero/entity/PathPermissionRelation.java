package org.zero.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author Zero
 * @Description
 * @Date 2021/6/23 22:45
 * @Since 1.8
 **/
@Data
@TableName("sys_request_path_permission_relation")
public class PathPermissionRelation {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer urlId;

    private Integer permissionId;
}
