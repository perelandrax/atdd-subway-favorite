package nextstep.subway.member.application;

import nextstep.subway.member.domain.Member;
import nextstep.subway.member.domain.MemberRepository;
import nextstep.subway.member.dto.MemberRequest;
import nextstep.subway.member.dto.MemberResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {
    @Mock
    private MemberRepository memberRepository;

    private MemberService memberService;

    @BeforeEach
    void setUp() {
        memberService = new MemberService(memberRepository);
    }

    @Test
    void createMember() {
        when(memberRepository.save(any())).thenReturn(new Member("email@email.com", "password", 20));
        MemberRequest memberRequest = new MemberRequest("email@email.com", "password", 20);
        MemberResponse memberResponse = memberService.createMember(memberRequest);
        assertThat(memberResponse).isNotNull();
    }

    @Test
    void findById() {
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(new Member("email@email.com", "password", 30)));

        MemberResponse memberResponse = memberService.findMember(1L);
        assertThat(memberResponse).isNotNull();
    }

    @Test
    void updateMember() {
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(new Member("email@email.com", "password", 30)));

        MemberRequest memberRequest = new MemberRequest("email@email.com", "password", 20);
        memberService.updateMember(1L, memberRequest);
    }

    @Test
    void deleteMember() {
        memberService.deleteMember(1L);
    }
}
