package nextstep.subway.auth.application.converter;

import nextstep.subway.auth.AuthorizationExtractor;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;

public class BasicAuthAuthenticationConverter implements AuthenticationConverter {

    @Override
    public AuthenticationToken convert(HttpServletRequest request) {
        String token = AuthorizationExtractor.extract(request, "Basic");

        byte[] decodedBytes = Base64.getDecoder().decode(token);
        String decodedString = new String(decodedBytes);

        String principal = decodedString.split(":")[0];
        String credentials = decodedString.split(":")[1];

        return new AuthenticationToken(principal, credentials);
    }
}
