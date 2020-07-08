package nextstep.subway.auths.application.provider;

public interface UserDetailsService {
    UserDetails loadUserByUsername(String username);
}
