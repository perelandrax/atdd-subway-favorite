package nextstep.subway.auth.acceptance;

import io.restassured.RestAssured;
import io.restassured.authentication.FormAuthConfig;
import nextstep.subway.AcceptanceTest;
import nextstep.subway.auth.dto.MemberResponse;
import nextstep.subway.auth.dto.TokenResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthAcceptanceTest extends AcceptanceTest {
    private static final String EMAIL = "email@email.com";
    private static final String PASSWORD = "password";
    private static final Integer AGE = 20;

    @DisplayName("Basic Auth")
    @Test
    void myInfoWithBasicAuth() {
        createMember(EMAIL, PASSWORD, AGE);

        MemberResponse memberResponse = myInfoWithBasicAuth(EMAIL, PASSWORD);

        assertThat(memberResponse.getId()).isNotNull();
        assertThat(memberResponse.getEmail()).isEqualTo(EMAIL);
        assertThat(memberResponse.getAge()).isEqualTo(AGE);
    }


    @DisplayName("Session")
    @Test
    void myInfoWithSession() {
        createMember(EMAIL, PASSWORD, AGE);

        MemberResponse memberResponse = myInfoWithSession(EMAIL, PASSWORD);

        assertThat(memberResponse.getId()).isNotNull();
        assertThat(memberResponse.getEmail()).isEqualTo(EMAIL);
        assertThat(memberResponse.getAge()).isEqualTo(AGE);
    }

    @DisplayName("Bearer Auth")
    @Test
    void myInfoWithBearerAuth() {
        createMember(EMAIL, PASSWORD, AGE);
        TokenResponse tokenResponse = login(EMAIL, PASSWORD);

        MemberResponse memberResponse = myInfoWithBearerAuth(tokenResponse);
        assertThat(memberResponse.getId()).isNotNull();
        assertThat(memberResponse.getEmail()).isEqualTo(EMAIL);
        assertThat(memberResponse.getAge()).isEqualTo(AGE);
    }

    public MemberResponse myInfoWithBasicAuth(String email, String password) {
        return
                RestAssured.given().log().all().
                        auth().preemptive().basic(email, password).
                        accept(MediaType.APPLICATION_JSON_VALUE).
                        when().
                        get("/me/basic").
                        then().
                        log().all().
                        statusCode(HttpStatus.OK.value()).
                        extract().as(MemberResponse.class);
    }

    public MemberResponse myInfoWithSession(String email, String password) {
        return
                RestAssured.given().log().all().
                        auth().form(email, password, new FormAuthConfig("/login", "email", "password")).
                        accept(MediaType.APPLICATION_JSON_VALUE).
                        when().
                        get("/me/session").
                        then().
                        log().all().
                        statusCode(HttpStatus.OK.value()).
                        extract().as(MemberResponse.class);
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

    public String createMember(String email, String password, Integer age) {
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
}
