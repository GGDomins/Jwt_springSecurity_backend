package restful_sign_project.controller.Response;

import lombok.Builder;
import lombok.Data;
import restful_sign_project.controller.status.ResponseMessage;
import restful_sign_project.controller.status.StatusCode;

@Data
public class PageSuccessResponse {
    private int code;
    private String message;
    private Object data;

    public PageSuccessResponse() {
        this.code = StatusCode.OK;
        this.message = ResponseMessage.AUTHORIZED;
        this.data = null;
    }

    @Builder
    public PageSuccessResponse(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
