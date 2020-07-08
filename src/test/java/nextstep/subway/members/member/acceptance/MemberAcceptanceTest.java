package nextstep.subway.members.member.acceptance;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.subway.AcceptanceTest;
import nextstep.subway.auths.dto.TokenResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static nextstep.subway.members.member.acceptance.step.MemberAcceptanceStep.로그인_되어_있음;
import static nextstep.subway.members.member.acceptance.step.MemberAcceptanceStep.*;

@DisplayName("회원 관련 기능")
public class MemberAcceptanceTest extends AcceptanceTest {

    public static final String EMAIL = "email@email.com";
    public static final String PASSWORD = "password";
    public static final int AGE = 20;

    @DisplayName("회원가입을 한다.")
    @Test
    void createMember() {
        // when
        ExtractableResponse<Response> response = 회원_생성을_요청(EMAIL, PASSWORD, 20);

        // then
        회원_생성됨(response);
    }

    @DisplayName("회원 정보를 조회한다.")
    @Test
    void getMember() {
        // given
        회원_등록되어_있음(EMAIL, PASSWORD, 20);
        TokenResponse token = 로그인_되어_있음(EMAIL, PASSWORD);

        // when
        ExtractableResponse<Response> response = 회원_정보_조회_요청(token);

        // then
        회원_정보_조회됨(response);

    }

    @DisplayName("회원 정보를 수정한다.")
    @Test
    void updateMember() {
        // given
        회원_등록되어_있음(EMAIL, PASSWORD, AGE);
        TokenResponse token = 로그인_되어_있음(EMAIL, PASSWORD);

        // when
        ExtractableResponse<Response> response = 회원_정보_수정_요청(token, "new" + EMAIL, "new" + PASSWORD, AGE);

        // then
        회원_정보_수정됨(response);
    }

    @DisplayName("회원 정보를 삭제한다.")
    @Test
    void deleteMember() {
        // given
        회원_등록되어_있음(EMAIL, PASSWORD, 20);
        TokenResponse token = 로그인_되어_있음(EMAIL, PASSWORD);

        // when
        ExtractableResponse<Response> response = 회원_삭제_요청(token);

        // then
        회원_삭제됨(response);
    }
}
