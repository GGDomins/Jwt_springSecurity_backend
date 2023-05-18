//package restful_sign_project.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import restful_sign_project.JWT.JwtTokenProvider;
//import restful_sign_project.controller.Response.PageResponse;
//import restful_sign_project.controller.status.ResponseMessage;
//import restful_sign_project.controller.status.StatusCode;
//
//import java.util.Date;
//import java.util.List;
//import java.util.logging.Logger;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@Slf4j
//class MyPageControllerTest {
//
//    @Autowired
//    ObjectMapper objectMapper;
//    @Autowired
//    JwtTokenProvider jwtTokenProvider;
//
//    @Test
//    @DisplayName("인증된 사용자가 /my-page 엔드포인트에 접근할 수 있는지 확인")
//    void myPage_withValidToken_shouldReturnPageResponse() throws Exception {
//        // given
//        String validToken = createValidToken();
//        log.info(validToken);
//
//
//        // when
//        boolean b = jwtTokenProvider.validateToken((validToken));
//        assertTrue(b);
//
//        // then
//
//    }
//
//
//    private String createValidToken() {
//        String userPk = "kevin0928@naver.com";
//        String roles = "ROLE_USER";
//        Long tokenValidTime = 1000 * 60 * 60l;
//        String secretKey = "missyouannawhereareyou";
//        Claims claims = Jwts.claims().setSubject(userPk); // JWT payload 에 저장되는 정보단위, 보통 여기서 user를 식별하는 값을 넣는다.
//        claims.put("roles", roles);
//        Date now = new Date();
//        return Jwts.builder()
//                .setClaims(claims) // 정보 저장
//                .claim("AUTHORITIES_KEY", roles)
//                .setIssuedAt(now) // 토큰 발행 시간 정보
//                .setExpiration(new Date(now.getTime() + tokenValidTime)) // set Expire Time
//                .signWith(SignatureAlgorithm.HS256, secretKey)  // 사용할 암호화 알고리즘과
//                // signature 에 들어갈 secret값 세팅
//                .compact();
//    }
//
//
//}
