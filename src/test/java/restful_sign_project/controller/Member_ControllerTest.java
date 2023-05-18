//package restful_sign_project.controller;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.transaction.annotation.Transactional;
//import restful_sign_project.controller.Response.SignInResponse;
//import restful_sign_project.controller.status.ResponseMessage;
//import restful_sign_project.controller.status.StatusCode;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@SpringBootTest
//@Transactional
//class Member_ControllerTest {
//    @Autowired
//    Member_Controller memberController;
//
//
//    @Test
//    void 회원가입검사() {
//        // given
//        Map<String, String> requestBody = new HashMap<>();
//        requestBody.put("name", "홍용준");
//        requestBody.put("email", "test@test.com");
//        requestBody.put("password", "1234");
//
//        // when
//        ResponseEntity<SignInResponse> responseEntity = memberController.signup(requestBody);
//        SignInResponse response = responseEntity.getBody();
//
//        // then
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertEquals(StatusCode.OK, response.getCode());
//        assertEquals(ResponseMessage.SIGNIN_SUCCESS, response.getMessage());
//        System.out.println(responseEntity);
//    }
//
//}