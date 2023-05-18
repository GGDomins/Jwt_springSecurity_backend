//package restful_sign_project.service;
//
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//import restful_sign_project.dto.Member_Dto;
//
//import static org.junit.jupiter.api.Assertions.*;
//@SpringBootTest
//@Transactional
//class Member_ServiceTest {
//    @Autowired
//    Member_Service memberService;
//
//    @Test
//    void Join_Test(){
//        Member_Dto memberDto = new Member_Dto("홍용준", "123", "1234");
//
//
//        try {
//            memberService.join(memberDto);
//        }catch(Exception e){
//            Assertions.assertThat(e)
//                    .isInstanceOf(RuntimeException.class)
//                    .hasMessage("저장 실패.");
//        }
//    }
//
//}