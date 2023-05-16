package restful_sign_project.controller.Response;

import lombok.Data;
import restful_sign_project.JWT.refresh.RefreshToken;

@Data
public class RefreshTokenResponse {
    RefreshToken ResponseToken;

    public RefreshTokenResponse(RefreshToken responseToken) {
        ResponseToken = responseToken;
    }
}
