package restful_sign_project.controller.Response;

import lombok.Builder;
import lombok.Data;
import restful_sign_project.controller.status.ResponseMessage;
import restful_sign_project.controller.status.StatusCode;
import restful_sign_project.entity.Member;
/**
 * 회원가입을 할 때 반환되는 Response입니다.
 */
@Data
public class  SignInResponse {
    private int code;
    private String message;
    private Object data;

    public SignInResponse(){
        this.code = StatusCode.BAD_REQUEST;
        this.message = ResponseMessage.EMAIL_EXISTED;
        this.data = null;
    }

    @Builder
    public SignInResponse(int code, String message, Object data){
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
