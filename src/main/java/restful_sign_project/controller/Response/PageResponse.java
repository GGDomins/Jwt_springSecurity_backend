package restful_sign_project.controller.Response;

import lombok.Builder;
import lombok.Data;
import org.apache.coyote.Response;
import restful_sign_project.controller.status.ResponseMessage;
import restful_sign_project.controller.status.StatusCode;

@Data
public class PageResponse {
    private int code;
    private String message;
    private Object token;

    public PageResponse() {
        this.code = StatusCode.UNAUTHORIZED;
        this.message = ResponseMessage.UNAUTHORIZED;
        this.token = null;
    }

    @Builder
    public PageResponse(int code, String message, Object token) {
        this.code = code;
        this.message = message;
        this.token = token;
    }
}
