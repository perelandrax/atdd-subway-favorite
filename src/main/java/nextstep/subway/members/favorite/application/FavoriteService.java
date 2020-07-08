package nextstep.subway.members.favorite.application;

import nextstep.subway.members.favorite.application.dto.FavoriteRequest;
import nextstep.subway.members.favorite.domain.Favorite;
import nextstep.subway.members.favorite.domain.FavoriteRepository;
import nextstep.subway.members.member.domain.LoginMember;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService {
    private FavoriteRepository favoriteRepository;

    public FavoriteService(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    public void createFavorite(LoginMember loginMember, FavoriteRequest request) {
        Favorite favorite = new Favorite(loginMember.getId(), request.getSource(), request.getTarget());
        favoriteRepository.save(favorite);
    }

    public void deleteFavorite(LoginMember loginMember, Long id) {
        Favorite favorite = favoriteRepository.findById(id).orElseThrow(RuntimeException::new);
        favorite.checkMember(loginMember);
        favoriteRepository.deleteById(id);
    }

    public List<Favorite> findFavoritesByMember(LoginMember loginMember) {
        return favoriteRepository.findByMemberId(loginMember.getId());
    }
}
