package nextstep.subway.auths.application.converter;

import javax.servlet.http.HttpServletRequest;

public interface AuthenticationConverter {
    AuthenticationToken convert(HttpServletRequest request);
}
