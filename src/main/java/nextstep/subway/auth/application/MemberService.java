package nextstep.subway.auth.application;

import nextstep.subway.auth.domain.Member;
import nextstep.subway.auth.domain.MemberRepository;
import nextstep.subway.auth.dto.MemberRequest;
import nextstep.subway.auth.dto.MemberResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MemberService {
    private MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberResponse createMember(MemberRequest request) {
        Member member = memberRepository.save(request.toMember());
        return MemberResponse.of(member);
    }

    public MemberResponse findMemberById(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(RuntimeException::new);
        return MemberResponse.of(member);
    }

    public void updateMember(Long id, MemberRequest param) {
        Member member = memberRepository.findById(id).orElseThrow(RuntimeException::new);
        member.update(param.toMember());
    }

    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }
}
