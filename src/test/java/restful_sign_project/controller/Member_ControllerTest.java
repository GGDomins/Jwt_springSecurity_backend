package restful_sign_project.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import restful_sign_project.controller.Response.SigninResponse;
import restful_sign_project.controller.status.ResponseMessage;
import restful_sign_project.controller.status.StatusCode;
import restful_sign_project.dto.Member_Dto;
import restful_sign_project.entity.Member;
import restful_sign_project.service.Member_Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
class Member_ControllerTest {
    @Autowired
    Member_Controller memberController;


    @Test
    void 회원가입검사() {
        // given
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("name", "홍용준");
        requestBody.put("email", "test@test.com");
        requestBody.put("password", "1234");

        // when
        ResponseEntity<SigninResponse> responseEntity = memberController.signup(requestBody);
        SigninResponse response = responseEntity.getBody();

        // then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(StatusCode.OK, response.getCode());
        assertEquals(ResponseMessage.SIGNIN_SUCCESS, response.getMessage());
        System.out.println(responseEntity);
    }
}