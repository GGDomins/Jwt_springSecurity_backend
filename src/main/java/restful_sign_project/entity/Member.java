package restful_sign_project.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Table(name = "RestFul_signup")
public class Member {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String email;
    private String passWord;
    @Builder
    public Member(String name, String email, String passWord) {
        this.name = name;
        this.email = email;
        this.passWord = passWord;
    }

}