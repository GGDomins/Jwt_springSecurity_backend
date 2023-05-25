package restful_sign_project.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import restful_sign_project.JWT.JwtTokenProvider;
import restful_sign_project.controller.Response.PageResponse;
import restful_sign_project.controller.status.ResponseMessage;
import restful_sign_project.controller.status.StatusCode;

import java.util.Optional;

@Service
public class PageService {

    private final JwtTokenProvider jwtTokenProvider;
    private final Long expireTimeMs = 30000L;
    public PageService(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public ResponseEntity<PageResponse> findPageByToken(String token) {
        if (jwtTokenProvider.validateToken(token)) { // JWT 토큰 유효성 검사
            Authentication authentication = jwtTokenProvider.getAuthentication(token); // 인증정보 추출
            SecurityContextHolder.getContext().setAuthentication(authentication); // Spring Security context에 인증 정보 저장
            PageResponse pageResponse = PageResponse.builder()
                    .code(StatusCode.OK)
                    .message(ResponseMessage.AUTHORIZED)
                    .build();
            return ResponseEntity.ok()
                    .header("accessToken",token)
                    .body(pageResponse);
        } else {
            PageResponse pageResponse = PageResponse.builder()
                    .code(StatusCode.UNAUTHORIZED)
                    .message(ResponseMessage.UNAUTHORIZED)
                    .build();
            return new ResponseEntity<>(pageResponse, HttpStatus.BAD_REQUEST);
        }
    }
}
