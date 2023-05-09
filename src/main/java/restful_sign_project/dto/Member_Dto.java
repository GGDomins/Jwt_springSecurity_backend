package restful_sign_project.dto;

import lombok.*;
import restful_sign_project.entity.Member;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Member_Dto {
    private String name;
    private String email;
    private String passWord;

    public Member to_Entity() {
        return Member.builder()
                .name(name)
                .email(email)
                .passWord(passWord)
                .build();
    }
}
