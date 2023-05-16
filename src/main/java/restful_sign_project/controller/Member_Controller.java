package restful_sign_project.controller;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import restful_sign_project.JWT.JwtTokenProvider;
import restful_sign_project.JWT.refresh.RefreshTokenRedisRepository;
import restful_sign_project.controller.Request.RefreshTokenRequest;
import restful_sign_project.controller.Response.*;
import restful_sign_project.controller.status.ResponseMessage;
import restful_sign_project.controller.status.StatusCode;
import restful_sign_project.dto.Member_Dto;
import restful_sign_project.entity.Member;
import restful_sign_project.service.Member_Service;
import restful_sign_project.service.RedisService;

import java.util.*;

@RestController("/api")
@Slf4j
@Transactional
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class Member_Controller {
    private final BCryptPasswordEncoder encoder;
    private final Member_Service memberService;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisService redisService;
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;
    @Value("${jwt.token.secret}")
    private String key;

    private final Long expireTimeMs = 1000 * 60 * 60l;
    private final Long RefreshExpireTimeMs = 1000 * 60 * 60 * 60l;

    public Member_Controller(
            BCryptPasswordEncoder encoder,
            Member_Service memberService,
            JwtTokenProvider jwtTokenProvider,
            RedisService redisService,
            RefreshTokenRedisRepository refreshTokenRedisRepository) {
        this.encoder = encoder;
        this.memberService = memberService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.redisService = redisService;
        this.refreshTokenRedisRepository = refreshTokenRedisRepository;
    }
    //회원가입

    /**
     * JSON형식으로 입력을 받으며 STRING : STRING 형식으로 입력을 받기 때문에 Map함수를 사용함.
     */
    @PostMapping("/signup")
    public ResponseEntity<SignInResponse> signup(@RequestBody Map<String, String> memberDto) {
        SignInResponse response = new SignInResponse();
        String name = (String) memberDto.get("name");
        String email = (String) memberDto.get("email");
        String password = (String) memberDto.get("password");
        Member_Dto member_dto = new Member_Dto(name, email, password);
        Optional<Member> memberFind = memberService.findMemberByEmail(email);
        if (memberFind.isEmpty()) { //Optional로 받았기 때문에 member가 없는/있는 상황을 고려해야함.
            Member member = memberService.join(member_dto);
            log.info(member.getRoles().get(0));
            response = SignInResponse.builder()
                    .code(StatusCode.OK)
                    .message(ResponseMessage.SIGNIN_SUCCESS)
                    .data(member)
                    .build();
            return ResponseEntity.ok(response); // 성공하면 OK안에 response를 담아서 return 함.
        }
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); //실패하면 BAD_REQUEST와 함께 response를 보냄
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody Map<String, String> user) { //로그인도 회원가입과 마찬가지로 map함수를 사용해서 받음
        LoginResponse loginResponse = new LoginResponse();
        Optional<Member> member1 = memberService.findMemberByEmail(user.get("email")); //memberService를 이용해서 email로 Member를 찾음
        if (!member1.isPresent()) { //email로 찾았는데 member가 없는 경우
            loginResponse = LoginResponse.builder()
                    .code(StatusCode.BAD_REQUEST)
                    .message(ResponseMessage.EMAIL_NOT_FOUND)
                    .token(null)
                    .build();
            return new ResponseEntity<>(loginResponse, HttpStatus.BAD_REQUEST);
        }
        Member member = member1.get(); //member가 있는 경우
        log.info(member.getPassword());
        log.info(user.get("password"));
        if (!encoder.matches(user.get("password"), member.getPassword())) { //
            loginResponse = LoginResponse.builder()
                    .code(StatusCode.BAD_REQUEST)
                    .message(ResponseMessage.PASSWORD_ERROR)
                    .token(null)
                    .build();
            return new ResponseEntity<>(loginResponse, HttpStatus.BAD_REQUEST);
        }
        String token = jwtTokenProvider.createToken(member.getEmail(), member.getRoles(), expireTimeMs); //AccessToken : tokenProvider을 통해서 인자로 이메일,역할,시간을 보낸다.
        String refreshToken = jwtTokenProvider.createToken(member.getEmail(), member.getRoles(), RefreshExpireTimeMs); //RefreshToken : tokenProvider을 통해서 인자로 이메일,역할,시간을 보낸다.
        redisService.setValues(refreshToken, member.getEmail());
        log.info(token);
        log.info(refreshToken);

        loginResponse = LoginResponse.builder()
                .code(StatusCode.OK)
                .message(ResponseMessage.LOGIN_SUCCESS)
                .token(token)
                .build();
        return ResponseEntity.ok(loginResponse); // ok 안에 token을 포함한 데이터를 포함해 보냄.
    }

    @GetMapping("/my-page") // AccessToken이 있다면 정상적으로 접근 가능
    public ResponseEntity<PageResponse> myPage(HttpServletRequest request) {
        // JWT 토큰 추출
        String token = jwtTokenProvider.resolveToken(request);

        // JWT 토큰 유효성 검증
        if (jwtTokenProvider.validateToken(token)) {
            // JWT 토큰으로부터 인증 정보 추출
            Authentication authentication = jwtTokenProvider.getAuthentication(token);

            // Spring Security Context에 인증 정보 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 로그 출력
            log.info("토큰 있다구!!!");

            // PageResponse 객체 생성 및 반환
            PageResponse pageResponse = PageResponse.builder()
                    .code(StatusCode.OK)
                    .message(ResponseMessage.AUTHORIZED)
                    .token(token)
                    .build();

            // 인증된 사용자만 접근 가능한 페이지
            return ResponseEntity.ok(pageResponse);
        } else {
            // 로그 출력
            log.info("토큰 없다구!!!");

            // PageResponse 객체 생성 및 반환
            PageResponse pageResponse = PageResponse.builder()
                    .code(StatusCode.UNAUTHORIZED)
                    .message(ResponseMessage.UNAUTHORIZED)
                    .build();

            // 인증되지 않은 사용자에게는 400 Bad Request 반환
            return new ResponseEntity<>(pageResponse, HttpStatus.BAD_REQUEST);
        }
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

    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        // 요청에서 refresh token 값을 추출
        String refreshToken = refreshTokenRequest.getRefreshToken();

        // Access Token 갱신
        String newAccessToken = jwtTokenProvider.refreshToken(refreshToken);

        // 새로운 Access Token 값과 함께 응답 객체 생성
        RefreshTokenResponse response = RefreshTokenResponse.builder()
                .code(StatusCode.OK)
                .message(ResponseMessage.REFRESH_TOKEN_SUCCESS)
                .accessToken(newAccessToken)
                .refreshToken(refreshToken)
                .build();

        return ResponseEntity.ok(response);
    }
}