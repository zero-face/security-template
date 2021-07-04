package org.zero.validator.code;

import org.springframework.security.core.AuthenticationException;

/**
 * @Author Zero
 * @Date 2021/7/2 0:31
 * @Since 1.8
 * @Description TODO
 **/
public class ValidateCodeException extends AuthenticationException {
    private static final long serialVersionUID = 5022575393500654458L;
    public ValidateCodeException(String message) {
        super(message);
    }
}
