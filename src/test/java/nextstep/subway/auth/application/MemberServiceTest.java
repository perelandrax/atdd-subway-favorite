package nextstep.subway.auth.application;

import nextstep.subway.auth.dto.MemberRequest;
import nextstep.subway.auth.dto.MemberResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberServiceTest {
    MemberService memberService;

    @BeforeEach
    void setUp() {
        memberService = new MemberService();
    }

    @Test
    void createMember() {
        MemberRequest memberRequest = new MemberRequest("email@email.com", "password", 20);
        MemberResponse memberResponse = memberService.createMember(memberRequest);
        assertThat(memberResponse).isNotNull();
    }

    @Test
    void findMemberByEmail() {
        MemberResponse memberResponse = memberService.findMemberByEmail("email@email.com");
        assertThat(memberResponse).isNotNull();
    }

    @Test
    void updateMember() {
        MemberRequest memberRequest = new MemberRequest("email@email.com", "password", 20);
        memberService.updateMember(1L, memberRequest);
    }

    @Test
    void deleteMember() {
        memberService.deleteMember(1L);
    }
}
