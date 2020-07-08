package nextstep.subway.auths.application.converter;

import nextstep.subway.auths.AuthorizationExtractor;
import nextstep.subway.auths.AuthorizationType;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;

public class BasicAuthAuthenticationConverter implements AuthenticationConverter {
    public static final String REGEX = ":";

    @Override
    public AuthenticationToken convert(HttpServletRequest request) {
        String token = AuthorizationExtractor.extract(request, AuthorizationType.BASIC);

        byte[] decodedBytes = Base64.getDecoder().decode(token);
        String decodedString = new String(decodedBytes);

        String principal = decodedString.split(REGEX)[0];
        String credentials = decodedString.split(REGEX)[1];

        return new AuthenticationToken(principal, credentials);
    }
}
