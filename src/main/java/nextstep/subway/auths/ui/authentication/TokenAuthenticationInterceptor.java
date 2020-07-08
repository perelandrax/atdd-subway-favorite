package nextstep.subway.auths.ui.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.subway.auths.JwtTokenProvider;
import nextstep.subway.auths.application.context.Authentication;
import nextstep.subway.auths.application.converter.AuthenticationConverter;
import nextstep.subway.auths.application.provider.AuthenticationProvider;
import nextstep.subway.auths.dto.TokenResponse;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenAuthenticationInterceptor extends AuthenticationInterceptor {
    private JwtTokenProvider jwtTokenProvider;

    public TokenAuthenticationInterceptor(AuthenticationConverter authenticationConverter, AuthenticationProvider authenticationProvider, JwtTokenProvider jwtTokenProvider) {
        super(authenticationConverter, authenticationProvider);
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void afterAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String payload = new ObjectMapper().writeValueAsString(authentication.getPrincipal());
        String token = jwtTokenProvider.createToken(payload);
        TokenResponse tokenResponse = new TokenResponse(token);

        String responseToClient = new ObjectMapper().writeValueAsString(tokenResponse);
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getOutputStream().print(responseToClient);
    }
}
