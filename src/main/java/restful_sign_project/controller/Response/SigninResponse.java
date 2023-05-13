package restful_sign_project.controller.Response;

import lombok.Builder;
import lombok.Data;
import restful_sign_project.controller.status.ResponseMessage;
import restful_sign_project.controller.status.StatusCode;
import restful_sign_project.entity.Member;

@Data
public class SigninResponse {
    private int code;
    private String message;
    private Object data;

    public SigninResponse(){
        this.code = StatusCode.BAD_REQUEST;
        this.message = ResponseMessage.EMAIL_EXISTED;
        this.data = null;
    }

    @Builder
    public SigninResponse(int code, String message, Object data){
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
