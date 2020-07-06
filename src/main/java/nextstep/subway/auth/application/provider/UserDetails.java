package nextstep.subway.auth.application.provider;

public interface UserDetails {
    boolean checkPassword(String password);
}
