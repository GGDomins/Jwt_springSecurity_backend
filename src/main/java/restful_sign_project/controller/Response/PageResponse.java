package restful_sign_project.controller.Response;

import lombok.Builder;
import lombok.Data;
import org.apache.coyote.Response;
import restful_sign_project.controller.status.ResponseMessage;
import restful_sign_project.controller.status.StatusCode;
/**
 * HTTP 요청중 GET을 이용해 다른 페이지로 넘어갈때 반환되는 Response입니다.
 */
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
