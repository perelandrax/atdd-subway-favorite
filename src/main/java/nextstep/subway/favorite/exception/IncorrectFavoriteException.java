package nextstep.subway.favorite.exception;

public class IncorrectFavoriteException extends RuntimeException {

    public static final String EXCEPTION_MESSAGE = "잘못된 즐겨찾기를 요청하였습니다.";

    public IncorrectFavoriteException() {
        super(EXCEPTION_MESSAGE);
    }
}
