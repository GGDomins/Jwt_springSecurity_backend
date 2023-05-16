package restful_sign_project.controller.Response;

import lombok.Builder;
import lombok.Data;
/**
 * 만료된 Access Token을 RefreshToken을 이용해서 재발급 하려고 할 때 반횐되는 Response입니다.
 */
@Data
@Builder
public class RefreshTokenResponse {
    private int code;
    private String message;
    private String accessToken;
    private String refreshToken;
}