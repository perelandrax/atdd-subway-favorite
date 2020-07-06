package nextstep.subway.auth.application.converter;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class FormLoginAuthenticationConverter implements AuthenticationConverter {
    @Override
    public AuthenticationToken convert(HttpServletRequest request) {
        Map<String, String[]> paramMap = request.getParameterMap();
        String principal = paramMap.get("principal")[0];
        String credentials = paramMap.get("credentials")[0];

        return new AuthenticationToken(principal, credentials);
    }
}
