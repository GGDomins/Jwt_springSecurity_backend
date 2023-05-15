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
    private Object data;

    public PageResponse() {
        this.code = StatusCode.UNAUTHORIZED;
        this.message = ResponseMessage.UNAUTHORIZED;
        this.data = null;
    }

    @Builder
    public PageResponse(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
