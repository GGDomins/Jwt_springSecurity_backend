package restful_sign_project.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import restful_sign_project.JWT.JwtTokenProvider;
import restful_sign_project.JWT.refresh.RefreshTokenRedisRepository;
import restful_sign_project.controller.Response.PageResponse;
import restful_sign_project.controller.status.ResponseMessage;
import restful_sign_project.controller.status.StatusCode;
import restful_sign_project.service.Member_Service;
import restful_sign_project.service.PageService;
import restful_sign_project.service.RedisService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class Page_Controller {
    private final BCryptPasswordEncoder encoder;
    private final Member_Service memberService;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisService redisService;
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;
    private final PageService pageService;

    public Page_Controller(
            BCryptPasswordEncoder encoder,
            Member_Service memberService,
            JwtTokenProvider jwtTokenProvider,
            RedisService redisService,
            RefreshTokenRedisRepository refreshTokenRedisRepository,
            PageService pageService) {
        this.encoder = encoder;
        this.memberService = memberService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.redisService = redisService;
        this.refreshTokenRedisRepository = refreshTokenRedisRepository;
        this.pageService = pageService;
    }

    @GetMapping("/my-page") // AccessToken이 있다면 정상적으로 접근 가능
    public ResponseEntity<?> myPage(HttpServletRequest request) {
        // JWT 토큰 추출
        String token = jwtTokenProvider.resolveToken(request);
        ResponseEntity<?> result = pageService.findPageByToken(token);
        return result;
    }
    @GetMapping("/my-page2")
    @PreAuthorize("hasRole('ROLE_USER')") // ROLE_USER가 아니면 403에러가 일어난다.
    public ResponseEntity<PageResponse> myPage() {
        // 페이지 응답 객체 생성
        PageResponse pageResponse = PageResponse.builder()
                .code(StatusCode.OK)
                .message(ResponseMessage.AUTHORIZED)
                .token(null)
                .build();

        // 인증된 사용자만 접근 가능한 페이지
        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
    }

}
