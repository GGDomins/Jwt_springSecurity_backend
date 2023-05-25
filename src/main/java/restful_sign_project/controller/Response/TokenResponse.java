package restful_sign_project.controller.Response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenResponse {
    private String AccessToken;
    private String RefreshToken;
}
