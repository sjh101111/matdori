// SecurityConfig.java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig{
//
//    @Bean
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/", "/home").permitAll() // 홈 페이지는 모든 사용자에게 허용
//                .anyRequest().authenticated() // 그 외의 요청은 인증된 사용자에게만 허용
//                .and()
//                .formLogin()
//                .loginPage("/login") // 로그인 페이지 설정
//                .permitAll() // 로그인 페이지는 모든 사용자에게 허용
//                .and()
//                .logout()
//                .permitAll(); // 로그아웃은 모든 사용자에게 허용
//    }
//}
