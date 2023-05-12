package restful_sign_project.controller.Response;

import lombok.Builder;
import restful_sign_project.controller.status.ResponseMessage;
import restful_sign_project.controller.status.StatusCode;

import java.util.List;

public class LoginResponse {
    private int code;
    private String message;
    private Object data;

    public LoginResponse() {
        this.code = code = StatusCode.BAD_REQUEST;
        this.message = ResponseMessage.EMAIL_EXISTED;
        this.data = null;
    }

    @Builder
    public LoginResponse(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
