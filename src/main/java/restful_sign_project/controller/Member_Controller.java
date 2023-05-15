package restful_sign_project.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import restful_sign_project.JWT.JwtTokenProvider;
import restful_sign_project.controller.Response.LoginResponse;
import restful_sign_project.controller.Response.PageResponse;
import restful_sign_project.controller.Response.PageSuccessResponse;
import restful_sign_project.controller.Response.SigninResponse;
import restful_sign_project.controller.status.ResponseMessage;
import restful_sign_project.controller.status.StatusCode;
import restful_sign_project.dto.Member_Dto;
import restful_sign_project.entity.Member;
import restful_sign_project.repository.Member_Repository;
import restful_sign_project.service.Member_Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@Slf4j
@Transactional
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class Member_Controller {
    @Autowired
    private PasswordEncoder encoder;
    private final Member_Service memberService;
    public Member_Controller(Member_Service memberService) {
        this.memberService = memberService;
    }
    @Value("${jwt.token.secret}")
    private String key;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    private Long expireTimeMs = 1000 * 60 * 60l;


    @PostMapping("/api/signup")
    public ResponseEntity<SigninResponse> signup(@RequestBody Map<String,String> memberDto) {
        SigninResponse response = new SigninResponse();
        String name = (String) memberDto.get("name");
        String email = (String) memberDto.get("email");
        String password = (String) memberDto.get("password");
        Member_Dto member_dto = new Member_Dto(name, email, password);
        Optional<Member> memberFind = memberService.findMemberByEmail(email);
        if (memberFind.isEmpty()) {
            Member member = memberService.join(member_dto);
            log.info(member.getRoles().get(0));
            response = SigninResponse.builder()
                    .code(StatusCode.OK)
                    .message(ResponseMessage.SIGNIN_SUCCESS)
                    .data(member)
                    .build();
//            return new ResponseEntity<>(response, HttpStatus.OK);
            return ResponseEntity.ok(response);
        }
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody Map<String, String> user) {
        LoginResponse loginResponse = new LoginResponse();
        Optional<Member> member1 = memberService.findMemberByEmail(user.get("email"));
        if (!member1.isPresent()) {
            loginResponse = LoginResponse.builder()
                    .code(StatusCode.BAD_REQUEST)
                    .message(ResponseMessage.EMAIL_NOT_FOUND)
                    .data(null)
                    .build();
            return new ResponseEntity<>(loginResponse, HttpStatus.BAD_REQUEST);
        }
        Member member = member1.get();
        log.info(member.getPassword());
        log.info(user.get("password"));
        if (!encoder.matches(user.get("password"),member.getPassword())) {
            loginResponse = LoginResponse.builder()
                    .code(StatusCode.BAD_REQUEST)
                    .message(ResponseMessage.PASSWORD_ERROR)
                    .data(null)
                    .build();
            return new ResponseEntity<>(loginResponse, HttpStatus.BAD_REQUEST);
        }
        String token = jwtTokenProvider.createToken(member.getEmail(), member.getRoles(), expireTimeMs);
        log.info(token);


        loginResponse = LoginResponse.builder()
                .code(StatusCode.OK)
                .message(ResponseMessage.LOGIN_SUCCESS)
                .data(token)
                .build();
        return ResponseEntity.ok(loginResponse);
    }
    @GetMapping("/my-page")
    public ResponseEntity<PageResponse> myPage(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        PageResponse pageResponse = new PageResponse();
        if (jwtTokenProvider.validateToken(token)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("토큰 있다구!!!");
           pageResponse = PageResponse.builder()
                    .code(StatusCode.OK)
                    .message(ResponseMessage.AUTHORIZED)
                    .data(null)
                    .build();
            // 인증된 사용자만 접근 가능한 페이지
            return ResponseEntity.ok(pageResponse);
        } else {
            log.info("토큰 없다구!!!");
            return new ResponseEntity<>(pageResponse, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/my-page2")
    @PreAuthorize("hasRole('ROLE_USER')") // ROLE_USER가 아니면 403에러가 일어난다.
    public ResponseEntity<PageSuccessResponse> myPage() {
        PageSuccessResponse pageSuccessResponse = new PageSuccessResponse();
        // 인증된 사용자만 접근 가능한 페이지
        return new ResponseEntity<>(pageSuccessResponse, HttpStatus.OK);
    }

}
