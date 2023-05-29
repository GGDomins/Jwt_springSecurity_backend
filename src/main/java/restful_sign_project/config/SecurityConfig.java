package restful_sign_project.config;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import restful_sign_project.JWT.JwtAuthenticationFilter;
import restful_sign_project.JWT.JwtTokenProvider;
import restful_sign_project.service.Member_Service;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate redisTemplate;
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(); // 비밀번호 암호화를 위한 빈 생성
    }


    @Bean
    @Override    // authenticationManager를 Bean 등록합니다.
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // 로그아웃을 수행하기 위한 요청 경로 설정
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK))
                .and()
                .cors() // CORS 구성을 활성화합니다.
                /**
                 * @CrossOrigin(origins = "https://restful-jwt-project.herokuapp.com") 어노테이션은 Spring MVC에서 CORS를 구성하는 방법 중 하나입니다.
                 *
                 * 그러나 앞서 제안한 방법 중 두 번째 방법을 사용하여 Spring Security 설정에서 CORS를 구성했다면, @CrossOrigin 어노테이션을 사용할 필요는 없습니다. Spring Security의 CORS 구성은 모든 엔드포인트에 대해 적용되기 때문에, @CrossOrigin 어노테이션을 개별적으로 추가할 필요가 없습니다.
                 *
                 * 따라서, 주어진 코드에서 @CrossOrigin(origins = "https://restful-jwt-project.herokuapp.com") 어노테이션을 제거해도 됩니다. Spring Security 설정에 의해 CORS가 이미 구성되었으므로, 해당 도메인에서의 접근이 허용될 것입니다.
                 */
                .and()
                .csrf().disable()
                .httpBasic().disable()
                .authorizeRequests()
                .antMatchers("/test").authenticated()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasRole("USER")
                .antMatchers("/**").permitAll()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, redisTemplate),
                        UsernamePasswordAuthenticationFilter.class);

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }


}