package nextstep.subway.auth.application.converter;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class FormLoginAuthenticationConverter implements AuthenticationConverter {
    public static final String PRINCIPAL = "principal";
    public static final String CREDENTIALS = "credentials";

    @Override
    public AuthenticationToken convert(HttpServletRequest request) {
        Map<String, String[]> paramMap = request.getParameterMap();
        String principal = paramMap.get(PRINCIPAL)[0];
        String credentials = paramMap.get(CREDENTIALS)[0];

        return new AuthenticationToken(principal, credentials);
    }
}
