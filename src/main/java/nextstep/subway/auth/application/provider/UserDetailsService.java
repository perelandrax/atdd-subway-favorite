package nextstep.subway.auth.application.provider;

public interface UserDetailsService {
    UserDetails loadUserByUsername(String username);
}
