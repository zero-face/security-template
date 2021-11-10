package org.zero.utils;

import org.apache.commons.lang.RandomStringUtils;
import org.zero.validator.smscode.SmsCode;

/**
 * @Author Zero
 * @Date 2021/7/4 15:28
 * @Since 1.8
 * @Description TODO
 **/
public class RandomSmsUtil {

    private final  static int expiredTime = 300;
    public static SmsCode createSMSCode() {
        String smsCode = RandomStringUtils.randomNumeric(6);
        return new SmsCode(smsCode, expiredTime);
    }
}
