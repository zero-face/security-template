package org.zero.config.handler;

import com.alibaba.fastjson.JSON;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;
import org.zero.core.error.EmBusinessError;
import org.zero.core.response.CommonReturnType;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: Hutengfei
 * @Description: 会话信息过期策略
 * @Date Create in 2019/9/4 9:34
 */
@Component
public class CustomizeSessionInformationExpiredStrategy implements SessionInformationExpiredStrategy {

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent sessionInformationExpiredEvent) throws IOException, ServletException {
        //失效则踢出session
        SessionRegistry sessionRegistry = new SessionRegistryImpl();
        final String sessionId = sessionInformationExpiredEvent.getRequest().getRequestedSessionId();
        sessionRegistry.getSessionInformation(sessionId).expireNow();
        CommonReturnType result = CommonReturnType.fail(EmBusinessError.SERVICE_AUTHENTICATION_INVALID);
        String currentSessionId = sessionInformationExpiredEvent.getRequest().getRequestedSessionId();
        HttpServletResponse httpServletResponse = sessionInformationExpiredEvent.getResponse();
        httpServletResponse.setContentType("text/json;charset=utf-8");
        httpServletResponse.getWriter().write(JSON.toJSONString(result));
    }
}
