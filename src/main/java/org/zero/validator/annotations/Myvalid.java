package org.zero.validator.annotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @Author Zero
 * @Date 2021/6/20 23:57
 * @Since 1.8
 **/
@Target({FIELD, PARAMETER}) //注解的作用目标{
                            //TYPE:接口、类、枚举、注解
                            //FIELD:字段、枚举的常量
                            //METHOD:方法
                            //PARAMETER:方法参数
                            //CONSTRUCTOR:构造函数
                            //LOCAL_VARIABLE:局部变量
                            //ANNOTATION_TYPE:注解
                            //PACKAGE:包
                            //}
@Retention(RUNTIME) //注解的保留策略{
                    // CLASS: 注解会在class字节码文件中存在，但运行时无法获得;
                    // SOURCE: 注解仅存在于源码中，在class字节码文件中不包含;
                    // RUNTIME:注解会在class字节码文件中存在，在运行时可以通过反射获取到
                    // }
@Documented
// 指定此注解的实现，即:验证器
@Constraint(validatedBy ={ZeroValidator.class})
public @interface Myvalid {
    // 当验证不通过时的提示信息
    String message() default "没有包含指定字段";

    // 根据实际需求定的方法
    String contains() default "";

    // 约束注解在验证时所属的组别
    Class<?>[] groups() default { };

    // 负载
    Class<? extends Payload>[] payload() default { };
}
