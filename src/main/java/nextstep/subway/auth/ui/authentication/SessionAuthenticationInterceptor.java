package nextstep.subway.auth.ui.authentication;

import nextstep.subway.auth.application.context.Authentication;
import nextstep.subway.auth.application.context.SecurityContext;
import nextstep.subway.auth.application.converter.AuthenticationConverter;
import nextstep.subway.auth.application.provider.AuthenticationProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static nextstep.subway.auth.application.context.SecurityContextHolder.SECURITY_CONTEXT;

public class SessionAuthenticationInterceptor extends AuthenticationInterceptor {

    public SessionAuthenticationInterceptor(AuthenticationConverter authenticationConverter, AuthenticationProvider authenticationProvider) {
        super(authenticationConverter, authenticationProvider);
    }

    @Override
    public void afterAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        request.getSession().setAttribute(SECURITY_CONTEXT, new SecurityContext(authentication));
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
