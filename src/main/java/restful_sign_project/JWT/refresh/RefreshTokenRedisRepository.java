package restful_sign_project.JWT.refresh;

import org.springframework.data.repository.CrudRepository;
import restful_sign_project.JWT.refresh.RefreshToken;

public interface RefreshTokenRedisRepository extends CrudRepository<RefreshToken, Long> {
    RefreshToken findByRefreshToken(String refreshToken);
}
