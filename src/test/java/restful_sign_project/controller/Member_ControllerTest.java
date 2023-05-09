package restful_sign_project.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import restful_sign_project.controller.Response.SigninResponse;
import restful_sign_project.dto.Member_Dto;

@SpringBootTest
@Transactional
class Member_ControllerTest {
    @Autowired
    Member_Controller memberController;

    @Test
    void 중복검사() {
        Member_Dto memberDto = new Member_Dto("홍용준", "test@test.com", "1234");
        Member_Dto memberDto2 = new Member_Dto("홍길동", "test@test.com", "1234");

        ResponseEntity<SigninResponse> response1 = memberController.signup(memberDto);
        ResponseEntity<SigninResponse> response2 = memberController.signup(memberDto2);

        Assertions.assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

}