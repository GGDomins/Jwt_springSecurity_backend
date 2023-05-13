package restful_sign_project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import restful_sign_project.JWT.JwtTokenProvider;
import restful_sign_project.dto.Member_Dto;
import restful_sign_project.entity.Member;

import restful_sign_project.repository.Member_Repository;

import java.util.Optional;

@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class Member_Service implements UserDetailsService {
    private final Member_Repository memberRepository;


    @Transactional
    public Member join(Member_Dto memberDto) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberDto.setPassWord(passwordEncoder.encode(memberDto.getPassWord()));
        return memberRepository.save(memberDto.to_Entity());
    }

    public Optional<Member> findMemberByEmail(String email) {
        return memberRepository.findMemberByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findMemberByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }
}
