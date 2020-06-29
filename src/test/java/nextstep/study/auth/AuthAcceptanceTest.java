package nextstep.study.auth;

import io.restassured.RestAssured;
import io.restassured.authentication.FormAuthConfig;
import nextstep.subway.AcceptanceTest;
import nextstep.subway.SubwayApplication;
import nextstep.subway.member.dto.MemberResponse;
import nextstep.subway.member.dto.TokenResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = SubwayApplication.class)
public class AuthAcceptanceTest extends AcceptanceTest {
    private static final String EMAIL = "email@email.com";
    private static final String PASSWORD = "password";
    private static final Integer AGE = 20;

    @DisplayName("Session")
    @Test
    void myInfoWithSession() {
        회원_등록되어_있음(EMAIL, PASSWORD, AGE);

        MemberResponse memberResponse = RestAssured.given().log().all().
                auth().form(EMAIL, PASSWORD, new FormAuthConfig("/login/session", "email", "password")).
                accept(MediaType.APPLICATION_JSON_VALUE).
                when().
                get("/me/session").
                then().
                log().all().
                statusCode(HttpStatus.OK.value()).
                extract().as(MemberResponse.class);

        회원_정보_응답됨(memberResponse);
    }

    @DisplayName("Basic Auth")
    @Test
    void myInfoWithBasicAuth() {
        회원_등록되어_있음(EMAIL, PASSWORD, AGE);

        MemberResponse memberResponse = RestAssured.given().log().all().
                auth().preemptive().basic(EMAIL, PASSWORD).
                accept(MediaType.APPLICATION_JSON_VALUE).
                when().
                get("/me/basic").
                then().
                log().all().
                statusCode(HttpStatus.OK.value()).
                extract().as(MemberResponse.class);

        회원_정보_응답됨(memberResponse);
    }


    @DisplayName("Bearer Auth")
    @Test
    void myInfoWithBearerAuth() {
        회원_등록되어_있음(EMAIL, PASSWORD, AGE);
        TokenResponse tokenResponse = login(EMAIL, PASSWORD);

        MemberResponse memberResponse = myInfoWithBearerAuth(tokenResponse);
        회원_정보_응답됨(memberResponse);
    }

    public MemberResponse myInfoWithBearerAuth(TokenResponse tokenResponse) {
        return
                RestAssured.given().log().all().
                        auth().oauth2(tokenResponse.getAccessToken()).
                        accept(MediaType.APPLICATION_JSON_VALUE).
                        when().
                        get("/me/bearer").
                        then().
                        log().all().
                        statusCode(HttpStatus.OK.value()).
                        extract().as(MemberResponse.class);
    }

    public TokenResponse login(String email, String password) {
        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);

        return
                RestAssured.given().log().all().
                        body(params).
                        contentType(MediaType.APPLICATION_JSON_VALUE).
                        accept(MediaType.APPLICATION_JSON_VALUE).
                        when().
                        post("/oauth/token").
                        then().
                        log().all().
                        statusCode(HttpStatus.OK.value()).
                        extract().as(TokenResponse.class);
    }

    public String 회원_등록되어_있음(String email, String password, Integer age) {
        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);
        params.put("age", age + "");

        return
                RestAssured.given().log().all().
                        body(params).
                        contentType(MediaType.APPLICATION_JSON_VALUE).
                        accept(MediaType.APPLICATION_JSON_VALUE).
                        when().
                        post("/members").
                        then().
                        log().all().
                        statusCode(HttpStatus.CREATED.value()).
                        extract().header("Location");
    }

    private void 회원_정보_응답됨(MemberResponse memberResponse) {
        assertThat(memberResponse.getId()).isNotNull();
        assertThat(memberResponse.getEmail()).isEqualTo(EMAIL);
        assertThat(memberResponse.getAge()).isEqualTo(AGE);
    }
}
