package restful_sign_project.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import restful_sign_project.controller.Response.LoginResponse;
import restful_sign_project.controller.Response.SigninResponse;
import restful_sign_project.controller.status.ResponseMessage;
import restful_sign_project.controller.status.StatusCode;
import restful_sign_project.dto.Member_Dto;
import restful_sign_project.entity.Member;
import restful_sign_project.service.Member_Service;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Controller
@Slf4j
@Transactional
public class Member_Controller {

    private final Member_Service memberService;


    public Member_Controller(Member_Service memberService) {
        this.memberService = memberService;
    }

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

//    @PostMapping("/Login")
//    public ResponseEntity<LoginResponse> login(@RequestBody Member_Dto memberDto) {
//        LoginResponse response = new LoginResponse();
//
//    }
}
