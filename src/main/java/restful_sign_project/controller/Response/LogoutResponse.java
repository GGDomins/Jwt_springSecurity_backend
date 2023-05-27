package restful_sign_project.controller.Response;

import lombok.Builder;
import lombok.Data;

@Data
public class LogoutResponse {
    private int code;
    private String message;

    @Builder
    public LogoutResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
