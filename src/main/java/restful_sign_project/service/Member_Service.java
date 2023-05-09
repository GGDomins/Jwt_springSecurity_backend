package restful_sign_project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import restful_sign_project.dto.Member_Dto;
import restful_sign_project.entity.Member;

import restful_sign_project.repository.Member_Repository;

import java.util.Optional;

@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class Member_Service {
    private final Member_Repository memberRepository;

    @Transactional
    public Member join(Member_Dto memberDto) {
        return memberRepository.save(memberDto.to_Entity());
    }

    public Optional<Member> findMemberByEmail(String email) {
        return memberRepository.findMemberByEmail(email);
    }

}
