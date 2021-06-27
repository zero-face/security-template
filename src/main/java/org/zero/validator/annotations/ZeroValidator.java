package org.zero.validator.annotations;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @Author Zero
 * @Date 2021/6/21 0:00
 * @Since 1.8
 **/
public class ZeroValidator implements ConstraintValidator<Myvalid, Object> {

    //请求中必须包含的内容
    private String contains;

    @Override
    public void initialize(Myvalid constraintAnnotation) {
        System.out.println(constraintAnnotation.message());
        this.contains = constraintAnnotation.contains();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (null == value) {
            return false;
        }
        if (value instanceof String) {
            String strMessage = (String) value;
            return strMessage.contains(contains);
        } else if (value instanceof Integer) {
            return contains.contains(String.valueOf(value));
        }
        return false;
    }
}
