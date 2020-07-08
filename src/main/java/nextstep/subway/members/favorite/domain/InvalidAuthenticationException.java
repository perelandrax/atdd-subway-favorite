package nextstep.subway.members.favorite.domain;

public class InvalidAuthenticationException extends RuntimeException {
    public InvalidAuthenticationException(String message) {
        super(message);
    }
}
