package nextstep.subway.auth.ui.authentication;

import nextstep.subway.auth.application.context.Authentication;
import nextstep.subway.auth.application.converter.AuthenticationConverter;
import nextstep.subway.auth.application.converter.AuthenticationToken;
import nextstep.subway.auth.application.provider.AuthenticationProvider;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class AuthenticationInterceptor implements HandlerInterceptor {
    private AuthenticationConverter authenticationConverter;
    private AuthenticationProvider authenticationProvider;

    public AuthenticationInterceptor(AuthenticationConverter authenticationConverter, AuthenticationProvider authenticationProvider) {
        this.authenticationConverter = authenticationConverter;
        this.authenticationProvider = authenticationProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        AuthenticationToken token = authenticationConverter.convert(request);
        Authentication authentication = authenticationProvider.authenticate(token);
        afterAuthentication(request, response, authentication);

        return false;
    }

    public abstract void afterAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException;
}
