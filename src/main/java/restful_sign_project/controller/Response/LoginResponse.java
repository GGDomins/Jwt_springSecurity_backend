package restful_sign_project.controller.Response;

import lombok.Builder;
import lombok.Data;
import restful_sign_project.controller.status.ResponseMessage;
import restful_sign_project.controller.status.StatusCode;

import java.util.List;
@Data
public class LoginResponse {
    private int code;
    private String message;
    private Object token;

    public LoginResponse() {
        this.code = code = StatusCode.BAD_REQUEST;
        this.message = ResponseMessage.LOGIN_FAILED;
        this.token = null;
    }

    @Builder
    public LoginResponse(int code, String message, Object token) {
        this.code = code;
        this.message = message;
        this.token = token;
    }
}
