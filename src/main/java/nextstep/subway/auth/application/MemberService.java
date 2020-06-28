package nextstep.subway.auth.application;

import nextstep.subway.auth.dto.MemberRequest;
import nextstep.subway.auth.dto.MemberResponse;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    public MemberResponse createMember(MemberRequest request) {
        return null;
    }

    public MemberResponse findMemberByEmail(String email) {
        return null;
    }

    public void updateMember(Long id, MemberRequest param) {

    }

    public void deleteMember(Long id) {

    }
}
