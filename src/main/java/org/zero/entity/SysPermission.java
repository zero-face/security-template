package org.zero.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * @Author Zero
 * @Description
 * @Date 2021/6/23 22:21
 * @Since 1.8
 **/
@Data
@TableName("sys_permission")
public class SysPermission implements Serializable {

        @TableId(type = IdType.AUTO)
        static final long serialVersionUID = 1L;

        private Integer id;

        private String permissionCode;

        private String permissionName;


}
