package me.johyeonjung.post_mini_project_back.config;

import lombok.RequiredArgsConstructor;
import me.johyeonjung.post_mini_project_back.filter.JwtAuthenticationFilter;
import me.johyeonjung.post_mini_project_back.security.OAuth2SuccessHandler;
import me.johyeonjung.post_mini_project_back.service.OAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration // 이 클래스가 스프링 설정 파일(컨피그)임을 알려주는 어노테이션
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final OAuth2UserService oAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    @Bean   // 스프링 시큐리티의 보안 필터 체인을 빈으로 등록
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // :one: CORS 설정 적용 (아래 정의한 corsConfigurationSource()를 사용)
        http.cors(cors ->
                cors.configurationSource(corsConfigurationSource()));

        // :two: 세션 비활성화 (STATELESS)
        // - 서버가 세션을 생성하지 않음
        // - JWT 같은 토큰 기반 인증을 사용할 때 필수 설정
        http.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        // http 기본 로그인 비활성화
        http.httpBasic(httpbasic ->
                httpbasic.disable());

        // form 로그인 비활성화 (토큰방식 사용 할 것이기 때문에)
        http.formLogin(formLogin ->
                formLogin.disable());

        // CSRF 비활성화 (세션 기반 폼 요청에 대한 CSRF 보호 끔)
        http.csrf(csrf ->
                csrf.disable());

        http.oauth2Login(oauth2 -> oauth2.userInfoEndpoint(userInfo -> userInfo.userService(oAuth2UserService))
                .successHandler(oAuth2SuccessHandler));

        // JWT 인증 필터를 UsernamePasswordAuthenticationFilter 앞에 등록해서 토큰 기반 인증을 먼저 수행
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // :three: 모든 요청을 인증 없이 허용
        // - 개발 초기 단계에서는 permitAll()로 설정해두고
        //   나중에 인증이 필요한 요청만 제한하도록 수정 가능
        http.authorizeHttpRequests(auth ->
                auth
                        .requestMatchers("/api/auth/**").permitAll()  // 화원가입URL, 로그인URL은 상시인가
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/swagger-ui.html").permitAll()
                        .requestMatchers("/doc").permitAll()
                        .anyRequest().authenticated()     // 나머지는 인가가 필요
        );



        // 위 설정들을 기반으로 SecurityFilterChain 객체를 생성해 반환
        return http.build();
    }

    @Bean   // CORS 설정을 스프링 컨테이너에 등록
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cors = new CorsConfiguration();

        // 허용할 출처(Origin) 설정 - 프론트엔드 개발 서버 주소
        cors.setAllowedOrigins(List.of(
                "http://localhost:5173",
                "http://localhost:5174"
        ));

        // 허용할 HTTP 메서드 설정
        cors.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));

        // 허용할 요청 헤더 설정 ("*" = 모든 헤더 허용)
        cors.setAllowedHeaders(List.of("*"));

        // 인증 정보(쿠키, Authorization 헤더 등)를 함께 전송할 수 있도록 허용
        cors.setAllowCredentials(true);

        // URL 패턴별 CORS 설정 등록 객체 생성
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        // 모든 URL 경로("/**")에 대해 위의 설정 적용
        source.registerCorsConfiguration("/**", cors);

        return source;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}