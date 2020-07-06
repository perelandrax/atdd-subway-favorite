package nextstep.subway.config;

import nextstep.subway.auth.JwtTokenProvider;
import nextstep.subway.auth.application.converter.BasicAuthAuthenticationConverter;
import nextstep.subway.auth.application.converter.FormLoginAuthenticationConverter;
import nextstep.subway.auth.application.provider.AuthenticationProvider;
import nextstep.subway.auth.application.provider.UserDetailsService;
import nextstep.subway.auth.ui.AuthenticationPrincipalArgumentResolver;
import nextstep.subway.auth.ui.authentication.SessionAuthenticationInterceptor;
import nextstep.subway.auth.ui.authentication.TokenAuthenticationInterceptor;
import nextstep.subway.auth.ui.authorization.SessionSecurityContextInterceptor;
import nextstep.subway.auth.ui.authorization.TokenSecurityContextInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private UserDetailsService userDetailsService;
    private JwtTokenProvider jwtTokenProvider;

    public WebMvcConfig(UserDetailsService userDetailsService, JwtTokenProvider jwtTokenProvider) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(createSecurityContextInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(createTokenSecurityContextInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(createAuthenticationSessionInterceptor()).addPathPatterns("/login/session");
        registry.addInterceptor(createAuthenticationTokenInterceptor()).addPathPatterns("/login/token");
    }

    @Override
    public void addArgumentResolvers(List argumentResolvers) {
        argumentResolvers.add(createAuthenticationArgumentResolver());
    }

    private HandlerInterceptor createSecurityContextInterceptor() {
        return new SessionSecurityContextInterceptor();
    }

    private HandlerInterceptor createTokenSecurityContextInterceptor() {
        return new TokenSecurityContextInterceptor(jwtTokenProvider);
    }

    private HandlerInterceptor createAuthenticationSessionInterceptor() {
        return new SessionAuthenticationInterceptor(new FormLoginAuthenticationConverter(), new AuthenticationProvider(userDetailsService));
    }

    private HandlerInterceptor createAuthenticationTokenInterceptor() {
        return new TokenAuthenticationInterceptor(new BasicAuthAuthenticationConverter(), new AuthenticationProvider(userDetailsService), jwtTokenProvider);
    }

    private AuthenticationPrincipalArgumentResolver createAuthenticationArgumentResolver() {
        return new AuthenticationPrincipalArgumentResolver();
    }
}
