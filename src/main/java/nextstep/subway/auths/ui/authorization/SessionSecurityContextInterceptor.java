package nextstep.subway.auths.ui.authorization;

import nextstep.subway.auths.application.context.SecurityContext;
import nextstep.subway.auths.application.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static nextstep.subway.auths.application.context.SecurityContextHolder.SECURITY_CONTEXT;

public class SessionSecurityContextInterceptor extends SecurityContextInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        SecurityContext securityContext = (SecurityContext) request.getSession().getAttribute(SECURITY_CONTEXT);
        if (securityContext != null) {
            SecurityContextHolder.setContext(securityContext);
        }

        return true;
    }
}
