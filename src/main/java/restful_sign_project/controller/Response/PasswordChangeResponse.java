package restful_sign_project.controller.Response;

import lombok.Builder;
import lombok.Data;
import restful_sign_project.controller.status.ResponseMessage;
import restful_sign_project.controller.status.StatusCode;
@Data
public class PasswordChangeResponse {
    private int code;
    private String message;
    private Object data;

    public PasswordChangeResponse() {
        this.code = StatusCode.DB_ERROR;
        this.message = ResponseMessage.PASSWORD_ERROR;
        this.data = null;
    }
    @Builder
    public PasswordChangeResponse(int code, String message, Object data){
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
